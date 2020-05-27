
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comandoEmbarquePontoDiario", propOrder = {
    "usuario",
    "senha",
    "idVeiculo",
    "pontosRef"
})
public class ComandoEmbarquePontoDiario {

    protected String usuario;
    protected String senha;
    protected Integer idVeiculo;
    protected String pontosRef;

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

    
    public String getSenha() {
        return senha;
    }

    
    public void setSenha(String value) {
        this.senha = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public String getPontosRef() {
        return pontosRef;
    }

    
    public void setPontosRef(String value) {
        this.pontosRef = value;
    }

}
