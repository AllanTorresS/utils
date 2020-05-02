
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "telemetria", propOrder = {
    "embreagem",
    "estadoLimpadorParabrisa",
    "freio",
    "motorFuncionando"
})
public class Telemetria {

    protected Integer embreagem;
    protected Integer estadoLimpadorParabrisa;
    protected Integer freio;
    protected Integer motorFuncionando;

    
    public Integer getEmbreagem() {
        return embreagem;
    }

    
    public void setEmbreagem(Integer value) {
        this.embreagem = value;
    }

    
    public Integer getEstadoLimpadorParabrisa() {
        return estadoLimpadorParabrisa;
    }

    
    public void setEstadoLimpadorParabrisa(Integer value) {
        this.estadoLimpadorParabrisa = value;
    }

    
    public Integer getFreio() {
        return freio;
    }

    
    public void setFreio(Integer value) {
        this.freio = value;
    }

    
    public Integer getMotorFuncionando() {
        return motorFuncionando;
    }

    
    public void setMotorFuncionando(Integer value) {
        this.motorFuncionando = value;
    }

}
