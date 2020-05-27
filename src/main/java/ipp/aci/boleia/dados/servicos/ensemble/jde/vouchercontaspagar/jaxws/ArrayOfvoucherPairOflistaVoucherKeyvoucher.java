
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfvoucherPairOflistaVoucherKeyvoucher", propOrder = {
    "voucher"
})
public class ArrayOfvoucherPairOflistaVoucherKeyvoucher {

    @XmlElement(nillable = true)
    protected List<PairOflistaVoucherKeyvoucher> voucher;

    
    public List<PairOflistaVoucherKeyvoucher> getVoucher() {
        if (voucher == null) {
            voucher = new ArrayList<PairOflistaVoucherKeyvoucher>();
        }
        return this.voucher;
    }

}
