package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada ao encontrar um erro de validacao em uma api disponivel para integração
 */
public class ExcecaoValidacaoApi extends ExcecaoValidacao {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoValidacaoApi(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O enumerável do erro
     * @param mensagem A mensagem a ser exibida
     */
    public ExcecaoValidacaoApi(Erro erro, String mensagem) {
        super(erro, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoValidacaoApi(Erro erro, Object... parametro) {
        super(erro, parametro);
    }

    /**
     * Constroi a excecao
     *
     * @param ex Uma exception de validação a ser convertida
     */
    public ExcecaoValidacaoApi(ExcecaoValidacao ex) {
        super(ex.getErro(), ex.getArgs());
    }
}
