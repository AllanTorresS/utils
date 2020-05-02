package ipp.aci.boleia.util.excecao;

/**
 * Excecao lançada quando violado uma regra/parametro versatil
 */
public class ExcecaoViolacaoRegraVersatil extends ExcecaoValidacao {

    /**
     * Constroi a excecao
     *
     * @param mensagem A manesagem a ser exibida
     */
    public ExcecaoViolacaoRegraVersatil(String mensagem) {
        super(Erro.ERRO_VALIDACAO, mensagem);
    }

    /**
     * Constroi a excecao
     *
     * @param erro O tipo de erro de validacao
     * @param parametro parametros da mensagem
     */
    public ExcecaoViolacaoRegraVersatil(Erro erro, Object... parametro) {
        super(erro, parametro);
    }
}
