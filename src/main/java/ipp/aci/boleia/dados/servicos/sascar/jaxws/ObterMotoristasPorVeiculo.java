
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMotoristasPorVeiculo", propOrder = {
    "usuario",
    "senha",
    "idVeiculo"
})
public class ObterMotoristasPorVeiculo {

    protected String usuario;
    protected String senha;
    protected Long idVeiculo;

    
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

    
    public Long getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Long value) {
        this.idVeiculo = value;
    }

}
