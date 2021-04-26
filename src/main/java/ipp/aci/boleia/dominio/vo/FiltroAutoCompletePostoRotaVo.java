package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa o filtro  ser usado na pesquisa do autocomplete de postos
 */
public class FiltroAutoCompletePostoRotaVo {

    private String termo;
    private String idFrota;
    private Boolean postoInterno;
    private List<Integer> parametrosDeUso;
    private Long tipoCombustivel;

    public FiltroAutoCompletePostoRotaVo() {
    }

    public FiltroAutoCompletePostoRotaVo(String termo, String idFrota, Boolean postoInterno, List<Integer> parametrosDeUso, Long tipoCombustivel) {
        this.termo = termo;
        this.idFrota = idFrota;
        this.postoInterno = postoInterno;
        this.parametrosDeUso = parametrosDeUso;
        this.tipoCombustivel = tipoCombustivel;
    }

    public Long getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(Long tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(String idFrota) {
        this.idFrota = idFrota;
    }

    public Boolean getPostoInterno() {
        return postoInterno;
    }

    public void setPostoInterno(Boolean postoInterno) {
        this.postoInterno = postoInterno;
    }

    public List<Integer> getParametrosDeUso() {
        return parametrosDeUso;
    }

    public void setParametrosDeUso(List<Integer> parametrosDeUso) {
        this.parametrosDeUso = parametrosDeUso;
    }
}
