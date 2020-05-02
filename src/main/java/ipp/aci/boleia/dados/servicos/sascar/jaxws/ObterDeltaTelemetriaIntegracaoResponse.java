
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterDeltaTelemetriaIntegracaoResponse", propOrder = {
    "_return"
})
public class ObterDeltaTelemetriaIntegracaoResponse {

    @XmlElement(name = "return")
    protected List<DeltaTelemetria> _return;

    
    public List<DeltaTelemetria> getReturn() {
        if (_return == null) {
            _return = new ArrayList<DeltaTelemetria>();
        }
        return this._return;
    }

}
