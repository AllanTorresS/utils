
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterEventoTelemetriaIntegracaoResponse", propOrder = {
    "_return"
})
public class ObterEventoTelemetriaIntegracaoResponse {

    @XmlElement(name = "return")
    protected List<EventoTelemetria> _return;

    
    public List<EventoTelemetria> getReturn() {
        if (_return == null) {
            _return = new ArrayList<EventoTelemetria>();
        }
        return this._return;
    }

}
