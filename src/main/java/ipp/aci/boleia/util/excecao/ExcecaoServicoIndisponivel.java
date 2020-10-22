package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada quando um forncedor externo apresenta erros
 */
public class ExcecaoServicoIndisponivel extends ExcecaoBoleia {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoServicoIndisponivel(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O enumerável do erro
     * @param mensagem A mensagem a ser exibida
     */
    public ExcecaoServicoIndisponivel(Erro erro, String mensagem) {
        super(erro, mensagem);
    }
    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoServicoIndisponivel(Erro erro, Object... parametro) {
        super(erro, parametro);
    }
}
