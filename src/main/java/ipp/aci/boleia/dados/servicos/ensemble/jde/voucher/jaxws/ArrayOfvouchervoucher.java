
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfvouchervoucher", propOrder = {
    "voucher"
})
public class ArrayOfvouchervoucher {

    @XmlElement(nillable = true)
    protected List<Voucher> voucher;

    
    public List<Voucher> getVoucher() {
        if (voucher == null) {
            voucher = new ArrayList<Voucher>();
        }
        return this.voucher;
    }

}
