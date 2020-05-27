
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enviarParametrizacaoTelemetriaResponse", propOrder = {
    "_return"
})
public class EnviarParametrizacaoTelemetriaResponse {

    @XmlElement(name = "return")
    protected List<ComandoTelemetriaRetorno> _return;

    
    public List<ComandoTelemetriaRetorno> getReturn() {
        if (_return == null) {
            _return = new ArrayList<ComandoTelemetriaRetorno>();
        }
        return this._return;
    }

}
