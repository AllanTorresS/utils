package ipp.aci.boleia.dominio.vo;

/**
 * Filtro para pesquisa para autocomplete de Empresas Agregadas
 */
public class FiltroPesquisaAutoCompleteEmpAgregadaUnidadeVo {

    private String termo;
    private Long idFrota;

    public FiltroPesquisaAutoCompleteEmpAgregadaUnidadeVo() {
        //Construtor default
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }
}
