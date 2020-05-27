
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDetalhePairOflistaDetalhesKeyDetalhe", propOrder = {
    "detalhe"
})
public class ArrayOfDetalhePairOflistaDetalhesKeyDetalhe {

    @XmlElement(name = "Detalhe", nillable = true)
    protected List<PairOflistaDetalhesKeyDetalhe> detalhe;

    
    public List<PairOflistaDetalhesKeyDetalhe> getDetalhe() {
        if (detalhe == null) {
            detalhe = new ArrayList<PairOflistaDetalhesKeyDetalhe>();
        }
        return this.detalhe;
    }

}
