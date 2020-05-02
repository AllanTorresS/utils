
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "liberarResult"
})
@XmlRootElement(name = "liberarResponse")
public class LiberarResponse {

    @XmlElement(required = true)
    protected LiberarResp liberarResult;

    
    public LiberarResp getLiberarResult() {
        return liberarResult;
    }

    
    public void setLiberarResult(LiberarResp value) {
        this.liberarResult = value;
    }

}
