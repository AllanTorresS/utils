
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfimpostoPairOflistaImpostoKeyimposto", propOrder = {
    "imposto"
})
public class ArrayOfimpostoPairOflistaImpostoKeyimposto {

    protected List<PairOflistaImpostoKeyimposto> imposto;

    
    public List<PairOflistaImpostoKeyimposto> getImposto() {
        if (imposto == null) {
            imposto = new ArrayList<PairOflistaImpostoKeyimposto>();
        }
        return this.imposto;
    }

}
