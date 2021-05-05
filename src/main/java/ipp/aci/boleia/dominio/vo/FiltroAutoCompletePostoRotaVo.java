package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa o filtro  ser usado na pesquisa do autocomplete de postos
 */
public class FiltroAutoCompletePostoRotaVo {

    private String termo;
    private Long idFrota;
    private Boolean postoInterno;
    private Boolean postoUrbano;
    private List<Integer> parametrosDeUso;
    private Long tipoCombustivel;

    private List<Long> postosParametizados;

    public FiltroAutoCompletePostoRotaVo() {
    }

    public FiltroAutoCompletePostoRotaVo(String termo, Long idFrota, Boolean postoInterno, List<Integer> parametrosDeUso, Long tipoCombustivel, Boolean postoUrbano, List<Long> postosParametizados) {
        this.termo = termo;
        this.idFrota = idFrota;
        this.postoInterno = postoInterno;
        this.parametrosDeUso = parametrosDeUso;
        this.tipoCombustivel = tipoCombustivel;
        this.postoUrbano = postoUrbano;
        this.postosParametizados = postosParametizados;
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

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
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

    public Boolean getPostoUrbano() {
        return postoUrbano;
    }

    public void setPostoUrbano(Boolean postoUrbano) {
        this.postoUrbano = postoUrbano;
    }

    public List<Long> getPostosParametizados() {
        return postosParametizados;
    }

    public void setPostosParametizados(List<Long> postosParametizados) {
        this.postosParametizados = postosParametizados;
    }
}
