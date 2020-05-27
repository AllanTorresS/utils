
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificarTituloPagoReq", propOrder = {
    "companhia",
    "numeroFaturaFornecedor",
    "companhiaDocumento",
    "numeroDocumento",
    "tipoDocumento",
    "parcela"
})
public class VerificarTituloPagoReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected String companhia;
    protected String numeroFaturaFornecedor;
    protected String companhiaDocumento;
    protected BigInteger numeroDocumento;
    protected String tipoDocumento;
    protected String parcela;

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getNumeroFaturaFornecedor() {
        return numeroFaturaFornecedor;
    }

    
    public void setNumeroFaturaFornecedor(String value) {
        this.numeroFaturaFornecedor = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public BigInteger getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(BigInteger value) {
        this.numeroDocumento = value;
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
