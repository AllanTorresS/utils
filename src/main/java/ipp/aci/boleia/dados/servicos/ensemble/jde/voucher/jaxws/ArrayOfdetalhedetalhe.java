
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfdetalhedetalhe", propOrder = {
    "detalhe"
})
public class ArrayOfdetalhedetalhe {

    @XmlElement(nillable = true)
    protected List<Detalhe> detalhe;

    
    public List<Detalhe> getDetalhe() {
        if (detalhe == null) {
            detalhe = new ArrayList<Detalhe>();
        }
        return this.detalhe;
    }

}
