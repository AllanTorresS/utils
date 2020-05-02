
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "buscar")
public class Buscar {

    protected BuscarReq pRequest;

    
    public BuscarReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(BuscarReq value) {
        this.pRequest = value;
    }

}
