
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOflinhaVoucherlinhaVoucher", propOrder = {
    "linhaVoucher"
})
public class ArrayOflinhaVoucherlinhaVoucher {

    @XmlElement(nillable = true)
    protected List<LinhaVoucher> linhaVoucher;

    
    public List<LinhaVoucher> getLinhaVoucher() {
        if (linhaVoucher == null) {
            linhaVoucher = new ArrayList<LinhaVoucher>();
        }
        return this.linhaVoucher;
    }

}
