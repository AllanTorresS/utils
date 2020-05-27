
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consultarResult"
})
@XmlRootElement(name = "consultarResponse")
public class ConsultarResponse {

    @XmlElement(required = true)
    protected ConsultarResp consultarResult;

    
    public ConsultarResp getConsultarResult() {
        return consultarResult;
    }

    
    public void setConsultarResult(ConsultarResp value) {
        this.consultarResult = value;
    }

}
