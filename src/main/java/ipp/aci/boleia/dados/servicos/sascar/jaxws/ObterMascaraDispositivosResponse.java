
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMascaraDispositivosResponse", propOrder = {
    "_return"
})
public class ObterMascaraDispositivosResponse {

    @XmlElement(name = "return")
    protected List<MascaraDispositivo> _return;

    
    public List<MascaraDispositivo> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MascaraDispositivo>();
        }
        return this._return;
    }

}
