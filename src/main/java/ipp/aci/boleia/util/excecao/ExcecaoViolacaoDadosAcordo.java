package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada ao encontrar um erro de validacao
 */
public class ExcecaoViolacaoDadosAcordo extends ExcecaoBoleia {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoViolacaoDadosAcordo(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoViolacaoDadosAcordo(Erro erro, Object... parametro) {
        super(erro, parametro);
    }
}
