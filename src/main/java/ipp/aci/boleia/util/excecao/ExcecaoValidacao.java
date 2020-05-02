package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada ao encontrar um erro de validacao
 */
public class ExcecaoValidacao extends ExcecaoBoleia {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoValidacao(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O enumerável do erro
     * @param mensagem A mensagem a ser exibida
     */
    public ExcecaoValidacao(Erro erro, String mensagem) {
        super(erro, mensagem);
    }
    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoValidacao(Erro erro,Object... parametro) {
        super(erro, parametro);
    }
}
