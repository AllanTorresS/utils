
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "prorrogarVencimento")
public class ProrrogarVencimento {

    protected ProrrogarVencimentoReq pRequest;

    
    public ProrrogarVencimentoReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(ProrrogarVencimentoReq value) {
        this.pRequest = value;
    }

}
