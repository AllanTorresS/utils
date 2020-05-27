
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "vincularResult"
})
@XmlRootElement(name = "vincularResponse")
public class VincularResponse {

    @XmlElement(required = true)
    protected Response vincularResult;

    
    public Response getVincularResult() {
        return vincularResult;
    }

    
    public void setVincularResult(Response value) {
        this.vincularResult = value;
    }

}
