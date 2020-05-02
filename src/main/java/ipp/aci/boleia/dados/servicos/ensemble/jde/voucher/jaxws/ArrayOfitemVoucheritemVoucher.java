
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfitemVoucheritemVoucher", propOrder = {
    "itemVoucher"
})
public class ArrayOfitemVoucheritemVoucher {

    @XmlElement(nillable = true)
    protected List<ItemVoucher> itemVoucher;

    
    public List<ItemVoucher> getItemVoucher() {
        if (itemVoucher == null) {
            itemVoucher = new ArrayList<ItemVoucher>();
        }
        return this.itemVoucher;
    }

}
