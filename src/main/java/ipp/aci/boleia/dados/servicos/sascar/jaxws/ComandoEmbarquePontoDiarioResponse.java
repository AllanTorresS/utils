
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comandoEmbarquePontoDiarioResponse", propOrder = {
    "_return"
})
public class ComandoEmbarquePontoDiarioResponse {

    @XmlElement(name = "return")
    protected LogComando _return;

    
    public LogComando getReturn() {
        return _return;
    }

    
    public void setReturn(LogComando value) {
        this._return = value;
    }

}
