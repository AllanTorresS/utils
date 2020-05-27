
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayout", propOrder = {
    "usuario",
    "senha",
    "layout"
})
public class ObterLayout {

    protected String usuario;
    protected String senha;
    @XmlSchemaType(name = "string")
    protected TipoLayout layout;

    
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

    
    public TipoLayout getLayout() {
        return layout;
    }

    
    public void setLayout(TipoLayout value) {
        this.layout = value;
    }

}
