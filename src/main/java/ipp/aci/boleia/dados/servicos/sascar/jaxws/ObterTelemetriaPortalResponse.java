
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterTelemetriaPortalResponse", propOrder = {
    "_return"
})
public class ObterTelemetriaPortalResponse {

    @XmlElement(name = "return")
    protected Telemetria _return;

    
    public Telemetria getReturn() {
        return _return;
    }

    
    public void setReturn(Telemetria value) {
        this._return = value;
    }

}
