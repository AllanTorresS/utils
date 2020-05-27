
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eventoTelemetriaContainer", propOrder = {
    "eventoTelemetria"
})
public class EventoTelemetriaContainer {

    @XmlElement(nillable = true)
    protected List<EventoTelemetria> eventoTelemetria;

    
    public List<EventoTelemetria> getEventoTelemetria() {
        if (eventoTelemetria == null) {
            eventoTelemetria = new ArrayList<EventoTelemetria>();
        }
        return this.eventoTelemetria;
    }

}
