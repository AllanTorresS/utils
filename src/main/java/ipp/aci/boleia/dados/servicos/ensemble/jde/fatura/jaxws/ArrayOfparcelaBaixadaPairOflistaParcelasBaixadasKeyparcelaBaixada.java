
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfparcelaBaixadaPairOflistaParcelasBaixadasKeyparcelaBaixada", propOrder = {
    "parcelaBaixada"
})
public class ArrayOfparcelaBaixadaPairOflistaParcelasBaixadasKeyparcelaBaixada {

    @XmlElement(nillable = true)
    protected List<PairOflistaParcelasBaixadasKeyparcelaBaixada> parcelaBaixada;

    
    public List<PairOflistaParcelasBaixadasKeyparcelaBaixada> getParcelaBaixada() {
        if (parcelaBaixada == null) {
            parcelaBaixada = new ArrayList<PairOflistaParcelasBaixadasKeyparcelaBaixada>();
        }
        return this.parcelaBaixada;
    }

}
