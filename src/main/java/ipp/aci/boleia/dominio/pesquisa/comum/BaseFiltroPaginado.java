package ipp.aci.boleia.dominio.pesquisa.comum;

/**
 * Base para criacao de filtros de pesquisa com paginacao
 */
public class BaseFiltroPaginado {

    private InformacaoPaginacao paginacao;
    private Long perfilUsuario;

    public BaseFiltroPaginado(){
    }

    /**
     * Construtor de um BaseFiltroPaginado
     * @param paginacao Informações da paginação
     */
    public BaseFiltroPaginado(InformacaoPaginacao paginacao){
        this.paginacao = paginacao;
    }

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

    public Long getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Long perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    /**
     * Limpa as informações de paginação presentes no filtro.
     */
    public void limparPaginacao() {
        if(paginacao != null) {
            paginacao.setTamanhoPagina(null);
            paginacao.setPagina(null);
        }
    }
}
