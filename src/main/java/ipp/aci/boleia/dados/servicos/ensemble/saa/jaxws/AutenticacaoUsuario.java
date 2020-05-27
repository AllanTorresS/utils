
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "autenticacaoUsuario")
public class AutenticacaoUsuario {

    protected AutenticacaoUsuarioReq pRequest;

    
    public AutenticacaoUsuarioReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(AutenticacaoUsuarioReq value) {
        this.pRequest = value;
    }

}
