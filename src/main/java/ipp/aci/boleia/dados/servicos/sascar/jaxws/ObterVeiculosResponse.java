
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterVeiculosResponse", propOrder = {
    "_return"
})
public class ObterVeiculosResponse {

    @XmlElement(name = "return")
    protected List<Veiculo> _return;

    
    public List<Veiculo> getReturn() {
        if (_return == null) {
            _return = new ArrayList<Veiculo>();
        }
        return this._return;
    }

}
