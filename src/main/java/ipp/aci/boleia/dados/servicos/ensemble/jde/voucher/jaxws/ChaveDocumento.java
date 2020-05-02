
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chaveDocumento", propOrder = {
    "numeroDocumento",
    "tipoDocumento",
    "companhiaDocumento"
})
public class ChaveDocumento {

    protected Long numeroDocumento;
    protected String tipoDocumento;
    protected String companhiaDocumento;

    
    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(Long value) {
        this.numeroDocumento = value;
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
