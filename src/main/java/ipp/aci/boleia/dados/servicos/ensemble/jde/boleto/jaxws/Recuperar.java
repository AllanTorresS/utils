
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "recuperar")
public class Recuperar {

    protected RecuperarBoletoReq pRequest;

    
    public RecuperarBoletoReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(RecuperarBoletoReq value) {
        this.pRequest = value;
    }

}
