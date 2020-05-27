
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "motoristaVeiculo", propOrder = {
    "idMotorista",
    "idMotoristaVeiculo",
    "idVeiculo"
})
public class MotoristaVeiculo {

    protected Integer idMotorista;
    protected Long idMotoristaVeiculo;
    protected String idVeiculo;

    
    public Integer getIdMotorista() {
        return idMotorista;
    }

    
    public void setIdMotorista(Integer value) {
        this.idMotorista = value;
    }

    
    public Long getIdMotoristaVeiculo() {
        return idMotoristaVeiculo;
    }

    
    public void setIdMotoristaVeiculo(Long value) {
        this.idMotoristaVeiculo = value;
    }

    
    public String getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(String value) {
        this.idVeiculo = value;
    }

}
