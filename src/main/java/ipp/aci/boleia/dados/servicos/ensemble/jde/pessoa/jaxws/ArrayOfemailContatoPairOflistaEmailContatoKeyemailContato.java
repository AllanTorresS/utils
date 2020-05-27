
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfemailContatoPairOflistaEmailContatoKeyemailContato", propOrder = {
    "emailContato"
})
public class ArrayOfemailContatoPairOflistaEmailContatoKeyemailContato {

    @XmlElement(nillable = true)
    protected List<PairOflistaEmailContatoKeyemailContato> emailContato;

    
    public List<PairOflistaEmailContatoKeyemailContato> getEmailContato() {
        if (emailContato == null) {
            emailContato = new ArrayList<PairOflistaEmailContatoKeyemailContato>();
        }
        return this.emailContato;
    }

}
