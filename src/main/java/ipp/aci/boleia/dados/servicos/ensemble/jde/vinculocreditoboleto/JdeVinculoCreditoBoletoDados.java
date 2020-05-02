package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto;

import ipp.aci.boleia.dados.IVinculoCreditoBoletoDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.JdeBaseDados;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.Request;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.Response;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.StatusIntegracao;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.VincularCreditoRequest;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.VinculoCreditoBoleto;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws.VinculoCreditoBoletoSoap;
import ipp.aci.boleia.dominio.AjusteCobranca;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataGregorian;
import static ipp.aci.boleia.util.jde.ConstantesJde.VINCULO_CREDITO_CENARIO;
import static ipp.aci.boleia.util.jde.ConstantesJde.VINCULO_CREDITO_CONTA;

/**
 * Implementação da interface responsável pela integração com o serviço de vinculo de crédito de boleto no JDE.
 */
@Repository
@Scope("singleton")
public class JdeVinculoCreditoBoletoDados extends JdeBaseDados<VinculoCreditoBoletoSoap> implements IVinculoCreditoBoletoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeVinculoCreditoBoletoDados.class);
    private static final String NOME_SERVICO = "vinculoCreditoBoletoJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/vinculoCreditoBoleto.wsdl";

    @Override
    public void vincular(AjusteCobranca ajusteCobranca) {
        Response response = enviarRequisicaoServico(criarRequisicaoServico(ajusteCobranca));
        StatusIntegracao statusIntegracao = response.getStatusIntegracao();
        if (!statusIntegracao.isStatus()) {
            String mensagem = getMensagens().obterMensagem(Erro.ERRO_INTEGRACAO.getChaveMensagem(), NOME_SERVICO + ".vincular: " + statusIntegracao.getMensagem());
            LOGGER.error(mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, NOME_SERVICO + ".vincular");
        }
    }

    @Override
    protected WsSecurityUsernameTokenHandler instanciarHeadersHandler() {
        return new WsSecurityUsernameTokenHandler(getJdeLogin(), getJdeSenha(), NOME_SERVICO);
    }

    @Override
    protected VinculoCreditoBoletoSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlWsdlCompleta());
        VinculoCreditoBoleto vinculoCreditoBoleto = new VinculoCreditoBoleto(url);
        return vinculoCreditoBoleto.getVinculoCreditoBoletoSoap();
    }

    /**
     * Retorna uma {@link URL} completa do WSDL do serviço.
     *
     * @return URL do wsdl
     */
    private String obterUrlWsdlCompleta() {
        return this.getClass().getResource(ARQUIVO_JDE_WSDL).toString();
    }

    /**
     * Cria o objeto com as informações da requisição do serviço.
     *
     * @param ajusteCobranca O ajuste realizado na cobrança
     * @return Objeto da requisição.
     */
    private Request criarRequisicaoServico(AjusteCobranca ajusteCobranca) {
        Cobranca cobranca = ajusteCobranca.getCobranca();
        Frota frota = cobranca.getFrota();
        VincularCreditoRequest vincularCreditoRequest = new VincularCreditoRequest();
        vincularCreditoRequest.setSzConta(VINCULO_CREDITO_CONTA);
        vincularCreditoRequest.setMnCliente(frota.getNumeroJdeInterno());
        vincularCreditoRequest.setMnCenario(Integer.parseInt(VINCULO_CREDITO_CENARIO));
        vincularCreditoRequest.setMnValorTransacao(ajusteCobranca.getValorAjuste());
        if (ajusteCobranca.getDataVencimentoAjuste() == null) {
            vincularCreditoRequest.setJdDataVencimentoDDJ(formatarDataGregorian(ajusteCobranca.getCobranca().getDataVencimentoPagto()));
        } else {
            vincularCreditoRequest.setJdDataVencimentoDDJ(formatarDataGregorian(ajusteCobranca.getDataVencimentoAjuste()));
        }
        vincularCreditoRequest.setJdDataDaFatura(formatarDataGregorian(getUtilitarioAmbiente().buscarDataAmbiente()));
        vincularCreditoRequest.setMnNumBoleto(cobranca.getNumeroDocumento());
        Request request = new Request();
        request.setEntrada(vincularCreditoRequest);
        return request;
    }

    /**
     * Envia a requisição do serviço.
     *
     * @param request Corpo da requisição
     * @return resposta da requisição
     */
    private Response enviarRequisicaoServico(Request request) {
        Response response;
        try {
            response = getServico().vincular(request);
        } catch (Exception ex) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, NOME_SERVICO + ".vincular");
        }
        return response;
    }
}
