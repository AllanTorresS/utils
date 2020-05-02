package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção de informações de veículo
 */
public class CtaPlusVeiculoVo {

    private Long codigo;
    private String placa;
    private String frota;
    private Boolean bypass;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFrota() {
        return frota;
    }

    public void setFrota(String frota) {
        this.frota = frota;
    }

    public Boolean getBypass() {
        return bypass;
    }

    public void setBypass(Boolean bypass) {
        this.bypass = bypass;
    }

}
