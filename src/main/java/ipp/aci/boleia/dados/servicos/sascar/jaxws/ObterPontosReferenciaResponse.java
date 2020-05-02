
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPontosReferenciaResponse", propOrder = {
    "_return"
})
public class ObterPontosReferenciaResponse {

    @XmlElement(name = "return")
    protected List<PontoReferencia> _return;

    
    public List<PontoReferencia> getReturn() {
        if (_return == null) {
            _return = new ArrayList<PontoReferencia>();
        }
        return this._return;
    }

}
