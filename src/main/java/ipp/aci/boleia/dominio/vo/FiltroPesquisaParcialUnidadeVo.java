package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialUnidadeVo extends BaseFiltroPaginado {

    private String termo;
    private Boolean postoInterno;
    private Long idFrota;

	public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Boolean getPostoInterno() {
        return postoInterno;
    }

    public void setPostoInterno(Boolean apenasPIsHabilitados) {
        this.postoInterno = apenasPIsHabilitados;
    }
    
    public Long getIdFrota() {
		return idFrota;
	}

	public void setIdFrota(Long idFrota) {
		this.idFrota = idFrota;
	}

}