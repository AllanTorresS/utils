
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicaoMotoristaPorRangeJSONResponse", propOrder = {
    "_return"
})
public class ObterPacotePosicaoMotoristaPorRangeJSONResponse {

    @XmlElement(name = "return", nillable = true)
    protected List<String> _return;

    
    public List<String> getReturn() {
        if (_return == null) {
            _return = new ArrayList<String>();
        }
        return this._return;
    }

}
