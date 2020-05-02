package ipp.aci.boleia.dominio.pesquisa.comum;

/**
 * Base para criacao de filtros de pesquisa com paginacao
 */
public class BaseFiltroPaginado {

    private InformacaoPaginacao paginacao;

    /**
     * Retorna os dados da paginacao
     *
     * @return Os dados da paginacao
     */
    public InformacaoPaginacao getPaginacao() {
        return paginacao;
    }

    /**
     * Atribui os dados de paginacao
     *
     * @param paginacao Os dados de paginacao
     */
    public void setPaginacao(InformacaoPaginacao paginacao) {
        this.paginacao = paginacao;
    }
}
