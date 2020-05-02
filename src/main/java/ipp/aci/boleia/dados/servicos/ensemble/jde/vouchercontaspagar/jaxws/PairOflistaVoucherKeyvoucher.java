
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaVoucherKeyvoucher")
public class PairOflistaVoucherKeyvoucher
    extends Voucher
{

    @XmlAttribute(name = "listaVoucherKey", required = true)
    protected String listaVoucherKey;

    
    public String getListaVoucherKey() {
        return listaVoucherKey;
    }

    
    public void setListaVoucherKey(String value) {
        this.listaVoucherKey = value;
    }

}
