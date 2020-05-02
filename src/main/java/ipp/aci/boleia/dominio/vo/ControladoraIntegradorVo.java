package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa uma entidade de controladora para integração com o Integrador
 */
public class ControladoraIntegradorVo {

    private Integer codigoTipoControladora;

    private List<BicoIntegradorVo> bicos;

    public Integer getCodigoTipoControladora() {
        return codigoTipoControladora;
    }

    public void setCodigoTipoControladora(Integer codigoTipoControladora) {
        this.codigoTipoControladora = codigoTipoControladora;
    }

    public List<BicoIntegradorVo> getBicos() {
        return bicos;
    }

    public void setBicos(List<BicoIntegradorVo> bicos) {
        this.bicos = bicos;
    }
}
