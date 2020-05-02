
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "liberarReq", propOrder = {
    "cenario",
    "ciaDocumento",
    "tipoDocumento",
    "numeroDocumento",
    "linhaDocumento"
})
public class LiberarReq
    extends EnsRequest
{

    protected long cenario;
    @XmlElement(required = true)
    protected String ciaDocumento;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected BigDecimal numeroDocumento;
    @XmlElement(required = true)
    protected String linhaDocumento;

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public String getCiaDocumento() {
        return ciaDocumento;
    }

    
    public void setCiaDocumento(String value) {
        this.ciaDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public BigDecimal getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(BigDecimal value) {
        this.numeroDocumento = value;
    }

    
    public String getLinhaDocumento() {
        return linhaDocumento;
    }

    
    public void setLinhaDocumento(String value) {
        this.linhaDocumento = value;
    }

}
