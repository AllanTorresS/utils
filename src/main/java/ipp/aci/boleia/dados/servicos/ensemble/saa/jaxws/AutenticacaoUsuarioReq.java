
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticacaoUsuarioReq", propOrder = {
    "usuario",
    "senha"
})
public class AutenticacaoUsuarioReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected String usuario;
    @XmlElement(required = true)
    protected String senha;

    
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

}
