
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTd40", propOrder = {
    "usuario",
    "senha",
    "satelital"
})
public class ObterMacroTd40 {

    protected String usuario;
    protected String senha;
    protected boolean satelital;

    
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

    
    public boolean isSatelital() {
        return satelital;
    }

    
    public void setSatelital(boolean value) {
        this.satelital = value;
    }

}
