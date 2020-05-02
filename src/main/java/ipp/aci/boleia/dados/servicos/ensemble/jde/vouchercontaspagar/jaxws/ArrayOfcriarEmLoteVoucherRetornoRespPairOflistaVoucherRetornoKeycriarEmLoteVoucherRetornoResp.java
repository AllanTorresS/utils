
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfcriarEmLoteVoucherRetornoRespPairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp", propOrder = {
    "criarEmLoteVoucherRetornoResp"
})
public class ArrayOfcriarEmLoteVoucherRetornoRespPairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp {

    @XmlElement(nillable = true)
    protected List<PairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp> criarEmLoteVoucherRetornoResp;

    
    public List<PairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp> getCriarEmLoteVoucherRetornoResp() {
        if (criarEmLoteVoucherRetornoResp == null) {
            criarEmLoteVoucherRetornoResp = new ArrayList<PairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp>();
        }
        return this.criarEmLoteVoucherRetornoResp;
    }

}
