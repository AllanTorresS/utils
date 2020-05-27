
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarEventosCaixaPretaResponse", propOrder = {
    "_return"
})
public class RecuperarEventosCaixaPretaResponse {

    @XmlElement(name = "return")
    protected CaixaPretaList _return;

    
    public CaixaPretaList getReturn() {
        return _return;
    }

    
    public void setReturn(CaixaPretaList value) {
        this._return = value;
    }

}
