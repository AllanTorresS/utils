package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto;

import ipp.aci.boleia.dados.IVincularJurosBoletoDados;
import ipp.aci.boleia.dados.servicos.ensemble.WsSecurityUsernameTokenHandler;
import ipp.aci.boleia.dados.servicos.ensemble.jde.JdeBaseDados;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.Request;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.Response;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.StatusIntegracao;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.VincularJurosBoleto;
import ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws.VincularJurosBoletoSoap;
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
import static ipp.aci.boleia.util.jde.ConstantesJde.VINCULAR_JUROS_CENARIO;

/**
 * Implementação da interface responsável pela integração com o serviço de vinculo de juros de boleto no JDE.
 */
@Repository
@Scope("singleton")
public class JdeVincularJurosBoletoDados extends JdeBaseDados<VincularJurosBoletoSoap> implements IVincularJurosBoletoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdeVincularJurosBoletoDados.class);
    private static final String NOME_SERVICO = "vincularJurosBoletoJDE";
    private static final String ARQUIVO_JDE_WSDL = "/wsdl/jde/vincularJurosBoleto.wsdl";

    @Override
    public void vincular(AjusteCobranca ajusteCobranca) {
        Request request = criarRequisicaoServico(ajusteCobranca);
        Response response = enviarRequisicaoServico(request);
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
    protected VincularJurosBoletoSoap instanciarServico() throws MalformedURLException {
        URL url = new URL(obterUrlWsdlCompleta());
        VincularJurosBoleto vincularJurosBoleto = new VincularJurosBoleto(url);
        return vincularJurosBoleto.getVincularJurosBoletoSoap();
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
        Request request = new Request();
        request.setMnCliente(frota.getNumeroJdeInterno());
        request.setMnCenario(Integer.parseInt(VINCULAR_JUROS_CENARIO));
        request.setMnValorTransacao(ajusteCobranca.getValorAjuste());
        request.setMnNumBoleto(cobranca.getNumeroDocumento());
        request.setJdDataDaFatura(formatarDataGregorian(getUtilitarioAmbiente().buscarDataAmbiente()));
        if (ajusteCobranca.getDataVencimentoAjuste() == null) {
            request.setJdDateDueJulian(formatarDataGregorian(ajusteCobranca.getCobranca().getDataVencimentoPagto()));
        } else {
            request.setJdDateDueJulian(formatarDataGregorian(ajusteCobranca.getDataVencimentoAjuste()));
        }
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
