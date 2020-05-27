
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacotePosicoesMotoristaResponse", propOrder = {
    "_return"
})
public class ObterPacotePosicoesMotoristaResponse {

    @XmlElement(name = "return")
    protected List<PacotePosicao> _return;

    
    public List<PacotePosicao> getReturn() {
        if (_return == null) {
            _return = new ArrayList<PacotePosicao>();
        }
        return this._return;
    }

}
