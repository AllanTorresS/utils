package ipp.aci.boleia.dominio.vo;

/**
 * Filtro para pesquisa para busca parcial de Empresas Agregadas
 */
public class FiltroPesquisaParcialEmpresaAgregadaVo {

    private String termo;
    private Long idFrota;

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
