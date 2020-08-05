package ipp.aci.boleia.dados;

/**
 * Contrato para implementação de repositórios da entidade PrazoGeracaoCobranca.
 */
public interface IPrazoGeracaoCobrancaDados extends IRepositorioBoleiaDados<PrazoGeracaoCobranca> {

    /** Retorna o prazo de geração de cobrança a partir de um prazo de pagamento.
     *
     * @param prazoPagamento o prazo de pagamento que terá a faixa de prazos consultada.
     * @return um objeto PrazoGeracaoCobranca.
     */
    PrazoGeracaoCobranca obterPorPrazoPagamento(Long prazoPagamento);

}
