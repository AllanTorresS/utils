
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "autorizacaoUsuario")
public class AutorizacaoUsuario {

    protected AutorizacaoReq pRequest;

    
    public AutorizacaoReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(AutorizacaoReq value) {
        this.pRequest = value;
    }

}
