package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.ISalesForceParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.vo.AtualizarExigenciaNfeErroVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Respositório dos serviços de integração com o salseforce para atualizar os dados de parametrização de NFe
 */
@Repository
public class SalesForceParametroNotaFiscalDados extends AcessoSalesForceBase implements ISalesForceParametroNotaFiscalDados  {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesForceParametroNotaFiscalDados.class);

    private static final String SEM_NOTA_FISCAL = "SemNotaFiscal__c";

    private static final String MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA = "Erro ao atualizar dados exigencia de emissao de nota fiscal no sales force: {}";

    @Value("${salesforce.account.atualizar.url}")
    private String accountUrl;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Override
    public AtualizarExigenciaNfeErroVo atualizarExigenciaNotaFiscal(String cnpj, boolean exigeNotaFiscal) {
        String semNotaFiscal = exigeNotaFiscal ?  mensagens.obterMensagem("texto.comum.nao") : mensagens.obterMensagem("texto.comum.sim");
        Map<String, String> corpo = new LinkedHashMap<>();
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();;
        corpo.put(SEM_NOTA_FISCAL, semNotaFiscal);

        try {
            if (Boolean.FALSE.equals(validarAccountExistente(cnpj))) {
                LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, this.mensagem);
                return new AtualizarExigenciaNfeErroVo(cnpj, exigeNotaFiscal, dataAtual, statusCode);
            }

            if (semNotaFiscal.equals(this.responseBody.get(SEM_NOTA_FISCAL).asText())) {
                return null;
            }

            prepararRequisicao(this.accountUrl.replace(CNPJ_URL, cnpj), corpo);
            if (!restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, this::tratarAtualizacaoAccount)) {
                if(!restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, this::tratarAtualizacaoAccount)){
                    LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, this.mensagem);
                    return new AtualizarExigenciaNfeErroVo(cnpj, exigeNotaFiscal, dataAtual, statusCode);
                }
            }
        } catch (Exception e) {
            LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, e.getMessage(), e);
            return new AtualizarExigenciaNfeErroVo(cnpj, exigeNotaFiscal, dataAtual, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return null;
    }

    /**
     * Verifica se existe uma conta no SalesForce com o cnpj informado
     * @param cnpj cnpj da Frota/Unidade/Empresa agregada
     * @return true se positivo
     */
    private Boolean validarAccountExistente(String cnpj) {
        try {
            super.prepararRequisicao(this.accountUrl.replace(CNPJ_URL, cnpj), null);
        }catch (Exception e){
            if(this.statusCode != HttpStatus.BAD_REQUEST.value()
                && this.statusCode != HttpStatus.FORBIDDEN.value()
                && this.statusCode != HttpStatus.UNAUTHORIZED.value()){
                try {
                    super.prepararRequisicao(this.accountUrl.replace(CNPJ_URL, cnpj), null);
                }catch (Exception ex){
                    LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, ex.getMessage(), ex);
                }
            }else{
                LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, e.getMessage(), e);
            }
        }
        Boolean integracaoVerificaExistenciaCnpjIsOk = restDados.doGet(this.endpointUrl, this.authorizationHeaders, this::tratarValidarExistenciaAccount);
        if(integracaoVerificaExistenciaCnpjIsOk){
            return integracaoVerificaExistenciaCnpjIsOk;
        }else{
            if(this.statusCode != HttpStatus.NOT_FOUND.value()){
                integracaoVerificaExistenciaCnpjIsOk = restDados.doGet(this.endpointUrl, this.authorizationHeaders, this::tratarValidarExistenciaAccount);
            }
        }
        if(!integracaoVerificaExistenciaCnpjIsOk){
            LOGGER.error(MSG_LOG_ERRO_AO_ATUALIZAR_DADOS_EXIGENCIA, this.mensagem);
        }
        return integracaoVerificaExistenciaCnpjIsOk;
    }

    /**
     * Tratamento da resposta da solicitação de validação da existencia da Account para o CNPJ informado.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     * @return true em caso de existencia da Account, caso contrario false.
     */
    private boolean tratarValidarExistenciaAccount(CloseableHttpResponse httpResponse) {
        prepararResposta(httpResponse);
        return this.statusCode == HttpStatus.OK.value() && this.responseBody.get(SEM_NOTA_FISCAL) != null;
    }

    /**
     * Tratamento da resposta da solicitação de atualização do cliente Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     * @return true em caso sucesso na requisição, caso contrario false.
     */
    private Boolean tratarAtualizacaoAccount(CloseableHttpResponse httpResponse) {
        prepararResposta(httpResponse);
        return this.statusCode == HttpStatus.OK.value();
    }
}
