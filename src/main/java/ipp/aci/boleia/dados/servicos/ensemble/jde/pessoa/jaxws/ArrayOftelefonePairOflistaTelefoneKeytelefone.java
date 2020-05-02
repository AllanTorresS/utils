
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOftelefonePairOflistaTelefoneKeytelefone", propOrder = {
    "telefone"
})
public class ArrayOftelefonePairOflistaTelefoneKeytelefone {

    protected List<PairOflistaTelefoneKeytelefone> telefone;

    
    public List<PairOflistaTelefoneKeytelefone> getTelefone() {
        if (telefone == null) {
            telefone = new ArrayList<PairOflistaTelefoneKeytelefone>();
        }
        return this.telefone;
    }

}
