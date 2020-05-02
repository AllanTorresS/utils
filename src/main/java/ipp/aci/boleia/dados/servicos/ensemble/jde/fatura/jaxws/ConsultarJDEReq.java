
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarJDEReq", propOrder = {
    "numeroDocumento",
    "companhiaDocumento",
    "tipoDocumento",
    "parcela"
})
public class ConsultarJDEReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected BigDecimal numeroDocumento;
    @XmlElement(required = true)
    protected String companhiaDocumento;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected String parcela;

    
    public BigDecimal getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(BigDecimal value) {
        this.numeroDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

}
