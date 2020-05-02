
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayoutTecladoVeiculosResponse", propOrder = {
    "_return"
})
public class ObterLayoutTecladoVeiculosResponse {

    @XmlElement(name = "return")
    protected List<LayoutTecladoVeiculos> _return;

    
    public List<LayoutTecladoVeiculos> getReturn() {
        if (_return == null) {
            _return = new ArrayList<LayoutTecladoVeiculos>();
        }
        return this._return;
    }

}
