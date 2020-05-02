
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicoesRFNacionalResponse", propOrder = {
    "_return"
})
public class ObterPacotePosicoesRFNacionalResponse {

    @XmlElement(name = "return")
    protected List<PacotePosicaoRFNacional> _return;

    
    public List<PacotePosicaoRFNacional> getReturn() {
        if (_return == null) {
            _return = new ArrayList<PacotePosicaoRFNacional>();
        }
        return this._return;
    }

}
