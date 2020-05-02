
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMotoristasResponse", propOrder = {
    "_return"
})
public class ObterMotoristasResponse {

    @XmlElement(name = "return")
    protected List<Motorista> _return;

    
    public List<Motorista> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Motorista>();
        }
        return this._return;
    }

}
