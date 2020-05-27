
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura", propOrder = {
    "itemFatura"
})
public class ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura {

    @XmlElement(nillable = true)
    protected List<PairOflistaItemFaturaKeyitemFatura> itemFatura;

    
    public List<PairOflistaItemFaturaKeyitemFatura> getItemFatura() {
        if (itemFatura == null) {
            itemFatura = new ArrayList<PairOflistaItemFaturaKeyitemFatura>();
        }
        return this.itemFatura;
    }

}
