
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayoutResponse", propOrder = {
    "_return"
})
public class ObterLayoutResponse {

    @XmlElement(name = "return")
    protected List<Layout> _return;

    
    public List<Layout> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Layout>();
        }
        return this._return;
    }

}
