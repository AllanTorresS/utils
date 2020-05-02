
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfdistribuicaoContabilPairOflistaDistribuicaoContabilKeydistribuicaoContabil", propOrder = {
    "distribuicaoContabil"
})
public class ArrayOfdistribuicaoContabilPairOflistaDistribuicaoContabilKeydistribuicaoContabil {

    @XmlElement(nillable = true)
    protected List<PairOflistaDistribuicaoContabilKeydistribuicaoContabil> distribuicaoContabil;

    
    public List<PairOflistaDistribuicaoContabilKeydistribuicaoContabil> getDistribuicaoContabil() {
        if (distribuicaoContabil == null) {
            distribuicaoContabil = new ArrayList<PairOflistaDistribuicaoContabilKeydistribuicaoContabil>();
        }
        return this.distribuicaoContabil;
    }

}
