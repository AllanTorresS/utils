
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterEventoTelemetriaDescricaoResponse", propOrder = {
    "_return"
})
public class ObterEventoTelemetriaDescricaoResponse {

    @XmlElement(name = "return")
    protected List<TipoEventoTelemetriaDescricao> _return;

    
    public List<TipoEventoTelemetriaDescricao> getReturn() {
        if (_return == null) {
            _return = new ArrayList<TipoEventoTelemetriaDescricao>();
        }
        return this._return;
    }

}
