
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfboletoPairOfboletosKeyboleto", propOrder = {
    "boleto"
})
public class ArrayOfboletoPairOfboletosKeyboleto {

    @XmlElement(nillable = true)
    protected List<PairOfboletosKeyboleto> boleto;

    
    public List<PairOfboletosKeyboleto> getBoleto() {
        if (boleto == null) {
            boleto = new ArrayList<PairOfboletosKeyboleto>();
        }
        return this.boleto;
    }

}
