
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfdistribuicaoContabildistribuicaoContabil", propOrder = {
    "distribuicaoContabil"
})
public class ArrayOfdistribuicaoContabildistribuicaoContabil {

    @XmlElement(nillable = true)
    protected List<DistribuicaoContabil> distribuicaoContabil;

    
    public List<DistribuicaoContabil> getDistribuicaoContabil() {
        if (distribuicaoContabil == null) {
            distribuicaoContabil = new ArrayList<DistribuicaoContabil>();
        }
        return this.distribuicaoContabil;
    }

}
