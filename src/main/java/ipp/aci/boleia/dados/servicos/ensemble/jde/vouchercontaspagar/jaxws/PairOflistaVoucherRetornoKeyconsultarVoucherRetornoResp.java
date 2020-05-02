
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp")
public class PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp
    extends ConsultarVoucherRetornoResp
{

    @XmlAttribute(name = "listaVoucherRetornoKey", required = true)
    protected String listaVoucherRetornoKey;

    
    public String getListaVoucherRetornoKey() {
        return listaVoucherRetornoKey;
    }

    
    public void setListaVoucherRetornoKey(String value) {
        this.listaVoucherRetornoKey = value;
    }

}
