
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfcontabilGeralcontabilGeral", propOrder = {
    "contabilGeral"
})
public class ArrayOfcontabilGeralcontabilGeral {

    @XmlElement(nillable = true)
    protected List<ContabilGeral> contabilGeral;

    
    public List<ContabilGeral> getContabilGeral() {
        if (contabilGeral == null) {
            contabilGeral = new ArrayList<ContabilGeral>();
        }
        return this.contabilGeral;
    }

}
