
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMotoristasVeiculosResponse", propOrder = {
    "_return"
})
public class ObterMotoristasVeiculosResponse {

    @XmlElement(name = "return")
    protected List<MotoristaVeiculo> _return;

    
    public List<MotoristaVeiculo> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MotoristaVeiculo>();
        }
        return this._return;
    }

}
