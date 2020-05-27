
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayoutAcaoEmbarcadaAVDResponse", propOrder = {
    "_return"
})
public class ObterLayoutAcaoEmbarcadaAVDResponse {

    @XmlElement(name = "return")
    protected List<LayoutAcaoEmbarcadaAVD> _return;

    
    public List<LayoutAcaoEmbarcadaAVD> getReturn() {
        if (_return == null) {
            _return = new ArrayList<LayoutAcaoEmbarcadaAVD>();
        }
        return this._return;
    }

}
