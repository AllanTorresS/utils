
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicoesRFNacional", propOrder = {
    "usuario",
    "senha",
    "quantidade"
})
public class ObterPacotePosicoesRFNacional {

    protected String usuario;
    protected String senha;
    protected Integer quantidade;

    
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

}
