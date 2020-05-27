
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "atualizarSenha", propOrder = {
    "usuario",
    "senhaAtual",
    "novaSenha"
})
public class AtualizarSenha {

    protected String usuario;
    protected String senhaAtual;
    protected String novaSenha;

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

    
    public String getSenhaAtual() {
        return senhaAtual;
    }

    
    public void setSenhaAtual(String value) {
        this.senhaAtual = value;
    }

    
    public String getNovaSenha() {
        return novaSenha;
    }

    
    public void setNovaSenha(String value) {
        this.novaSenha = value;
    }

}
