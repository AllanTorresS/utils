
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "linhaVoucher", propOrder = {
    "linhaDocumento",
    "linhaExtensao"
})
public class LinhaVoucher {

    protected String linhaDocumento;
    protected String linhaExtensao;

    
    public String getLinhaDocumento() {
        return linhaDocumento;
    }

    
    public void setLinhaDocumento(String value) {
        this.linhaDocumento = value;
    }

    
    public String getLinhaExtensao() {
        return linhaExtensao;
    }

    
    public void setLinhaExtensao(String value) {
        this.linhaExtensao = value;
    }

}
