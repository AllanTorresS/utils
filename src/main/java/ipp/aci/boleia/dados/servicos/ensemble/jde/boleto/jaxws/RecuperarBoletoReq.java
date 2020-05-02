
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarBoletoReq", propOrder = {
    "tipoDocumento",
    "codigoCliente",
    "numeroBoleto",
    "companhia",
    "documento",
    "parcela",
    "tipoDocumentoAbadi"
})
public class RecuperarBoletoReq
    extends EnsRequest
{

    protected String tipoDocumento;
    protected BigDecimal codigoCliente;
    protected String numeroBoleto;
    protected String companhia;
    protected BigDecimal documento;
    protected String parcela;
    protected String tipoDocumentoAbadi;

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public BigDecimal getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(BigDecimal value) {
        this.codigoCliente = value;
    }

    
    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    
    public void setNumeroBoleto(String value) {
        this.numeroBoleto = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public BigDecimal getDocumento() {
        return documento;
    }

    
    public void setDocumento(BigDecimal value) {
        this.documento = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public String getTipoDocumentoAbadi() {
        return tipoDocumentoAbadi;
    }

    
    public void setTipoDocumentoAbadi(String value) {
        this.tipoDocumentoAbadi = value;
    }

}
