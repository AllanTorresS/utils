
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layoutTecladoVeiculos", propOrder = {
    "idLayout",
    "idVeiculo",
    "tipoLayout"
})
public class LayoutTecladoVeiculos {

    protected Integer idLayout;
    protected Integer idVeiculo;
    protected String tipoLayout;

    
    public Integer getIdLayout() {
        return idLayout;
    }

    
    public void setIdLayout(Integer value) {
        this.idLayout = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getTipoLayout() {
        return tipoLayout;
    }

    
    public void setTipoLayout(String value) {
        this.tipoLayout = value;
    }

}
