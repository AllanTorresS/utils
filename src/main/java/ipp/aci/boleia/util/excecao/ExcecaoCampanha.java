package ipp.aci.boleia.util.excecao;

/**
 * Excecao lancada em caso de erro no gerador de campanhas
 */
public class ExcecaoCampanha extends ExcecaoBoleia {

    /**
     * Constroi a excecao
     *
     * @param mensagem A mensagem a ser exibida
     */
    public ExcecaoCampanha(String mensagem) {
        super(Erro.ERRO_BUSCAR_CAMPANHA, mensagem);
    }

}
