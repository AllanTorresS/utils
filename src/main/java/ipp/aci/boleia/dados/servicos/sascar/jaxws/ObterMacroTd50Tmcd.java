
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTd50Tmcd", propOrder = {
    "usuario",
    "senha",
    "tipoTeclado"
})
public class ObterMacroTd50Tmcd {

    protected String usuario;
    protected String senha;
    @XmlSchemaType(name = "string")
    protected TipoTeclado tipoTeclado;

    
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

    
    public TipoTeclado getTipoTeclado() {
        return tipoTeclado;
    }

    
    public void setTipoTeclado(TipoTeclado value) {
        this.tipoTeclado = value;
    }

}
