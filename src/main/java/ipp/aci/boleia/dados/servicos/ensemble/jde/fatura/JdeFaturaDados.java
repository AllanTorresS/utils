package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura;

import ipp.aci.boleia.dados.IFaturaDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.JdeBaseDados;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ConsultarJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ConsultarJDEResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.EnsRequest;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.EnsResponse;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.FaturaSoap;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.Fatura_Service;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.IncluirJDEResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.PairOflistaItemFaturaKeyitemFatura;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ProrrogarVencimentoReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws.ProrrogarVencimentoResp;
import ipp.aci.boleia.dominio.AjusteCobranca;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.ItemAjusteCobranca;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioIntegracao;
import ipp.aci.boleia.util.UtilitarioIntegracao.ConsumidorIntegracao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.jde.ConstantesJde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataGregorian;
import static ipp.aci.boleia.util.jde.ConstantesJde.BOLEIA;
import static ipp.aci.boleia.util.jde.ConstantesJde.CLIENTE_REPASSE;
import static ipp.aci.boleia.util.jde.ConstantesJde.CONDICAO_PGTO_REPASSE;
import static ipp.aci.boleia.util.jde.ConstantesJde.CREDITO_FROTA_CENARIO;
import static ipp.aci.boleia.util.jde.ConstantesJde.CREDITO_FROTA_OBSERVACAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.CREDITO_FROTA_TIPO_DOCUMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.FATURA_CENARIO;
import static ipp.aci.boleia.util.jde.ConstantesJde.FATURA_REPASSE_OBSERVACAO;
import static ipp.aci.boleia.util.jde.ConstantesJde.FATURA_REPASSE_TIPO_DOCUMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.FATURA_SISTEMA_GERADOR;
import static ipp.aci.boleia.util.jde.ConstantesJde.FATURA_TIPO_DOCUMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.FILIAL;
import static ipp.aci.boleia.util.jde.ConstantesJde.FILIAL_FABRICA_REPASSE;

/**
 * Implementação do integração com JDE para {@link IFaturaDados}
 * 
 */
@Repository
@Scope("singleton")
public class JdeFaturaDados extends JdeBaseDados<FaturaSoap> implements IFaturaDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeFaturaDados.class);
    private static final String NOME_SERVICO = "faturaJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/fatura.wsdl";
    private static final String STATUS_PROP = "isStatus";
    private static final String ERRO_MSG_PROP = "getMsgErro";
    private static final String DESC_ERRO_PROP = "getDescricaoErro";
    private static final String STATUS_PAGAMENTO_PAGO = "P";
    private static final String PREFIXO_ITEM_AJUSTE = "ajuste";
    private static final BigDecimal INDICE_CONTA_RESPONSABILIDADE_PROFROTAS = new BigDecimal(1);
    private static final BigDecimal INDICE_CONTA_RESPONSABILIDADE_IPIRANGA = new BigDecimal(2);

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(getJdeLogin(), getJdeSenha(), NOME_SERVICO);
    }

    @Override
    protected FaturaSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlJdeCompleta());
        Fatura_Service fatura = new Fatura_Service(url);
        return fatura.getFaturaSoap();
    }

    /**
     * Dado o endereco do wsdl, converte-o em um caminho absoluto
     *
     * @return url JDE completa
     */
    private String obterUrlJdeCompleta() {
        return this.getClass().getResource(ARQUIVO_JDE_WSDL).toString();
    }

    @Override
    public IncluirJDEResp incluir(IncluirJDEReq requisicao) {
        try {
            return invocarIntegracao(requisicao, "incluir", getServico()::incluirJDE);
        } catch (ExcecaoValidacao | ExcecaoBoleiaRuntime ex) {
            IncluirJDEResp respostaErro = new IncluirJDEResp();
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());

            LOGGER.error(mensagem, ex);
            respostaErro.setStatus(false);
            respostaErro.setMsgErro(mensagem);

            return respostaErro;
        }
    }

    @Override
    public PedidoCreditoFrota incluir(PedidoCreditoFrota pedido) {
        IncluirJDEReq requisicao = obterRequisicaoIncluir(pedido);
        try {
            IncluirJDEResp resposta = invocarIntegracao(requisicao, "incluir", getServico()::incluirJDE);
            pedido.setNumeroDocumentoJde(resposta.getNumeroDocumento().longValue());
            pedido.setMensagemErroJde(null);
            pedido.setStatusJde(StatusIntegracaoJde.REALIZADO.getValue());
            pedido.setCiaDocumentoJde(resposta.getCiaDocumento());
        } catch (ExcecaoValidacao | ExcecaoBoleiaRuntime ex) {
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());
            LOGGER.error(mensagem, ex);
            pedido.setMensagemErroJde(mensagem);
            pedido.setStatusJde(StatusIntegracaoJde.ERRO_ENVIO.getValue());
        }
        return pedido;
    }

    @Override
    public Cobranca consultarCobranca(Cobranca cobranca) {
        try {
            ConsultarJDEReq requisicao = obterRequisicaoConsultar(cobranca);
            ConsultarJDEResp resposta = invocarIntegracao(requisicao, "consultarCobranca", getServico()::consultarJDE);
            if(resposta.getDataVenc() != null) {
                cobranca.setDataVencimentoPagto(resposta.getDataVenc().toGregorianCalendar().getTime());
            }
            Date dataAmbiente = getUtilitarioAmbiente().buscarDataAmbiente();
            if (STATUS_PAGAMENTO_PAGO.equalsIgnoreCase(resposta.getStatusPgto())) {
                cobranca.setDataPagamento(dataAmbiente);
                cobranca.setStatus(StatusPagamentoCobranca.PAGO.getValue());
                cobranca.setStatusIntegracaoJDE(StatusIntegracaoJde.REALIZADO.getValue());
            } else if (cobranca.getDataVencimentoPagto() != null
                    && dataAmbiente.after(UtilitarioCalculoData.obterUltimoInstanteDia(cobranca.getDataVencimentoPagto()))) {
                cobranca.setStatus(StatusPagamentoCobranca.VENCIDO.getValue());
            } else {
                cobranca.setStatus(StatusPagamentoCobranca.EM_ABERTO.getValue());
            }
        } catch (ExcecaoValidacao | ExcecaoBoleiaRuntime ex) {
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());
            LOGGER.error(mensagem, ex);
            cobranca.setMensagemErro(mensagem);
        }

        return cobranca;
    }

    @Override
    public List<CicloRepasse> incluir(List<CicloRepasse> ciclosRepasse) {
        IncluirJDEReq requisicao = obterRequisicaoIncluir(ciclosRepasse);
        try {
            IncluirJDEResp resposta = invocarIntegracao(requisicao, "incluir", getServico()::incluirJDE);
            ciclosRepasse.forEach((cicloRepasse) -> {
                cicloRepasse.setNumeroDocumento(resposta.getNumeroDocumento().intValue());
                cicloRepasse.setMensagemErroJDE(null);
                cicloRepasse.setStatusIntegracaoJDE(StatusIntegracaoJde.REALIZADO.getValue());
                cicloRepasse.setCiaDocumento(resposta.getCiaDocumento());
            });
        } catch (ExcecaoValidacao | ExcecaoBoleiaRuntime ex) {
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());
            ciclosRepasse.forEach((cicloRepasse) -> {
                cicloRepasse.setMensagemErroJDE(mensagem);
                cicloRepasse.setStatusIntegracaoJDE(StatusIntegracaoJde.ERRO_ENVIO.getValue());
            });
            LOGGER.error(mensagem, ex);
        }
        return ciclosRepasse;
    }

    @Override
    public CicloRepasse consultarCicloRepasse(CicloRepasse cicloRepasse) {
        try {
            ConsultarJDEReq requisicao = obterRequisicaoConsultarCiclo(cicloRepasse);
            ConsultarJDEResp resposta = invocarIntegracao(requisicao, "consultarCobranca", getServico()::consultarJDE);
            if(resposta.getDataVenc() != null) {
                cicloRepasse.setDataVencimento(resposta.getDataVenc().toGregorianCalendar().getTime());
            }
            Date dataAmbiente = getUtilitarioAmbiente().buscarDataAmbiente();
            if (STATUS_PAGAMENTO_PAGO.equalsIgnoreCase(resposta.getStatusPgto())) {
                cicloRepasse.setDataPagamento(dataAmbiente);
                cicloRepasse.setStatus(StatusPagamentoCobranca.PAGO.getValue());
                cicloRepasse.setStatusIntegracaoJDE(StatusIntegracaoJde.REALIZADO.getValue());
            } else if (cicloRepasse.getDataVencimento() != null
                    && dataAmbiente.after(UtilitarioCalculoData.obterUltimoInstanteDia(cicloRepasse.getDataVencimento()))) {
                cicloRepasse.setStatus(StatusPagamentoCobranca.VENCIDO.getValue());
            } else {
                cicloRepasse.setStatus(StatusPagamentoCobranca.EM_ABERTO.getValue());
            }
        } catch (ExcecaoValidacao | ExcecaoBoleiaRuntime ex) {
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());
            LOGGER.error(mensagem, ex);
            cicloRepasse.setMensagemErroJDE(mensagem);
        }

        return cicloRepasse;
    }

    @Override
    public void prorrogarVencimento(AjusteCobranca ajusteCobranca) throws ExcecaoValidacao {
        ProrrogarVencimentoReq requisicao = criarRequisicaoProrrogarVencimento(ajusteCobranca);
        enviarRequisicaoProrrogarVencimento(requisicao);
    }

    /**
     * Envia a requisição para prorrogar o vencimento do boleto.
     *
     * @param requisicao para ser enviada
     * @throws ExcecaoValidacao caso ocorra erro durante o processo.
     */
    private void enviarRequisicaoProrrogarVencimento(ProrrogarVencimentoReq requisicao) throws ExcecaoValidacao {
        ProrrogarVencimentoResp response = invocarIntegracao(requisicao, "prorrogarVencimento", getServico()::prorrogarVencimento);
        if (! response.isStatus()) {
            String mensagem = getMensagens().obterMensagem(Erro.ERRO_INTEGRACAO.getChaveMensagem(), NOME_SERVICO + ".prorrogarVencimento: " + response.getMensagem());
            LOGGER.error(mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, NOME_SERVICO + ".prorrogarVencimento");
        }
    }

    /**
     * Cria o objeto com as informações da requisição do serviço de prorrogar o vencimento.
     *
     * @param ajusteCobranca O ajuste realizado na cobrança
     * @return Objeto da requisição.
     */
    private ProrrogarVencimentoReq criarRequisicaoProrrogarVencimento(AjusteCobranca ajusteCobranca) {
        ProrrogarVencimentoReq req = new ProrrogarVencimentoReq();
        req.setTipo(ajusteCobranca.getCobranca().getTipoDocumento());
        req.setDocumento(new BigDecimal(ajusteCobranca.getCobranca().getNumeroDocumento()));
        req.setCompanhia(ajusteCobranca.getCobranca().getCiaDocumento());
        req.setSufixo(ConstantesJde.SUFIXO_PRORROGAR_VENCIMENTO);
        req.setAcrecimo(ConstantesJde.ACRECIMO_PRORROGAR_VENCIMENTO);
        req.setVencimento(formatarDataGregorian(ajusteCobranca.getDataVencimentoAjuste()));
        req.setAlteracao(ConstantesJde.ALTERACAO_PRORROGAR_VENCIMENTO);
        req.setAprovacao(formatarDataGregorian(ajusteCobranca.getDataAjuste()));
        return req;
    }

    /**
    * Método que consome método da api e trata exceção.
    *
    * @param requisicao Requisição a ser enviada.
    * @param nomeServico Nome do serviço a ser chamado.
    * @param consumidor Consumidor do serviço
    * @return Reposta da integração
    * @throws ExcecaoBoleiaRuntime caso ocorra um erro de integração
    */
    private <E extends EnsRequest, S extends EnsResponse> S invocarIntegracao(E requisicao, String nomeServico, ConsumidorIntegracao<E, S> consumidor) throws ExcecaoValidacao  {
        S resp = UtilitarioIntegracao.invocarIntegracao(requisicao, NOME_SERVICO + "." + nomeServico, consumidor);
        validarResposta(resp, nomeServico);
        return resp;
    }

    /**
    * Método que valida resposta para verificar erros de validação no serviço.
    * @param resposta Resposta
     * @param nomeServico O nome do serviço a ser validado
     * @throws ExcecaoValidacao Em caso de erro de validação
    */
    private <S extends EnsResponse> void validarResposta(S resposta, String nomeServico) throws ExcecaoValidacao {
        Method statusMethod = BeanUtils.findMethod(resposta.getClass(), STATUS_PROP);
        Method erroMsgMethod = BeanUtils.findMethod(resposta.getClass(), ERRO_MSG_PROP);
        Method descErrorMethod = BeanUtils.findMethod(resposta.getClass(), DESC_ERRO_PROP);
        if(statusMethod != null && (erroMsgMethod != null || descErrorMethod != null) ){
            try {
                if(statusMethod.invoke(resposta).equals(false)) {
                    String message = null;
                    
                    if( erroMsgMethod != null ) {
                        message = erroMsgMethod.invoke(resposta)  != null ? (String) erroMsgMethod.invoke(resposta) : null;
                    }

                    if((message == null || message.isEmpty()) && descErrorMethod != null) {
                        message = (String) descErrorMethod.invoke(resposta);
                    }

                    throw new ExcecaoValidacao(message);
                }
            } catch (IllegalAccessException | InvocationTargetException ex) {
                throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + "." + nomeServico);
            }
        }
    }

    /**
     * Cria uma requisicao para inclusao de credito da frota no JDE.
     *
     * @param pedido O pedido de credito de frota
     * @return Um objeto de requisicao conforme o contrato de uso do JDE
     */
    private IncluirJDEReq obterRequisicaoIncluir(PedidoCreditoFrota pedido) {
        Date dataAmbiente = getUtilitarioAmbiente().buscarDataAmbiente();
        IncluirJDEReq req = new IncluirJDEReq();
        req.setCenario(new BigDecimal(CREDITO_FROTA_CENARIO));
        req.setFilialFabrica(FILIAL);
        req.setTipoDocumento(CREDITO_FROTA_TIPO_DOCUMENTO);
        req.setSistemaGeradorFatura(BOLEIA);
        req.setCliente(new BigDecimal(pedido.getFrota().getNumeroJdeInterno()));
        req.setDataFatura(formatarDataGregorian(dataAmbiente));
        req.setObservacao(CREDITO_FROTA_OBSERVACAO);
        req.setListaItemFatura(obterListaItensFatura(pedido));
        return req;
    }

    /**
     * Cria uma requisicao para inclusao de ciclo de repasse no JDE.
     * @param ciclosRepasse o ciclo de repasse em questão
     * @return Um objeto de requisicao conforme o contrato de uso do JDE
     */
    private IncluirJDEReq obterRequisicaoIncluir(List<CicloRepasse> ciclosRepasse) {
        IncluirJDEReq req = new IncluirJDEReq();
        String delimitador = " ";
        CicloRepasse ciclo = ciclosRepasse.stream().findAny().get();
        req.setCenario(new BigDecimal(FATURA_CENARIO));
        req.setFilialFabrica(FILIAL_FABRICA_REPASSE);
        req.setCliente(new BigDecimal(CLIENTE_REPASSE));
        req.setTipoDocumento(FATURA_REPASSE_TIPO_DOCUMENTO);
        req.setDataFatura(obterDataFatura(ciclo));
        req.setObservacao(String.join(delimitador, UtilitarioFormatacaoData.formatarDataMesAno(ciclo.getDataFim()), FATURA_REPASSE_OBSERVACAO));
        req.setSistemaGeradorFatura(FATURA_SISTEMA_GERADOR);
        req.setCondicaoPgto(CONDICAO_PGTO_REPASSE);
        req.setListaItemFatura(obterListaItensFatura(ciclosRepasse));
        return req;
    }

    /**
     * Cria uma lista de itens de fatura a partir de um objeto pedidoCreditoFrota
     *
     * @param pedido O pedido de credito da frota em questao
     * @return Uma instancia de {@link ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura}, contendo o valor do credito do pedido.
     */
    private ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura obterListaItensFatura(PedidoCreditoFrota pedido) {
        ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura listaItensFatura = new ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura();
        PairOflistaItemFaturaKeyitemFatura item = new PairOflistaItemFaturaKeyitemFatura();
        item.setValorTransacao(pedido.getValorPedido());
        item.setListaItemFaturaKey("1");
        listaItensFatura.getItemFatura().add(item);
        return listaItensFatura;
    }

    @Override
    public IncluirJDEReq obterRequisicaoIncluir(Cobranca cobranca, Cobranca cobrancaAnterior) {
        IncluirJDEReq req = new IncluirJDEReq();
        req.setCenario(new BigDecimal(FATURA_CENARIO));
        req.setFilialFabrica(FILIAL);
        req.setTipoDocumento(FATURA_TIPO_DOCUMENTO);
        req.setSistemaGeradorFatura(FATURA_SISTEMA_GERADOR);
        req.setCliente(obterNumeroInterno(cobranca));
        req.setDataFatura(obterDataFatura(cobranca));
        req.setListaItemFatura(obterListaItensFatura(cobranca, cobrancaAnterior));
        req.setObservacao(UtilitarioFormatacaoData.formatarDataMesAno(cobranca.getDataFimPeriodo()));
        return req;
    }

    /**
     * Cria uma requisicao para consulta de cobrancas no JDE.
     *
     * @param cobranca A cobranca a ser consultada
     * @return Um objeto de requisicao conforme o contrato de uso do JDE
     */
    private ConsultarJDEReq obterRequisicaoConsultar(Cobranca cobranca) {
        ConsultarJDEReq req = new ConsultarJDEReq();
        req.setTipoDocumento(cobranca.getTipoDocumento().toUpperCase());
        req.setNumeroDocumento(new BigDecimal(cobranca.getNumeroDocumento()));
        req.setCompanhiaDocumento(cobranca.getCiaDocumento().toUpperCase());
        req.setParcela(UtilitarioFormatacao.formatarNumeroZerosEsquerda(cobranca.getQuantidadeParcelas(), 3));
        return req;
    }

    /**
     * Cria uma requisicao para consulta de cobrancas no JDE.
     *
     * @param cicloRepasse A cobranca a ser consultada
     * @return Um objeto de requisicao conforme o contrato de uso do JDE
     */
    private ConsultarJDEReq obterRequisicaoConsultarCiclo(CicloRepasse cicloRepasse) {
        ConsultarJDEReq req = new ConsultarJDEReq();
        req.setTipoDocumento(ConstantesJde.FATURA_REPASSE_TIPO_DOCUMENTO);
        req.setNumeroDocumento(new BigDecimal(cicloRepasse.getNumeroDocumento()));
        req.setCompanhiaDocumento(cicloRepasse.getCiaDocumento());
        return req;
    }

    /**
     * Retorna o numero interno (JDE) de uma Cobranca
     *
     * @param cobranca A Cobranca em questao
     * @return O numero interno da Cobranca
     */
    private BigDecimal obterNumeroInterno(Cobranca cobranca) {
        List<Frota> frotas = cobranca.getFrotas();
        Frota frota = frotas.get(0);
        Integer numeroInterno = frota.getNumeroJdeInterno();
        return new BigDecimal(numeroInterno);
    }

    /**
     * Cria uma lista de itens de fatura a partir de um objeto Cobranca
     *
     * @param cobranca A cobranca em questão
     * @param cobrancaAnterior A cobrança anterior à cobrança em questão
     * @return Uma instancia de {@link ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura}, contendo os itens da Cobranca informada.
     */
    private ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura obterListaItensFatura(Cobranca cobranca, Cobranca cobrancaAnterior) {
        ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura listaItensFatura = new ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura();
        AtomicInteger contadorItemFatura = new AtomicInteger(0);
        List<PairOflistaItemFaturaKeyitemFatura> itens = cobranca.getTransacoesConsolidadas()
                .stream()
                .map(tc -> this.obterItemFatura(tc, contadorItemFatura))
                .filter(it -> it.getValorTransacao().compareTo(BigDecimal.ZERO) != 0)
                .collect(Collectors.toList());

        if (!cobranca.getValorDescontoAbastecimentos().equals(BigDecimal.ZERO)){
            itens = incluiIndicesConta(itens, cobranca, contadorItemFatura);
        }

        listaItensFatura.getItemFatura().addAll(itens);

        if (cobrancaAnterior != null && cobrancaAnterior.getAjustes() != null) {
            for (AjusteCobranca ajuste : cobrancaAnterior.getAjustes()) {
                List<PairOflistaItemFaturaKeyitemFatura> itensAjuste = ajuste.getItensAjuste().stream().map(this::obterItemFaturaAjuste).collect(Collectors.toList());
                listaItensFatura.getItemFatura().addAll(itensAjuste);
            }
        }

        return listaItensFatura;
    }

    /**
     * Cria um {@link PairOflistaItemFaturaKeyitemFatura} a partir de um item de ajuste de cobrança
     *
     * @param itemAjuste O item de ajuste em questão
     * @return Uma instância de {@link PairOflistaItemFaturaKeyitemFatura} contendo os dados do item de ajuste informado
     */
    private PairOflistaItemFaturaKeyitemFatura obterItemFaturaAjuste(ItemAjusteCobranca itemAjuste) {
        PairOflistaItemFaturaKeyitemFatura item = new PairOflistaItemFaturaKeyitemFatura();
        Integer codigoPosto = itemAjuste.getAutorizacaoPagamento().getPontoVenda().getNumeroJdeInterno();
        item.setValorTransacao(itemAjuste.obterValorItemAjuste().negate());
        item.setListaItemFaturaKey(PREFIXO_ITEM_AJUSTE.concat(String.valueOf(itemAjuste.getId())));
        item.setCodigoPosto(codigoPosto.toString());
        return item;
    }

    /**
     * Adiciona itens à fatura a ser enviada
     * @param itens A lista de itens a serem adicionados
     * @param cobranca A entidade da fatura
     * @param contadorItemFatura O contador de items na fatura
     * @return Uma lista com pares de chave e items da fatura
     */
    private List<PairOflistaItemFaturaKeyitemFatura> incluiIndicesConta(List<PairOflistaItemFaturaKeyitemFatura> itens, Cobranca cobranca, AtomicInteger contadorItemFatura){
        String codigoPosto = itens.get(itens.size() - 1).getCodigoPosto();

        PairOflistaItemFaturaKeyitemFatura descontoProFrotas = new PairOflistaItemFaturaKeyitemFatura();
        descontoProFrotas.setListaItemFaturaKey(String.valueOf(contadorItemFatura.incrementAndGet()));
        descontoProFrotas.setIndiceConta(INDICE_CONTA_RESPONSABILIDADE_PROFROTAS);
        descontoProFrotas.setValorTransacao(cobranca.getValorDescontoAbastecimentos().divide(new BigDecimal(2)).negate());
        descontoProFrotas.setCodigoPosto(codigoPosto);

        PairOflistaItemFaturaKeyitemFatura descontoIpiranga = new PairOflistaItemFaturaKeyitemFatura();
        descontoIpiranga.setListaItemFaturaKey(String.valueOf(contadorItemFatura.incrementAndGet()));
        descontoIpiranga.setIndiceConta(INDICE_CONTA_RESPONSABILIDADE_IPIRANGA);
        descontoIpiranga.setValorTransacao(cobranca.getValorDescontoAbastecimentos().divide(new BigDecimal(2)).negate());
        descontoIpiranga.setCodigoPosto(codigoPosto);

        itens.add(descontoProFrotas);
        itens.add(descontoIpiranga);

        return itens;
    }

    /**
     * Cria uma lista de itens de fatura a partir de um conjunto de ciclos de repasse
     *
     * @param ciclos os ciclos de repasse a serem enviados
     * @return Uma instancia de {@link ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura}, contendo os ciclos de repasse.
     */
    private ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura obterListaItensFatura(List<CicloRepasse> ciclos) {
        final Integer INICIO_CONTADOR = 1;
        ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura listaItensFatura = new ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura();
        AtomicInteger contador = new AtomicInteger(INICIO_CONTADOR);
        List<PairOflistaItemFaturaKeyitemFatura> itens = ciclos
                .stream()
                .map(c -> obterItemFatura(c, contador))
                .collect(Collectors.toList());
        listaItensFatura.getItemFatura().addAll(itens);
        return listaItensFatura;
    }

    /**
     * Cria um {@link PairOflistaItemFaturaKeyitemFatura} a partir de um ciclo de repasse
     * @param cicloRepasse O ciclo em questao
     * @param contador Contador para representar o indice (ListaItemFaturaKey)
     * @return Uma instancia de {@link PairOflistaItemFaturaKeyitemFatura} contendo os dados do ciclo informado
     */
    private PairOflistaItemFaturaKeyitemFatura obterItemFatura(CicloRepasse cicloRepasse, AtomicInteger contador) {
        PairOflistaItemFaturaKeyitemFatura item = new PairOflistaItemFaturaKeyitemFatura();
        item.setValorTransacao(cicloRepasse.getValorNominalRepasse());
        item.setListaItemFaturaKey(String.valueOf(contador.getAndIncrement()));
        item.setCodigoPosto(String.valueOf(cicloRepasse.getPontoDeVenda().getNumeroJdeInterno()));
        return item;
    }

    /**
     * Cria um {@link PairOflistaItemFaturaKeyitemFatura} a partir de uma Transacao Consolidada
     * @param transacao A Transacao em questao
     * @param contadorItemFatura O contador de items na fatura
     * @return Uma instancia de {@link PairOflistaItemFaturaKeyitemFatura} contendo os dados da Transacao inrofmada
     */
    private PairOflistaItemFaturaKeyitemFatura obterItemFatura(TransacaoConsolidada transacao, AtomicInteger contadorItemFatura) {
        PairOflistaItemFaturaKeyitemFatura item = new PairOflistaItemFaturaKeyitemFatura();
        item.setValorTransacao(transacao.getValorTotal());
        item.setListaItemFaturaKey(String.valueOf(contadorItemFatura.incrementAndGet()));
        item.setCodigoPosto(String.valueOf(transacao.getPontoVenda().getNumeroJdeInterno()));
        return item;
    }

    /**
     * Converte a data de fim de periodo de uma Cobranca em um objeto {@link XMLGregorianCalendar}
     * @param cobranca A Cobranca em questao
     * @return Um objeto {@link XMLGregorianCalendar} que representa a data de fim de periodo da Cobranca informada
     */
    private XMLGregorianCalendar obterDataFatura(Cobranca cobranca) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(cobranca.getTransacoesConsolidadas().get(0).getDataFimPeriodo());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
    }

    /**
     * Converte a data de fim de um ciclo de repasse em um objeto {@link XMLGregorianCalendar}
     * @param cicloRepasse O CicloRepasse em questao
     * @return Um objeto {@link XMLGregorianCalendar} que representa a data de fim de um ciclo de repasse informada
     */
    private XMLGregorianCalendar obterDataFatura(CicloRepasse cicloRepasse) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(cicloRepasse.getDataFim());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
    }


}