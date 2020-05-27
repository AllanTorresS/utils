
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "liberar")
public class Liberar {

    protected LiberarReq pRequest;

    
    public LiberarReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(LiberarReq value) {
        this.pRequest = value;
    }

}
