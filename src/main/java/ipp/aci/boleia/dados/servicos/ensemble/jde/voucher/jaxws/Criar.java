
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "criar")
public class Criar {

    protected CriarReq pRequest;

    
    public CriarReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(CriarReq value) {
        this.pRequest = value;
    }

}
