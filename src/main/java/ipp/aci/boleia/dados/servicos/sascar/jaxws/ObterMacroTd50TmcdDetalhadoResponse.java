
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTd50TmcdDetalhadoResponse", propOrder = {
    "_return"
})
public class ObterMacroTd50TmcdDetalhadoResponse {

    @XmlElement(name = "return")
    protected List<MacroTd50TmcdDetalhado> _return;

    
    public List<MacroTd50TmcdDetalhado> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MacroTd50TmcdDetalhado>();
        }
        return this._return;
    }

}
