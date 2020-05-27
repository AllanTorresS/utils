
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaVoucherDetalheKeyvoucherDetalhe")
public class PairOflistaVoucherDetalheKeyvoucherDetalhe
    extends VoucherDetalhe
{

    @XmlAttribute(name = "listaVoucherDetalheKey", required = true)
    protected String listaVoucherDetalheKey;

    
    public String getListaVoucherDetalheKey() {
        return listaVoucherDetalheKey;
    }

    
    public void setListaVoucherDetalheKey(String value) {
        this.listaVoucherDetalheKey = value;
    }

}
