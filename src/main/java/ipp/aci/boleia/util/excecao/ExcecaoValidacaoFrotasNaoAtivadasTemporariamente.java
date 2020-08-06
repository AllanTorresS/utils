package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada ao encontrar um erro de validacao na seleção multiplas de frotas
 * que não tenha vindo da selecao de uma data passada
 */
public class ExcecaoValidacaoFrotasNaoAtivadasTemporariamente extends ExcecaoValidacao {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoValidacaoFrotasNaoAtivadasTemporariamente(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoValidacaoFrotasNaoAtivadasTemporariamente(Erro erro, Object... parametro) {
        super(erro, parametro);
    }
}
