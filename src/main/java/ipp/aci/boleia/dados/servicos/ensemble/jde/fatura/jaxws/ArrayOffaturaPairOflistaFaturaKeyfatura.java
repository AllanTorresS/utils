
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOffaturaPairOflistaFaturaKeyfatura", propOrder = {
    "fatura"
})
public class ArrayOffaturaPairOflistaFaturaKeyfatura {

    @XmlElement(nillable = true)
    protected List<PairOflistaFaturaKeyfatura> fatura;

    
    public List<PairOflistaFaturaKeyfatura> getFatura() {
        if (fatura == null) {
            fatura = new ArrayList<PairOflistaFaturaKeyfatura>();
        }
        return this.fatura;
    }

}
