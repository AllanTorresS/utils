
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfemailPairOflistaEmailKeyemail", propOrder = {
    "email"
})
public class ArrayOfemailPairOflistaEmailKeyemail {

    protected List<PairOflistaEmailKeyemail> email;

    
    public List<PairOflistaEmailKeyemail> getEmail() {
        if (email == null) {
            email = new ArrayList<PairOflistaEmailKeyemail>();
        }
        return this.email;
    }

}
