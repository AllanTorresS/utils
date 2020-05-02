
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicoesResponse", propOrder = {
    "_return"
})
public class ObterPacotePosicoesResponse {

    @XmlElement(name = "return")
    protected List<PacotePosicao> _return;

    
    public List<PacotePosicao> getReturn() {
        if (_return == null) {
            _return = new ArrayList<PacotePosicao>();
        }
        return this._return;
    }

}
