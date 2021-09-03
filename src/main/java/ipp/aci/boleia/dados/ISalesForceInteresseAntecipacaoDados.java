package ipp.aci.boleia.dados;

/**
 * Interface para enviar ao Salesforce solicitação de interesse no programa de antecipação de recebíveis
 */
public interface ISalesForceInteresseAntecipacaoDados {

    /**
     * Atualiza o campo de participação no programa de antecipação de uma conta no Salesforce
     *
     * @param cnpj O CNPJ que identifica a conta no Salesforce
     * @param novoValor O novo valor do campo
     */
    public void atualizarCampoInteresseAntecipacao(Long cnpj, boolean novoValor);
}