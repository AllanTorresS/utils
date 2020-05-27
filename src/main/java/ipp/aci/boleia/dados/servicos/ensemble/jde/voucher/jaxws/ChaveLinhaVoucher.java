
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chaveLinhaVoucher", propOrder = {
    "parcela",
    "extensaoParcela"
})
public class ChaveLinhaVoucher {

    protected String parcela;
    protected BigDecimal extensaoParcela;

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public BigDecimal getExtensaoParcela() {
        return extensaoParcela;
    }

    
    public void setExtensaoParcela(BigDecimal value) {
        this.extensaoParcela = value;
    }

}
