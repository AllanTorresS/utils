package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

/**
 * Classe base para as classes que atualizam dados de conta no Salesforce
 */
public class SalesForceAtualizacaoContaBase extends AcessoSalesForceBase {

    private static final String TIPO_CONTA_URL = "{tipo_conta}";
    private static final String TIPO_CONTA_PV = "PV";
    private static final String TIPO_CONTA_CLIENTE = "CL";

    @Value("${salesforce.account.atualizar.url}")
    protected String urlAtualizacaoConta;

    /**
     * Prepara os dados de requisição para atualização de uma conta de cliente no Salesforce
     *
     * @param cnpj O CNPJ da conta que será atualizada
     * @param corpo O corpo da requisição, com os campos alterados e seus novos valores
     */
    protected void prepararRequisicaoAtualizacaoCliente(Long cnpj, Object corpo) {
        prepararRequisicao(montarUrlAtualizacao(cnpj, TIPO_CONTA_CLIENTE), corpo);
    }

    /**
     * Prepara os dados de requisição para atualização de uma conta de posto no Salesforce
     *
     * @param cnpj O CNPJ da conta que será atualizada
     * @param corpo O corpo da requisição, com os campos alterados e seus novos valores
     */
    protected void prepararRequisicaoAtualizacaoPosto(Long cnpj, Object corpo) {
        prepararRequisicao(montarUrlAtualizacao(cnpj, TIPO_CONTA_PV), corpo);
    }

    /**
     * Monta a URL de atualização de conta no Salesforce
     *
     * @param cnpj O CNPJ da conta que terá os dados atualizados
     * @param tipoConta O tipo da conta que está sendo atualizada
     * @return A URL de atualização da conta
     */
    private String montarUrlAtualizacao(Long cnpj, String tipoConta) {
        return this.urlAtualizacaoConta
                .replace(CNPJ_URL, UtilitarioFormatacao.formatarNumeroZerosEsquerda(cnpj, UtilitarioFormatacao.TAMANHO_CNPJ))
                .replace(TIPO_CONTA_URL, tipoConta);
    }

    /**
     * Tratamento da resposta à requisição de atualização de conta do Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     */
    protected boolean tratarAtualizacaoConta(CloseableHttpResponse httpResponse) {
        prepararResposta(httpResponse);
        return this.statusCode == HttpStatus.OK.value();
    }

    public String getUrlAtualizacaoConta() {
        return urlAtualizacaoConta;
    }

    public void setUrlAtualizacaoConta(String urlAtualizacaoConta) {
        this.urlAtualizacaoConta = urlAtualizacaoConta;
    }
}
