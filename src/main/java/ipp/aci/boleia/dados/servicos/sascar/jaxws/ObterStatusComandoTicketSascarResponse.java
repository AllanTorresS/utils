
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterStatusComandoTicketSascarResponse", propOrder = {
    "_return"
})
public class ObterStatusComandoTicketSascarResponse {

    @XmlElement(name = "return")
    protected List<StatusComando> _return;

    
    public List<StatusComando> getReturn() {
        if (_return == null) {
            _return = new ArrayList<StatusComando>();
        }
        return this._return;
    }

}
