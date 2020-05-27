
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterClientesResponse", propOrder = {
    "_return"
})
public class ObterClientesResponse {

    @XmlElement(name = "return")
    protected List<Cliente> _return;

    
    public List<Cliente> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Cliente>();
        }
        return this._return;
    }

}
