
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "consultar")
public class Consultar {

    protected ConsultarReq pRequest;

    
    public ConsultarReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(ConsultarReq value) {
        this.pRequest = value;
    }

}
