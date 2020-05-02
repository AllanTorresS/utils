package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher;

import ipp.aci.boleia.dados.IVoucherDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.JdeBaseDados;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.ArrayOfdistribuicaoContabildistribuicaoContabil;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.ArrayOfvouchervoucher;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.BuscarReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.BuscarResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.CriarReq;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.CriarResp;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.DistribuicaoContabil;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.Fornecedor;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.ResultadoVoucher;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.Voucher;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.VoucherSoap;
import ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws.Voucher_Service;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataGregorian;
import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;
import static ipp.aci.boleia.util.jde.ConstantesJde.COMPANHIA;
import static ipp.aci.boleia.util.jde.ConstantesJde.FILIAL;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CENARIO_APROVADO_PAGAMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CENARIO_SUSPENSO_PAGAMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_CODIGO_PAGAMENTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_INDICE_CONTA_CONTABIL_MDR;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_INDICE_CONTA_CONTABIL_POS_PAGO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_INDICE_CONTA_CONTABIL_PRE_PAGO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_OBSERVACAO_MDR_REEMBOLSO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_OBSERVACAO_POS_PAGO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_OBSERVACAO_PRE_PAGO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_TEMPO_HORA;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_TEMPO_MINUTO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VOUCHER_TEMPO_SEGUNDO;

/**
 * Implementação do integração com JDE para {@link IVoucherDados}
 * 
 */
@Repository
@Scope("singleton")
public class JdeVoucherDados extends JdeBaseDados<VoucherSoap> implements IVoucherDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeVoucherDados.class);
    private static final String NOME_SERVICO = "voucherJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/voucher.wsdl";
    private static final String STATUS_PAGAMENTO_PAGO = "P";

    @Override
    public Reembolso criar(Reembolso reembolso){
        try {
            CriarResp resposta = enviarRequisicaoCriar(obterRequisicaoCriar(reembolso));
            if (resposta.getStatusIntegracao().isStatus() && resposta.getNumeroDocumento().trim().length() > 1) {
                reembolso.setCiaDocumento(resposta.getCompanhiaDocumento());
                reembolso.setTipoDocumento(resposta.getTipoCodigoDocumento());
                reembolso.setNumeroDocumento(Long.parseLong(resposta.getNumeroDocumento()));
                reembolso.setQuantidadeParcelas(resposta.getListaItemVoucher().getItemVoucher().size());
                reembolso.setMensagemErro(null);

                if (reembolso.estaAprovadoParaPagamento()) {
                    reembolso.setStatusIntegracao(StatusIntegracaoReembolsoJde.REALIZADO.getValue());
                } else {
                    reembolso.setStatusIntegracao(StatusIntegracaoReembolsoJde.AGUARDANDO_LIBERACAO.getValue());
                }
            } else {
                reembolso.setMensagemErro(resposta.getStatusIntegracao().getMensagem());
                reembolso.setStatusIntegracao(StatusIntegracaoJde.ERRO_ENVIO.getValue());
            }
        } catch(ExcecaoBoleiaRuntime ex) {
            String mensagem = getMensagens().obterMensagem(ex.getErro().getChaveMensagem(), ex.getArgs());
            LOGGER.error(mensagem, ex);
            reembolso.setMensagemErro(mensagem);
            reembolso.setStatusIntegracao(StatusIntegracaoJde.ERRO_ENVIO.getValue());
        }
        return reembolso;
    }

    @Override
    public Reembolso consultar(Reembolso reembolso){
        BuscarResp resposta = enviarRequisicaoBuscar(obterRequisicaoBuscar(reembolso));
        Date dataAmbiente = getUtilitarioAmbiente().buscarDataAmbiente();
        if (resposta.getStatusIntegracao().isStatus()) {
            ResultadoVoucher resultado = resposta.getCabecalho().getCabecalho().get(0).getDetalhe().getDetalhe().get(0).getResultadoVoucher();
            if(resultado.getStatusPagamento().equals(STATUS_PAGAMENTO_PAGO)){
                reembolso.setDataPagamento(dataAmbiente);
                reembolso.setStatus(StatusPagamentoReembolso.PAGO.getValue());
                reembolso.setStatusIntegracao(StatusIntegracaoJde.REALIZADO.getValue());
            } else if (dataAmbiente.after(reembolso.getDataVencimentoPagto())) {
                reembolso.setStatus(StatusPagamentoReembolso.ATRASADO.getValue());
            } else {
                reembolso.setStatus(StatusPagamentoReembolso.EM_ABERTO.getValue());
            }
            reembolso.setMensagemErro(null);
            XMLGregorianCalendar dataVencimentoXML = resultado.getDataVencimento();
            reembolso.setDataVencimentoPagto(UtilitarioCalculoData
                    .obterData(dataVencimentoXML.getDay(), dataVencimentoXML.getMonth() - 1, dataVencimentoXML.getYear()));
        } else {
            reembolso.setMensagemErro(resposta.getStatusIntegracao().getMensagem());
        }
        return reembolso;
    }

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(getJdeLogin(), getJdeSenha(), NOME_SERVICO);
    }

    @Override
    protected VoucherSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlJdeCompleta());
        Voucher_Service voucher = new Voucher_Service(url);
        return voucher.getVoucherSoap();
    }

    /**
     * Envia uma requisição para a operação criar.
     *
     * @param req Corpo da requisição
     * @return Resposta da requisição
     */
    private CriarResp enviarRequisicaoCriar(CriarReq req){
        CriarResp response;
        try {
            response = getServico().criar(req);
        } catch (Exception ex) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".cadastro. " + ex.getMessage());
        }
        return response;
    }

    /**
     * Envia uma requisição para a operação buscar.
     *
     * @param req Corpo da requisição
     * @return Resposta da requisição
     */
    private BuscarResp enviarRequisicaoBuscar(BuscarReq req){
        BuscarResp response;
        try {
            response = getServico().buscar(req);
        } catch (Exception ex) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".cadastro. " + ex.getMessage());
        }
        return response;
    }

    /**
    * Dado o endereco do wsdl, converte-o em um caminho absoluto
    *
    * @return url JDE completa
    */
   private String obterUrlJdeCompleta() {
       return this.getClass().getResource(ARQUIVO_JDE_WSDL).toString();
   }

    /**
     * Cria requisicao para inclusão de reembolso no jde.
     *
     * @param reembolso Reembolso
     * @return Requisição
     */
    private CriarReq obterRequisicaoCriar(Reembolso reembolso) {
        Boolean isPrePago = obterPrimeiroObjetoDaLista(reembolso.getTransacoesConsolidadas()).isPrePaga();
        CriarReq req = new CriarReq();
        req.setCenario(definirCenarioVoucher(reembolso.getStatusLiberacaoPagamentoEnum()));
        req.setFilial(FILIAL);
        req.setCompanhia(COMPANHIA);
        req.setCodigoPagamento(VOUCHER_CODIGO_PAGAMENTO);
        req.setNumeroFatura(reembolso.getId().toString());
        XMLGregorianCalendar dataEnvio = formatarDataGregorian(definirHorarioVoucher(new Date()));

        req.setDataFatura(dataEnvio);
        req.setDataContabil(dataEnvio);
        req.setDataTributacaoServico(dataEnvio);
        req.setDataProcessamento(dataEnvio);

        Fornecedor pontoDeVenda = new Fornecedor();
        pontoDeVenda.setCodigo(obterPrimeiroObjetoDaLista(reembolso.getPontosDeVenda()).getNumeroJdeInterno().longValue());
        req.setFornecedor(pontoDeVenda);

        BigDecimal valorBruto = reembolso.getValorTotal();
        BigDecimal valorMdr = reembolso.getValorDesconto().negate();
        String valorLiquido = reembolso.getValorReembolso().toString();

        ArrayOfvouchervoucher listaVoucher = new ArrayOfvouchervoucher();
        Voucher voucher = new Voucher();
        voucher.setValorMoedaNacional(valorLiquido);
        voucher.setDataVencimento(formatarDataGregorian(definirHorarioVoucher(reembolso.getDataVencimentoPagto())));
        listaVoucher.getVoucher().add(voucher);
        req.setVoucher(listaVoucher);

        ArrayOfdistribuicaoContabildistribuicaoContabil listaDistribuicaoContabil = new ArrayOfdistribuicaoContabildistribuicaoContabil();
        DistribuicaoContabil distribuicaoContabil = new DistribuicaoContabil();
        distribuicaoContabil.setIndiceContaContabil(isPrePago ? VOUCHER_INDICE_CONTA_CONTABIL_PRE_PAGO : VOUCHER_INDICE_CONTA_CONTABIL_POS_PAGO);
        distribuicaoContabil.setValorMoedaNacional(valorBruto);
        distribuicaoContabil.setObservacao(isPrePago ? VOUCHER_OBSERVACAO_PRE_PAGO : VOUCHER_OBSERVACAO_POS_PAGO );
        distribuicaoContabil.setReferencia(pontoDeVenda.getCodigo().toString());
        listaDistribuicaoContabil.getDistribuicaoContabil().add(distribuicaoContabil);

        DistribuicaoContabil distribuicaoContabilMdr = new DistribuicaoContabil();
        distribuicaoContabilMdr.setIndiceContaContabil(VOUCHER_INDICE_CONTA_CONTABIL_MDR);
        distribuicaoContabilMdr.setValorMoedaNacional(valorMdr);
        distribuicaoContabilMdr.setObservacao(VOUCHER_OBSERVACAO_MDR_REEMBOLSO);
        distribuicaoContabilMdr.setReferencia(pontoDeVenda.getCodigo().toString());
        listaDistribuicaoContabil.getDistribuicaoContabil().add(distribuicaoContabilMdr);

        req.setDistribuicaoContabil(listaDistribuicaoContabil);

        return req;
    }

    /**
     * Cria requisicao para consulta de reembolso no jde.
     *
     * @param reembolso Reembolso
     * @return Requisição
     */
    private BuscarReq obterRequisicaoBuscar(Reembolso reembolso) {
        BuscarReq req = new BuscarReq();
        req.setCodigoFornecedor(obterPrimeiroObjetoDaLista(reembolso.getPontosDeVenda()).getNumeroJdeInterno());
        req.setDocumento(reembolso.getNumeroDocumento());
        req.setTipoDocumento(reembolso.getTipoDocumento());
        req.setCompanhiaDocumento(reembolso.getCiaDocumento());
        return req;
    }

    /**
     * Define a data/hora que será utilizada no voucher.
     *
     * @param dataOriginal Data original
     * @return Data/hora do voucher
     */
    private Date definirHorarioVoucher(Date dataOriginal){
        Calendar novaData = new GregorianCalendar();
        novaData.setTime(dataOriginal);
        novaData.set(novaData.get(Calendar.YEAR),
                novaData.get(Calendar.MONTH),
                novaData.get(Calendar.DAY_OF_MONTH),
                VOUCHER_TEMPO_HORA,VOUCHER_TEMPO_MINUTO,VOUCHER_TEMPO_SEGUNDO);
        return novaData.getTime();
    }

    /**
     * Define o valor do atributo "cenario" da requisição do serviço soap.
     *
     * @param statusLiberacao O status da liberação de pagamento do reembolso no JDE.
     * @return Valor do cenario
     */
    private long definirCenarioVoucher(StatusLiberacaoReembolsoJde statusLiberacao) {
        if (statusLiberacao.isAprovadoPagamento()) {
            return VOUCHER_CENARIO_APROVADO_PAGAMENTO;
        }
        return VOUCHER_CENARIO_SUSPENSO_PAGAMENTO;
    }
}