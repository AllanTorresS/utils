
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTd50TmcdResponse", propOrder = {
    "_return"
})
public class ObterMacroTd50TmcdResponse {

    @XmlElement(name = "return")
    protected List<MacroTd50Tmcd> _return;

    
    public List<MacroTd50Tmcd> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MacroTd50Tmcd>();
        }
        return this._return;
    }

}
