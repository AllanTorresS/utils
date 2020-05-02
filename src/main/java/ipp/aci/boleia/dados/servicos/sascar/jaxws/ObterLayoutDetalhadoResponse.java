
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayoutDetalhadoResponse", propOrder = {
    "_return"
})
public class ObterLayoutDetalhadoResponse {

    @XmlElement(name = "return")
    protected List<LayoutDetalhado> _return;

    
    public List<LayoutDetalhado> getReturn() {
        if (_return == null) {
            _return = new ArrayList<LayoutDetalhado>();
        }
        return this._return;
    }

}
