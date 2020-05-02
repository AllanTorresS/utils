
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTms3Response", propOrder = {
    "_return"
})
public class ObterMacroTms3Response {

    @XmlElement(name = "return")
    protected List<MacroTms3> _return;

    
    public List<MacroTms3> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MacroTms3>();
        }
        return this._return;
    }

}
