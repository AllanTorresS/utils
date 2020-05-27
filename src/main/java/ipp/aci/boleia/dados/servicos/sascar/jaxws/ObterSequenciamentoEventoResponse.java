
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterSequenciamentoEventoResponse", propOrder = {
    "_return"
})
public class ObterSequenciamentoEventoResponse {

    @XmlElement(name = "return")
    protected List<SequenciamentoEvento> _return;

    
    public List<SequenciamentoEvento> getReturn() {
        if (_return == null) {
            _return = new ArrayList<SequenciamentoEvento>();
        }
        return this._return;
    }

}
