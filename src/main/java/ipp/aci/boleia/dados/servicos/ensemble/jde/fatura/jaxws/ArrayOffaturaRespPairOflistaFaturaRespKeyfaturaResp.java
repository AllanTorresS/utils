
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOffaturaRespPairOflistaFaturaRespKeyfaturaResp", propOrder = {
    "faturaResp"
})
public class ArrayOffaturaRespPairOflistaFaturaRespKeyfaturaResp {

    @XmlElement(nillable = true)
    protected List<PairOflistaFaturaRespKeyfaturaResp> faturaResp;

    
    public List<PairOflistaFaturaRespKeyfaturaResp> getFaturaResp() {
        if (faturaResp == null) {
            faturaResp = new ArrayList<PairOflistaFaturaRespKeyfaturaResp>();
        }
        return this.faturaResp;
    }

}
