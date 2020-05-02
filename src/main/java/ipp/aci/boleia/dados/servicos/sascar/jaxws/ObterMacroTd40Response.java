
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterMacroTd40Response", propOrder = {
    "_return"
})
public class ObterMacroTd40Response {

    @XmlElement(name = "return")
    protected List<MacroTd40> _return;

    
    public List<MacroTd40> getReturn() {
        if (_return == null) {
            _return = new ArrayList<MacroTd40>();
        }
        return this._return;
    }

}
