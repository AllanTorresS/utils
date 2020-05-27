
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autorizacaoReq", propOrder = {
    "usuario",
    "sistema"
})
public class AutorizacaoReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected String usuario;
    @XmlElement(required = true)
    protected String sistema;

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

    
    public String getSistema() {
        return sistema;
    }

    
    public void setSistema(String value) {
        this.sistema = value;
    }

}
