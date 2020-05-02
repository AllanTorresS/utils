
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterClientes", propOrder = {
    "usuario",
    "senha",
    "quantidade",
    "idCliente"
})
public class ObterClientes {

    protected String usuario;
    protected String senha;
    protected Integer quantidade;
    protected Integer idCliente;

    
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

    
    public Integer getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(Integer value) {
        this.quantidade = value;
    }

    
    public Integer getIdCliente() {
        return idCliente;
    }

    
    public void setIdCliente(Integer value) {
        this.idCliente = value;
    }

}
