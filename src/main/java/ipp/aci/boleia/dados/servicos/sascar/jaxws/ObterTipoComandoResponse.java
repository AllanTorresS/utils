
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterTipoComandoResponse", propOrder = {
    "_return"
})
public class ObterTipoComandoResponse {

    @XmlElement(name = "return")
    protected List<TipoComando> _return;

    
    public List<TipoComando> getReturn() {
        if (_return == null) {
            _return = new ArrayList<TipoComando>();
        }
        return this._return;
    }

}
