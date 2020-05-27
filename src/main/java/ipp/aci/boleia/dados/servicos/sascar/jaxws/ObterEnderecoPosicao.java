
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterEnderecoPosicao", propOrder = {
    "usuario",
    "senha",
    "latitude",
    "longitude"
})
public class ObterEnderecoPosicao {

    protected String usuario;
    protected String senha;
    protected String latitude;
    protected String longitude;

    
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

    
    public String getLatitude() {
        return latitude;
    }

    
    public void setLatitude(String value) {
        this.latitude = value;
    }

    
    public String getLongitude() {
        return longitude;
    }

    
    public void setLongitude(String value) {
        this.longitude = value;
    }

}
