
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfvoucherDetalhePairOflistaVoucherDetalheKeyvoucherDetalhe", propOrder = {
    "voucherDetalhe"
})
public class ArrayOfvoucherDetalhePairOflistaVoucherDetalheKeyvoucherDetalhe {

    @XmlElement(nillable = true)
    protected List<PairOflistaVoucherDetalheKeyvoucherDetalhe> voucherDetalhe;

    
    public List<PairOflistaVoucherDetalheKeyvoucherDetalhe> getVoucherDetalhe() {
        if (voucherDetalhe == null) {
            voucherDetalhe = new ArrayList<PairOflistaVoucherDetalheKeyvoucherDetalhe>();
        }
        return this.voucherDetalhe;
    }

}
