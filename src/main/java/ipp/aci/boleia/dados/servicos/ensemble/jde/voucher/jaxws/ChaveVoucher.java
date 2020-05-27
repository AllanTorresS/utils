
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chaveVoucher", propOrder = {
    "documento",
    "tipoDocumento",
    "companhiaDocumento"
})
public class ChaveVoucher {

    protected Long documento;
    protected String tipoDocumento;
    protected String companhiaDocumento;

    
    public Long getDocumento() {
        return documento;
    }

    
    public void setDocumento(Long value) {
        this.documento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

}
