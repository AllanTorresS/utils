
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOftipoDocumentoPairOflistaTipoDocumentoPFKeytipoDocumento", propOrder = {
    "tipoDocumento"
})
public class ArrayOftipoDocumentoPairOflistaTipoDocumentoPFKeytipoDocumento {

    @XmlElement(nillable = true)
    protected List<PairOflistaTipoDocumentoPFKeytipoDocumento> tipoDocumento;

    
    public List<PairOflistaTipoDocumentoPFKeytipoDocumento> getTipoDocumento() {
        if (tipoDocumento == null) {
            tipoDocumento = new ArrayList<PairOflistaTipoDocumentoPFKeytipoDocumento>();
        }
        return this.tipoDocumento;
    }

}
