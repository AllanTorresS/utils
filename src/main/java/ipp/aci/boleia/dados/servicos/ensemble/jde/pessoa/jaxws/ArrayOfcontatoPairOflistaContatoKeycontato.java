
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfcontatoPairOflistaContatoKeycontato", propOrder = {
    "contato"
})
public class ArrayOfcontatoPairOflistaContatoKeycontato {

    @XmlElement(nillable = true)
    protected List<PairOflistaContatoKeycontato> contato;

    
    public List<PairOflistaContatoKeycontato> getContato() {
        if (contato == null) {
            contato = new ArrayList<PairOflistaContatoKeycontato>();
        }
        return this.contato;
    }

}
