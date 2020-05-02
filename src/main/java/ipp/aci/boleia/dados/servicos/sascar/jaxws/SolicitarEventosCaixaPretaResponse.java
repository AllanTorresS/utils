
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solicitarEventosCaixaPretaResponse", propOrder = {
    "_return"
})
public class SolicitarEventosCaixaPretaResponse {

    @XmlElement(name = "return")
    protected String _return;

    
    public String getReturn() {
        return _return;
    }

    
    public void setReturn(String value) {
        this._return = value;
    }

}
