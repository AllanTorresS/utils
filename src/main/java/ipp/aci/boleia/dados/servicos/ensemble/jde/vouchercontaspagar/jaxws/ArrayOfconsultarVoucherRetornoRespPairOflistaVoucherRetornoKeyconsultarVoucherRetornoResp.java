
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfconsultarVoucherRetornoRespPairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp", propOrder = {
    "consultarVoucherRetornoResp"
})
public class ArrayOfconsultarVoucherRetornoRespPairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp {

    @XmlElement(nillable = true)
    protected List<PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp> consultarVoucherRetornoResp;

    
    public List<PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp> getConsultarVoucherRetornoResp() {
        if (consultarVoucherRetornoResp == null) {
            consultarVoucherRetornoResp = new ArrayList<PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp>();
        }
        return this.consultarVoucherRetornoResp;
    }

}
