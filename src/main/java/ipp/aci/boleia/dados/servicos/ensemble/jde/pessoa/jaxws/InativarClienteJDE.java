
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "inativarClienteJDE")
public class InativarClienteJDE {

    protected InativarJDEReq pRequest;

    
    public InativarJDEReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(InativarJDEReq value) {
        this.pRequest = value;
    }

}
