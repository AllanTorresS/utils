
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOftelefoneContatoPairOflistaTelefoneContatoKeytelefoneContato", propOrder = {
    "telefoneContato"
})
public class ArrayOftelefoneContatoPairOflistaTelefoneContatoKeytelefoneContato {

    @XmlElement(nillable = true)
    protected List<PairOflistaTelefoneContatoKeytelefoneContato> telefoneContato;

    
    public List<PairOflistaTelefoneContatoKeytelefoneContato> getTelefoneContato() {
        if (telefoneContato == null) {
            telefoneContato = new ArrayList<PairOflistaTelefoneContatoKeytelefoneContato>();
        }
        return this.telefoneContato;
    }

}
