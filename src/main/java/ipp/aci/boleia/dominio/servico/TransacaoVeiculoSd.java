package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IItemAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.ISaldoVeiculoDados;
import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Executa operacoes de transacao financeira das frotas
 */
@Component
public class TransacaoVeiculoSd {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private ISaldoVeiculoDados repositorioSaldo;

    @Autowired
    private IVeiculoDados repositorioVeiculo;

    @Autowired
    private IItemAutorizacaoPagamentoDados repositorioItemAutorizacaoPagamento;

    @Autowired
    private HistoricoSaldoVeiculoSd historicoSaldoVeiculoSd;

    /**
     * Registra uma transacao de abastecimento de um veiculo, debitando o valor correspondente do saldo de sua cota mensal
     *
     * @param autorizacaoPagamento A autorizacao de pagamento a ser registrada
     * @return O saldo do veiculo atualizado
     */
    public SaldoVeiculo registrarTransacaoAbastecimentoVeiculo(AutorizacaoPagamento autorizacaoPagamento) {
        return registrarTransacaoVeiculo(autorizacaoPagamento, true);
    }

    /**
     * Estorna uma transacao de abastecimento de um veiculo, creditando o valor correspondente ao saldo de sua cota mensal
     *
     * @param autorizacaoPagamento A autorizacao de pagamento a ser registrada
     * @return O saldo do veiculo atualizado
     */
    public SaldoVeiculo estornarTransacaoAbastecimentoVeiculo(AutorizacaoPagamento autorizacaoPagamento) {
        return registrarTransacaoVeiculo(autorizacaoPagamento, false);
    }

    /**
     * Registra uma transacao, atualizando o saldo da cota mensal do veiculo
     *
     * @param autorizacaoPagamento A autorizacao de pagamento a ser registrada
     * @param debito True se a transacao deve reduzir o saldo corrente do veiculo
     * @return O saldo do veiculo atualizado
     */
    private SaldoVeiculo registrarTransacaoVeiculo(AutorizacaoPagamento autorizacaoPagamento, boolean debito) {
        Veiculo veiculo = autorizacaoPagamento.getVeiculo();
        SaldoVeiculo saldo = veiculo.getSaldoVeiculo();
        if(saldo == null) {
            saldo = new SaldoVeiculo();
            saldo.setVeiculo(veiculo);
            saldo = repositorioSaldo.armazenar(saldo);
        }
        saldo = registraTransacao(autorizacaoPagamento, debito, veiculo, saldo);
        armazenarDados(veiculo, saldo);
        return saldo;
    }

    /**
     * Valida o valor recebido antes de registrar a transacao
     * @param valorUnitario O valor unitario do abastecimento
     * @param litrosAbastecidos O numero de litros do abastecimento
     */
    private void validarValorTransacao(BigDecimal valorUnitario, BigDecimal litrosAbastecidos) {
        if (valorUnitario == null || litrosAbastecidos == null || valorUnitario.compareTo(BigDecimal.ZERO) <= 0 || litrosAbastecidos.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcecaoBoleiaRuntime(Erro.TRANSACAO_VALOR_INVALIDO);
        }
    }

    /**
     * Verifica se o abastecimento aconteceu no mes corrente
     *
     * @param dataAutorizacao A data da autorizacao do abastecimento
     * @return true caso tenha ocorrido no mes corrente
     */
    private boolean mesCorrente(Date dataAutorizacao) {
        Date diaPrimeiro = UtilitarioCalculoData.obterPrimeiroDiaMes(dataAutorizacao);
        return dataAutorizacao.compareTo(diaPrimeiro) >= 0;
    }

    /**
     * Registra a transacao do veiculo, abatendo o valor correspondente em seu saldo, caso seja veiculo agregado
     * ou o abastecimento tenha ocorrido no mes atual
     *
     * @param autorizacaoPagamento O abastecimento
     * @param debito True caso seja um debito
     * @param veiculo O veiculo em questao
     * @param saldo O saldo do veiculo
     * @return O saldo do veiculo atualizado
     */
    private SaldoVeiculo registraTransacao(AutorizacaoPagamento autorizacaoPagamento, boolean debito, Veiculo veiculo, SaldoVeiculo saldo) {
        if (!veiculo.isProprio()){
            saldo = registraTransacaoVeiculoAgregado(autorizacaoPagamento, debito, saldo);
        } else if(mesCorrente(autorizacaoPagamento.getDataProcessamento())) {
            saldo = registraTransacaoMesCorrente(autorizacaoPagamento, debito, saldo);
        }
        veiculo.setSaldoVeiculo(saldo);
        return saldo;
    }



    /**
     * Registra a transacao do veiculo, abatendo o valor correspondente em seu saldo, no caso de
     * um abastecimento que tenha ocorrido no mes atual
     *
     * @param autorizacaoPagamento O abastecimento
     * @param debito True caso seja um debito
     * @param saldo O saldo do veiculo
     * @return O saldo do veiculo atualizado
     */
    private SaldoVeiculo registraTransacaoMesCorrente(AutorizacaoPagamento autorizacaoPagamento, boolean debito, SaldoVeiculo saldo) {
        BigDecimal valorConsumidoAtual = calcularValorConsumidoAtual(autorizacaoPagamento, debito, saldo);
        if (valorConsumidoAtual.compareTo(BigDecimal.ZERO) >= 0) {
            saldo.setValorConsumido(valorConsumidoAtual);
        }
        BigDecimal litrosConsumidosAtual = saldo.getLitrosConsumidos() != null ? saldo.getLitrosConsumidos() : BigDecimal.ZERO;
        litrosConsumidosAtual = litrosConsumidosAtual.add(debito ?
                autorizacaoPagamento.getTotalLitrosAbastecimento() :
                autorizacaoPagamento.getTotalLitrosAbastecimento().negate());
        if (litrosConsumidosAtual.compareTo(BigDecimal.ZERO) >= 0) {
            saldo.setLitrosConsumidos(litrosConsumidosAtual);
        }
        return saldo;
    }


    /**
     * Registra a transacao do veiculo, abatendo o valor correspondente em seu saldo no caso de um veiculo agregado
     *
     * @param autorizacaoPagamento O abastecimento
     * @param debito True caso seja um debito
     * @param saldo O saldo do veiculo
     * @return O saldo do veiculo atualizado
     */
    private SaldoVeiculo registraTransacaoVeiculoAgregado(AutorizacaoPagamento autorizacaoPagamento, boolean debito, SaldoVeiculo saldo) {
        if (!veiculoAgregadoAcumulaSaldo(autorizacaoPagamento, saldo))
            return saldo;

        BigDecimal valorConsumidoAtual = calcularValorConsumidoAtual(autorizacaoPagamento, debito, saldo);
        valorConsumidoAtual = valorConsumidoAtual.add(calcularValorAgregadoExtra(autorizacaoPagamento));
        if (valorConsumidoAtual.compareTo(BigDecimal.ZERO) >= 0) {
            saldo.setValorConsumido(valorConsumidoAtual);
        }
        return saldo;
    }

    /**
     * Verifica se o Parametro CREDITO_VEICULO_AGREGADO está Ativo
     * E se a Cota do veiculo possui valor
     *
     * @param autorizacaoPagamento O abastecimento
     * @param saldo O saldo do veiculo
     * @return true para acumular saldo
     */
    private boolean veiculoAgregadoAcumulaSaldo(AutorizacaoPagamento autorizacaoPagamento, SaldoVeiculo saldo) {
        Frota frota = autorizacaoPagamento.getFrota();
        if (frota != null && frota.getParametrosSistema() != null && !frota.getParametrosSistema().isEmpty()) {
            FrotaParametroSistema creditoVeiculoAgregado = frota.getParametrosSistema().stream()
                    .filter(p-> p.getParametroSistema() == ParametroSistema.CREDITO_VEICULO_AGREGADO.getCodigo() ).findFirst().orElse(null);
            if (creditoVeiculoAgregado != null && creditoVeiculoAgregado.getAtivo() && saldo.getCotaValor() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcula o valor consumido atual.
     *
     * @param autorizacaoPagamento O abastecimento
     * @param debito True caso seja um debito
     * @param saldo O saldo do veiculo
     * @return O saldo do veiculo atualizado
     */
    private BigDecimal calcularValorConsumidoAtual(AutorizacaoPagamento autorizacaoPagamento, boolean debito, SaldoVeiculo saldo){
        BigDecimal valorTotal = autorizacaoPagamento.getValorTotalAbastecimento();
        validarValorTransacao(autorizacaoPagamento.getValorUnitarioAbastecimento(), autorizacaoPagamento.getTotalLitrosAbastecimento());
        BigDecimal valorConsumidoAtual = saldo.getValorConsumido() != null ? saldo.getValorConsumido() : BigDecimal.ZERO;
        BigDecimal valorDesconto = autorizacaoPagamento.getValorDescontoTotal() != null? autorizacaoPagamento.getValorDescontoTotal():BigDecimal.ZERO;
        valorTotal = valorTotal.subtract(valorDesconto);
        return valorConsumidoAtual.add(debito ? valorTotal : valorTotal.negate());
    }
    /**
     * Soma os valores dos produtos consumidos em um abastecimento.
     *
     * @param autorizacaoPagamento O abastecimento
     * @return O saldo do veiculo atualizado
     */
    private BigDecimal calcularValorAgregadoExtra(AutorizacaoPagamento autorizacaoPagamento) {
        return repositorioItemAutorizacaoPagamento.obterItensPorIdAutorizacao(autorizacaoPagamento.getId())
                .stream().filter(i->!i.isAbastecimento())
                .map(i ->  i.getValorTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

     /**
     * Armazena o veículo e o saldo em seus respectivos repositórios
     *
     * @param veiculo veiculo a ser armazenado.
     * @param saldo saldo atualizado a ser armazenado.
     */
    private void armazenarDados(Veiculo veiculo, SaldoVeiculo saldo) {
        veiculo.setSaldoVeiculo(saldo);
        repositorioVeiculo.armazenar(veiculo);
        repositorioSaldo.armazenar(saldo);
        //historicoSaldoVeiculoSd.criarHistoricoSaldo(saldo, ambiente);
    }

}