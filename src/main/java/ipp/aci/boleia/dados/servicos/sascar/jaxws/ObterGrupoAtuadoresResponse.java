
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterGrupoAtuadoresResponse", propOrder = {
    "_return"
})
public class ObterGrupoAtuadoresResponse {

    @XmlElement(name = "return")
    protected List<GrupoAtuador> _return;

    
    public List<GrupoAtuador> getReturn() {
        if (_return == null) {
            _return = new ArrayList<GrupoAtuador>();
        }
        return this._return;
    }

}
