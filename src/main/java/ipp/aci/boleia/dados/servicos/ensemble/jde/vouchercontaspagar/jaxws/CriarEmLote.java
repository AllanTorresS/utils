
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "criarEmLote")
public class CriarEmLote {

    protected CriarEmLoteReq pRequest;

    
    public CriarEmLoteReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(CriarEmLoteReq value) {
        this.pRequest = value;
    }

}
