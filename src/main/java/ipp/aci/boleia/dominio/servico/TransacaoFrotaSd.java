package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IExtratoPedidoTransacaoDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.IPedidoCreditoFrotaDados;
import ipp.aci.boleia.dados.ISaldoFrotaDados;
import ipp.aci.boleia.dados.ITransacaoFrotaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.ExtratoPedidoTransacao;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.SaldoFrota;
import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.TransacaoFrota;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.OperacaoDeCredito;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.dominio.enums.TipoAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TipoTransacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoCreditoInsuficiente;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Executa operacoes de transacao financeira das frotas
 */
@Component
public class TransacaoFrotaSd {

    @Autowired
    private ITransacaoFrotaDados repositorioTrancacao;

    @Autowired
    private IFrotaDados repositorioFrota;

    @Autowired
    private ISaldoFrotaDados repositorioSaldo;

    @Autowired
    private IExtratoPedidoTransacaoDados repositorioExtrato;

    @Autowired
    private IPedidoCreditoFrotaDados repositorioPedido;

    @Autowired
    private TransacaoVeiculoSd transacaoVeiculoSd;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida uma transacao de pagamento de produto ou servico
     *
     * @param autorizacaoPagamento A autorizacao de pagamento dos produtos ou servicos
     * @throws ExcecaoCreditoInsuficiente Quando nao ha saldo suficiente para a transacao informada
     */
    public void validarProdutoOuServico(AutorizacaoPagamento autorizacaoPagamento) throws ExcecaoCreditoInsuficiente {
        TipoAutorizacaoPagamento tipoPagamento = TipoAutorizacaoPagamento.obterPorValor(autorizacaoPagamento.getTipoAutorizacaoPagamento());
       if(!Objects.equals(tipoPagamento, TipoAutorizacaoPagamento.MAN_PI)){
           TipoTransacao tipo = obterTipoTransacao(tipoPagamento, false);
           if(tipo.isDebito() && autorizacaoPagamento.getFrota().getSaldo().getSaldoCorrente().compareTo(autorizacaoPagamento.getValorTotal()) < 0) {
               throw new ExcecaoCreditoInsuficiente();
           }
       }
    }

    /**
     * Registra uma transacao de pagamento de produto ou servico
     *
     * @param autorizacaoPagamento A autorizacao de pagamento dos produtos ou servicos
     * @param tipoPagamento O tipo do pagamento
     * @return A transacao registrada
     * @throws ExcecaoCreditoInsuficiente Quando nao ha saldo suficiente para a transacao informada
     */
    public TransacaoFrota pagarProdutoOuServico(AutorizacaoPagamento autorizacaoPagamento, TipoAutorizacaoPagamento tipoPagamento) throws ExcecaoCreditoInsuficiente {
        TipoTransacao tipo = obterTipoTransacao(tipoPagamento, false);
        validarProdutoOuServico(autorizacaoPagamento);

        SaldoVeiculo saldoVeiculo = transacaoVeiculoSd.registrarTransacaoAbastecimentoVeiculo(autorizacaoPagamento);
        autorizacaoPagamento.getVeiculo().setSaldoVeiculo(saldoVeiculo);

        return registrarTransacaoSemVerificarSaldo( autorizacaoPagamento.getFrota(), autorizacaoPagamento.getValorTotal(), tipo, null, null);

    }

    /**
     * Registra uma transacao de pagamento de produto ou servico
     *
     * @param autorizacaoPagamento A autorizacao de pagamento dos produtos ou servicos
     * @param tipoPagamento O tipo do pagamento
     * @return A transacao registrada
     */
    public TransacaoFrota pagarProdutoOuServicoPermitindoSaldoNegativo(AutorizacaoPagamento autorizacaoPagamento, TipoAutorizacaoPagamento tipoPagamento) {
        if (autorizacaoPagamento.getFrota().isPrePaga()
                && autorizacaoPagamento.getFrota().getSaldo().getSaldoCorrente().compareTo(autorizacaoPagamento.getValorTotal()) < 0) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_FROTA_PRE_NEGATIVADA);
        }
        TipoTransacao tipo = obterTipoTransacao(tipoPagamento, false);
        SaldoVeiculo saldoVeiculo = transacaoVeiculoSd.registrarTransacaoAbastecimentoVeiculo(autorizacaoPagamento);
        autorizacaoPagamento.getVeiculo().setSaldoVeiculo(saldoVeiculo);
        return registrarTransacaoSemVerificarSaldo(autorizacaoPagamento.getFrota(), autorizacaoPagamento.getValorTotal(), tipo, null, null);
    }

    /**
     * Registra uma transacao de pagamento de ciclo, restaurando o saldo da frota com o valor correspondente.
     * @param idFrota    A frota
     * @param valorPago O valor pago
     * @return A transacao frota cujo ciclo foi pago
     */
    public TransacaoFrota pagarCiclo(Long idFrota, BigDecimal valorPago) {
        Frota frota = repositorioFrota.obterPorId(idFrota);
        verificarModalidadePagamento(frota, ModalidadePagamento.POS_PAGO);
        return registrarTransacaoSemVerificarSaldo(frota, valorPago, TipoTransacao.PAGAMENTO_CICLO, null, null);
    }

    /**
     * Quando a frota é habilitada no sistema, cria um registro de saldo
     * para a mesma, contendo o limite de credito ou saldo informado. Registra uma
     * transacao de credito no valor correspondente a esse limite, elevando
     * o saldo inicial de zero para o valor do limite.
     *
     * @param idFrota A frota
     * @param valorInicial O limite de credito ou saldo inicial da frota
     */
    public void concederCreditoHabilitacaoFrota(Long idFrota, BigDecimal valorInicial) {
        Frota frota = repositorioFrota.obterPorId(idFrota);
        ModalidadePagamento modalidadePagamento = ModalidadePagamento.obterPorValor(frota.getModoPagamento());
        SaldoFrota saldo = frota.getSaldo();
        if(saldo == null) {
            saldo = new SaldoFrota();
            saldo.setFrota(frota);
            saldo.setSaldoCorrente(BigDecimal.ZERO);
            PedidoCreditoFrota pedido = null;
            if(ModalidadePagamento.PRE_PAGO.equals(modalidadePagamento)) {
                pedido = criarPedidoCreditoInicialHabilitacao(frota, valorInicial);
            } else {
                saldo.setLimiteCredito(valorInicial);
            }
            saldo = repositorioSaldo.armazenar(saldo);
            frota.setSaldo(saldo);
            registrarTransacaoSemVerificarSaldo(frota, valorInicial, TipoTransacao.CONCESSAO_CREDITO_HABILITACAO_FROTA, pedido, null);
        } else {
            throw new ExcecaoBoleiaRuntime(Erro.CONCESSAO_CREDITO_HABILITACAO_JA_REALIZADA);
        }
    }

    /**
     * Cria um pedido de credito especial, para que a concessao do saldo inicial da frota
     * se comporte como um saldo pre convencional (com data de expiracao, por exemplo)
     *
     * @param frota A frota
     * @param valorInicial O valor concedido como credito inicial
     * @return O pedido salvo
     */
    private PedidoCreditoFrota criarPedidoCreditoInicialHabilitacao(Frota frota, BigDecimal valorInicial) {
        String codigo = mensagens.obterMensagem("pedido.credito.pre.pago.habilitacao.prefixo") + UtilitarioCriptografia.gerarTokenAlfanumerico(6);
        PedidoCreditoFrota novoPedido = new PedidoCreditoFrota();
        novoPedido.setCodigoPedido(codigo);
        novoPedido.setDataPedido(ambiente.buscarDataAmbiente());
        novoPedido.setFrota(frota);
        novoPedido.setValorPedido(valorInicial);
        novoPedido.setValorPago(valorInicial);
        novoPedido.setCodigoPedidoMundipagg(codigo);
        novoPedido.setValidadePedido(UtilitarioCalculoData.adicionarDiasData(ambiente.buscarDataAmbiente(), 180));
        novoPedido.setStatus(StatusPedidoCredito.PAGO.getValue());
        novoPedido.setValorConsumido(BigDecimal.ZERO);
        return repositorioPedido.armazenar(novoPedido);
    }

    /**
     * Quando a frota realiza o pagamento de um pedido de credito pre-pago,
     * concedendo seu devido saldo em uma transacao
     * @param pedido de credito pago
     */
    public void concederPagamentoCreditoPrePago(PedidoCreditoFrota pedido) {
        Frota frota = pedido.getFrota();
        verificarModalidadePagamento(frota, ModalidadePagamento.PRE_PAGO);
        SaldoFrota saldo = frota.getSaldo();
        if(saldo == null) {
            saldo = new SaldoFrota();
            saldo.setFrota(frota);
            saldo.setSaldoCorrente(BigDecimal.ZERO);
            saldo = repositorioSaldo.armazenar(saldo);
            frota.setSaldo(saldo);
        }
        registrarTransacaoSemVerificarSaldo(frota, pedido.getValorPago(), TipoTransacao.COMPRA_CREDITO_PREPAGO_FROTA, pedido, null);
    }

    /**
     * Quando um pedido de credito pre-pago da frota é expirado, causando uma transacao para zerar seu saldo
     * @param pedido expirado
     */
    public void expirarCreditoPrePago(PedidoCreditoFrota pedido) {
        Frota frota = pedido.getFrota();
        verificarModalidadePagamento(frota, ModalidadePagamento.PRE_PAGO);
        registrarTransacaoSemVerificarSaldo(frota, pedido.getSaldoPedido(), TipoTransacao.EXPIRACAO_CREDITO_PREPAGO_FROTA, pedido, null);
    }

    /**
     * Registro um extrato simples de transacao da frota vinculado ou não a um pedido de credito de frota,
     * no caso de frota pre-paga.
     * @param valorPago da transacao em relacao ao pedido
     * @param transacaoFrota transacao total realizada
     * @param pedido vinculado a transacao
     * @return transacao do extrado atualizada
     */
    private TransacaoFrota registrarExtratoTransacaoPedido(BigDecimal valorPago, TransacaoFrota transacaoFrota, PedidoCreditoFrota pedido) {
        ExtratoPedidoTransacao extrato = new ExtratoPedidoTransacao();
        extrato.setTransacaoFrota(transacaoFrota);
        extrato.setPedido(pedido);
        extrato.setValor(valorPago);
        repositorioExtrato.armazenar(extrato);
        return repositorioTrancacao.armazenar(transacaoFrota);
    }

    /**
     * Quando a frota tem o limite ou saldo alterado no sistema, cria uma transacao,
     * contendo a diferença do valor informado para o atual. Registra uma
     * transacao de credito no valor correspondente a essa diferença, elevando
     * ou diminuindo saldo da frota.
     *
     * @param idFrota A frota
     * @param valorAlterado O novo limite de credito ou saldo da frota
     * @throws ExcecaoCreditoInsuficiente no caso do novo limite não comportar o saldo atual da frota
     */
    public void alterarLimiteOuSaldoFrota(Long idFrota, BigDecimal valorAlterado) throws  ExcecaoCreditoInsuficiente {
        Frota frota = repositorioFrota.obterPorId(idFrota);
        ModalidadePagamento modalidadePagamento = ModalidadePagamento.obterPorValor(frota.getModoPagamento());
        SaldoFrota saldo = repositorioSaldo.obterSaldoPorIdFrota(frota.getId());
        if(saldo == null) {
            saldo = new SaldoFrota();
            saldo.setFrota(frota);
            saldo.setSaldoCorrente(BigDecimal.ZERO);
            saldo = repositorioSaldo.armazenar(saldo);
        }
        frota.setSaldo(saldo);
        BigDecimal diferenca;
        if(modalidadePagamento != null && modalidadePagamento.equals(ModalidadePagamento.POS_PAGO)) {
            diferenca = valorAlterado.subtract(saldo.getLimiteCredito());
        } else {
            diferenca = valorAlterado.subtract(saldo.getSaldoCorrente());
        }
        if (diferenca.compareTo(BigDecimal.ZERO) != 0) {
            if(diferenca.compareTo(BigDecimal.ZERO)>0) {
                registrarTransacao(frota, diferenca, TipoTransacao.AUMENTO_SALDO_LIMITE_FROTA);
                if(ModalidadePagamento.PRE_PAGO.equals(modalidadePagamento)) {
                    frota.setPrimeiraCompra(true);
                    saldo.setFrota(repositorioFrota.armazenar(frota));
                }
            } else {
                registrarTransacao(frota, diferenca.abs(), TipoTransacao.REDUCAO_SALDO_LIMITE_FROTA);
            }
            if(ModalidadePagamento.POS_PAGO.equals(modalidadePagamento)) {
                saldo.setLimiteCredito(valorAlterado);
            }
            repositorioSaldo.armazenar(saldo);
        }
    }

    /**
     * Estorna uma transacao realizada previamente
     *
     * @param autorizacaoPagamento A autorizacao de pagamento dos produtos ou servicos
     * @param existeCobrancaPaga Caso exista alguma cobrança relacionada ao abastecimento paga, o saldo não é retornado novamente para a frota.
     * @return a transacao de estorno registrada
     */
    public TransacaoFrota estornarTransacao(AutorizacaoPagamento autorizacaoPagamento, Boolean existeCobrancaPaga) {
        TransacaoFrota original = repositorioTrancacao.obterPorId(autorizacaoPagamento.getTransacaoFrota().getId());
        BigDecimal valorTotalTransacao = existeCobrancaPaga ? BigDecimal.ZERO : original.getValorTotal().abs();
        TipoTransacao tipoEstorno = TipoTransacao.obterPorValor(original.getTipoTransacao()).obterTipoEstorno();
        SaldoVeiculo saldoVeiculo = transacaoVeiculoSd.estornarTransacaoAbastecimentoVeiculo(autorizacaoPagamento);
        autorizacaoPagamento.getVeiculo().setSaldoVeiculo(saldoVeiculo);
        return registrarTransacaoSemVerificarSaldo(original.getFrota(), valorTotalTransacao, tipoEstorno, null, original);
    }

    /**
     * Registra uma transacao para uma frota verificando seu saldo corrente disponivel.
     * @param frota a frota
     * @param valorTotal o valor da transacao
     * @param tipo o tipo da transacao
     * @throws ExcecaoCreditoInsuficiente Caso o saldo corrente seja inferior ao valor da transacao informada
     */
    private void registrarTransacao(Frota frota, BigDecimal valorTotal, TipoTransacao tipo) throws ExcecaoCreditoInsuficiente {
        if (tipo.isDebito() && frota.getSaldo().getSaldoCorrente().compareTo(valorTotal) < 0) {
            if (frota.isPrePaga()) {
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_FROTA_PRE_NEGATIVADA);
            } else {
                throw new ExcecaoCreditoInsuficiente();
            }
        }
        registrarTransacaoSemVerificarSaldo(frota, valorTotal, tipo, null, null);
    }

    /**
     * Registra uma transacao para uma frota verificando seu saldo corrente disponivel baseado no desconto aplicado.
     * @param frota a frota
     * @param valorTotal o valor da transação
     * @param valor o valor de ajuste da transação
     * @param tipo o tipo do abastecimento
     * @param isEstorno se é uma transação de estorno
     * @return A transacao registrada
     * @throws ExcecaoCreditoInsuficiente Caso o saldo corrente seja inferior ao valor da transacao informada
     */
    public TransacaoFrota registrarTransacaoComDesconto(Frota frota, BigDecimal valorTotal, BigDecimal valor, TipoAutorizacaoPagamento tipo, Boolean isEstorno) throws ExcecaoCreditoInsuficiente {
        TipoTransacao tipoTransacao = obterTipoTransacao(tipo, isEstorno);

        if(tipoTransacao.isDebito() && frota.getSaldo().getSaldoCorrente().compareTo(valorTotal) < 0) {
            throw new ExcecaoCreditoInsuficiente();
        }

        return registrarTransacaoSemVerificarSaldo(frota, valor, tipoTransacao, null, null);
    }

    /**
     * Registra uma transacao para uma frota, atualizando seu saldo corrente independente do saldo disponivel.
     *
     * @param frota a frota
     * @param valorTotal o valor da transacao
     * @param tipo o tipo da transacao
     * @param pedido O pedido a ser associado a transacao
     * @param transacaoOriginal Caso se trate de um estorno, contem a transacao sendo estornada
     * @return A transacao registrada
     */
    private TransacaoFrota registrarTransacaoSemVerificarSaldo(Frota frota, BigDecimal valorTotal, TipoTransacao tipo, PedidoCreditoFrota pedido, TransacaoFrota transacaoOriginal) {

        SaldoFrota saldo = frota.getSaldo();

        if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new ExcecaoBoleiaRuntime(Erro.TRANSACAO_VALOR_INVALIDO);
        }

        if (tipo.isDebito()) {
            valorTotal = valorTotal.negate();
        }

        // arredondando o valor antes de alterar o saldo da frota
        valorTotal = valorTotal.setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal novoSaldo = saldo.getSaldoCorrente().add(valorTotal);
        saldo.setSaldoCorrente(novoSaldo);
        frota.setSaldo(repositorioSaldo.armazenar(saldo));

        TransacaoFrota transacao = new TransacaoFrota();
        transacao.setDataTransacao(ambiente.buscarDataAmbiente());
        transacao.setFrota(frota);
        transacao.setTipoTransacao(tipo.getValue());
        transacao.setValorTotal(valorTotal);
        transacao.setConsumiuCreditoPrePago(transacaoOriginal != null ? transacaoOriginal.getConsumiuCreditoPrePago() : false);

        transacao = repositorioTrancacao.armazenar(transacao);
        if (frota.isPrePaga()) {
            boolean usoDeCredito = tipo.isDebito() && OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO.equals(tipo.getTipoOperacao());
            if (usoDeCredito) {
                return registrarExtratosParaPedidosPrePagos(transacao, valorTotal);
            }
            boolean isEstorno = !tipo.isDebito() && transacaoOriginal != null;
            if (isEstorno) {
                return registrarExtratosEstornoEmPedidosPrePagos(transacao, valorTotal, transacaoOriginal);
            }
        }
        return registrarExtratoTransacaoPedido(valorTotal, transacao, pedido);
    }

    /**
     * Dado um valor a ser estornado do saldo da frota, percorre os pedidos de compra de credito da transação que está sendo estornada
     * retornando o saldo desses pedidos ate que o valor total que precisa ser estornado seja atingido.
     * Cria um registro de {@link ExtratoPedidoTransacao} para cada pedido sujo saldo for afetado.
     *
     * @param transacao responsavel pelo estorno
     * @param valorTotal para estornar dos pedidos e registar no extratos
     * @param transacaoOriginal transacao original que esta sendo estornada
     * @return A transacao atualizada
     */
    private TransacaoFrota registrarExtratosEstornoEmPedidosPrePagos(TransacaoFrota transacao, BigDecimal valorTotal, TransacaoFrota transacaoOriginal) {
        List<PedidoCreditoFrota> pedidos
                = transacaoOriginal.getExtratosTransacao().stream().map(ExtratoPedidoTransacao::getPedido).collect(Collectors.toList());
        for (PedidoCreditoFrota pedido : pedidos) {
            if (valorTotal.compareTo(BigDecimal.ZERO) > 0) {
                ExtratoPedidoTransacao extratoPedido = new ExtratoPedidoTransacao();
                extratoPedido.setTransacaoFrota(transacao);
                if (pedido.getValorConsumido() == null) {
                    pedido.setValorConsumido(BigDecimal.ZERO);
                }
                if (valorTotal.compareTo(pedido.getValorConsumido()) < 0) {
                    pedido.setValorConsumido(pedido.getValorConsumido().add(valorTotal.negate()));
                    extratoPedido.setValor(valorTotal);
                    valorTotal = BigDecimal.ZERO;
                } else {
                    extratoPedido.setValor(pedido.getValorConsumido());
                    valorTotal = valorTotal.subtract(pedido.getValorConsumido());
                    pedido.setValorConsumido(BigDecimal.ZERO);
                }
                extratoPedido.setPedido(repositorioPedido.armazenar(pedido));
                repositorioExtrato.armazenar(extratoPedido);
                transacao.setConsumiuCreditoPrePago(true);
            }
        }
        return repositorioTrancacao.armazenar(transacao);
    }

    /**
     * Dado um valor a ser debitado do saldo da frota, percorre os pedidos de compra de credito que ainda possuam
     * saldo, do mais antigo para o mais rececente, consumindo o saldo desses pedidos ate que o valor total da transacao seja atingido.
     * Cria um registro de {@link ExtratoPedidoTransacao} para cada pedido sujo saldo for afetado.
     * Caso a soma dos saldos pre-pagos disponiveis nao seja suficiente para cobrir completamente o debito, entao a transação é recusada.
     *
     * @param valorTotal a debitar dos pedidos
     * @param transacao responsavel pelo debito
     * @return A transacao atualizada
     */
    private TransacaoFrota registrarExtratosParaPedidosPrePagos(TransacaoFrota transacao, BigDecimal valorTotal) {
        List<PedidoCreditoFrota> pedidosPagos = repositorioPedido.obterPagosComSaldo(transacao.getFrota().getId());
        BigDecimal somaSaldosPre = pedidosPagos.stream().map(PedidoCreditoFrota::getSaldoPedido).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(somaSaldosPre.compareTo(valorTotal.negate()) >= 0) {
            for (PedidoCreditoFrota pedido : pedidosPagos) {
                if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                    ExtratoPedidoTransacao extratoPedido = new ExtratoPedidoTransacao();
                    extratoPedido.setTransacaoFrota(transacao);
                    if (pedido.getValorConsumido() == null) {
                        pedido.setValorConsumido(BigDecimal.ZERO);
                    }
                    if (pedido.getSaldoPedido().compareTo(valorTotal.negate()) > 0) {
                        pedido.setValorConsumido(pedido.getValorConsumido().add(valorTotal.negate()));
                        extratoPedido.setValor(valorTotal);
                        valorTotal = BigDecimal.ZERO;
                    } else {
                        pedido.setValorConsumido(pedido.getValorConsumido().add(pedido.getSaldoPedido()));
                        extratoPedido.setValor(pedido.getSaldoPedido().negate());
                        valorTotal = valorTotal.add(pedido.getSaldoPedido());
                    }
                    extratoPedido.setPedido(repositorioPedido.armazenar(pedido));
                    repositorioExtrato.armazenar(extratoPedido);
                    transacao.setConsumiuCreditoPrePago(true);
                }
            }
        }

        if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new ExcecaoBoleiaRuntime(Erro.FROTA_SALDO_INSUFICIENTE);
        }

        return repositorioTrancacao.armazenar(transacao);
    }

    /**
     * Determina o tipo de transacao a partir do tipo de autorizacao de pagamento
     *
     * @param tipoAutorizacao o tipo de autorizacao
     * @param isEstorno true caso seja uma transacao de estorno
     * @return O tipo de transacao correspondente
     */
    private TipoTransacao obterTipoTransacao(TipoAutorizacaoPagamento tipoAutorizacao, boolean isEstorno) {
        TipoTransacao tipoTransacao;
        if(!isEstorno) {
            tipoTransacao = obterTipoTransacaoAutorizacao(tipoAutorizacao);
        } else {
            tipoTransacao = obterTipoTransacaoEstorno(tipoAutorizacao);
        }
        return tipoTransacao;
    }

    /**
     * Obtem a enumeracao TipoTransacao de estorno de acordo com o tipo de autorizacao de pagamento informado
     * @param tipoAutorizacao O tipo da autorizacao
     * @return O TipoTransacao correspondente
     */
    private TipoTransacao obterTipoTransacaoEstorno(TipoAutorizacaoPagamento tipoAutorizacao) {
        if(tipoAutorizacao.getEstorno()!=null){
            return tipoAutorizacao.getEstorno();
        }
        else{
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("autorizador.servico.validacao.meioPagamento.invalido", tipoAutorizacao));
        }
    }

    /**
     * Obtem a enumeracao TipoTransacao de acordo com o tipo de autorizacao de pagamento informado
     * @param tipoAutorizacao O tipo da autorizacao
     * @return O TipoTransacao correspondente
     */
    private TipoTransacao obterTipoTransacaoAutorizacao(TipoAutorizacaoPagamento tipoAutorizacao) {
        if (tipoAutorizacao != null && tipoAutorizacao.getAutorizacao() != null) {
            return tipoAutorizacao.getAutorizacao();
        } else {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("autorizador.servico.validacao.meioPagamento.invalido", tipoAutorizacao));
        }
    }

    /**
     * Verifica se a frota pode realizar movimentacoes financeiras de acordo com sua modalidade de pagamento
     * @param frota A frota
     * @param modalidade exigida
     */
    private void verificarModalidadePagamento(Frota frota, ModalidadePagamento modalidade) {
        if(!modalidade.getValue().equals(frota.getModoPagamento())) {
            throw new ExcecaoBoleiaRuntime(Erro.OPERACAO_NAO_PERMITIDA, mensagens.obterMensagem("operacao.nao.permitida.transacao.frota.modalidade"));
        }
    }

    /**
     * Edita o saldo de uma frota ao editar/alterar um abastecimento
     * @param abastecimentoAlterado abastecimento {@link AutorizacaoPagamento} com os novos valores e alterações
     * @param abastecimentoHistorico abastecimento edição {@link AutorizacaoPagamentoEdicao} com os valores anteriores a edição
     */
    public void alterarTransacaoSaldoFrota(AutorizacaoPagamento abastecimentoAlterado, AutorizacaoPagamentoEdicao abastecimentoHistorico){

        BigDecimal valorDaTransacaoDeCompensasao = abastecimentoHistorico.getValorTotal();
        TransacaoFrota transacaoFrotaAntiga = this.registrarTransacaoSemVerificarSaldo(abastecimentoHistorico.getFrota(),
                valorDaTransacaoDeCompensasao, TipoTransacao.EDICAO_AUTORIZACAO_PAGAMENTO, null, null);
        transacaoFrotaAntiga.setAutorizacaoPagamento(null);

        BigDecimal valorDaTransacaoDeDescontoParaCompensasao = abastecimentoHistorico.getValorDescontoTotal();
        this.registrarTransacaoSemVerificarSaldo(abastecimentoHistorico.getFrota(),
                valorDaTransacaoDeDescontoParaCompensasao, TipoTransacao.EDICAO_AUTORIZACAO_PAGAMENTO, null, null);

        TipoAutorizacaoPagamento tipoAutorizacaoPagamento = TipoAutorizacaoPagamento.obterPorValor(abastecimentoAlterado.getTipoAutorizacaoPagamento());
        TipoTransacao tipoTransacao = obterTipoTransacao(tipoAutorizacaoPagamento, false);
        BigDecimal valorDaNovaTransacao = abastecimentoAlterado.getValorTotal();
        TransacaoFrota novaTransacaoFrota = this.registrarTransacaoSemVerificarSaldo(abastecimentoAlterado.getFrota(),
                valorDaNovaTransacao, tipoTransacao, null, null);
        abastecimentoAlterado.setTransacaoFrota(novaTransacaoFrota);
        novaTransacaoFrota.setAutorizacaoPagamento(abastecimentoAlterado);
    }
}