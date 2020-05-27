
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterDadosAdicionaisResponse", propOrder = {
    "_return"
})
public class ObterDadosAdicionaisResponse {

    @XmlElement(name = "return")
    protected List<DadosAdicionais> _return;

    
    public List<DadosAdicionais> getReturn() {
        if (_return == null) {
            _return = new ArrayList<DadosAdicionais>();
        }
        return this._return;
    }

}
