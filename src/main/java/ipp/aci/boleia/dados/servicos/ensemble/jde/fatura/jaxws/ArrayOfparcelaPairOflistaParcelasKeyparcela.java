
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfparcelaPairOflistaParcelasKeyparcela", propOrder = {
    "parcela"
})
public class ArrayOfparcelaPairOflistaParcelasKeyparcela {

    @XmlElement(nillable = true)
    protected List<PairOflistaParcelasKeyparcela> parcela;

    
    public List<PairOflistaParcelasKeyparcela> getParcela() {
        if (parcela == null) {
            parcela = new ArrayList<PairOflistaParcelasKeyparcela>();
        }
        return this.parcela;
    }

}
