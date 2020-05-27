
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfitemPairOflistaItensKeyitem", propOrder = {
    "item"
})
public class ArrayOfitemPairOflistaItensKeyitem {

    @XmlElement(nillable = true)
    protected List<PairOflistaItensKeyitem> item;

    
    public List<PairOflistaItensKeyitem> getItem() {
        if (item == null) {
            item = new ArrayList<PairOflistaItensKeyitem>();
        }
        return this.item;
    }

}
