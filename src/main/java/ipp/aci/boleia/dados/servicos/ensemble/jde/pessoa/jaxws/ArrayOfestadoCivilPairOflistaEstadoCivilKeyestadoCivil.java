
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfestadoCivilPairOflistaEstadoCivilKeyestadoCivil", propOrder = {
    "estadoCivil"
})
public class ArrayOfestadoCivilPairOflistaEstadoCivilKeyestadoCivil {

    @XmlElement(nillable = true)
    protected List<PairOflistaEstadoCivilKeyestadoCivil> estadoCivil;

    
    public List<PairOflistaEstadoCivilKeyestadoCivil> getEstadoCivil() {
        if (estadoCivil == null) {
            estadoCivil = new ArrayList<PairOflistaEstadoCivilKeyestadoCivil>();
        }
        return this.estadoCivil;
    }

}
