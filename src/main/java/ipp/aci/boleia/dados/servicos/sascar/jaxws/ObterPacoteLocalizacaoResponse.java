
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterPacoteLocalizacaoResponse", propOrder = {
    "_return"
})
public class ObterPacoteLocalizacaoResponse {

    @XmlElement(name = "return")
    protected List<PacoteLocalizacao> _return;

    
    public List<PacoteLocalizacao> getReturn() {
        if (_return == null) {
            _return = new ArrayList<PacoteLocalizacao>();
        }
        return this._return;
    }

}
