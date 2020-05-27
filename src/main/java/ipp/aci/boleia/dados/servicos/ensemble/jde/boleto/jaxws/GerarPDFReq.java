
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GerarPDFReq", propOrder = {
    "codigoClienteAN8",
    "centroDeCusto",
    "codigoDependencia",
    "tipoDocumento",
    "modeloNF",
    "serieNF",
    "numeroNF",
    "parcela",
    "numeroBoleto",
    "ciaFiscal"
})
public class GerarPDFReq
    extends EnsRequest
{

    protected Long codigoClienteAN8;
    protected String centroDeCusto;
    protected String codigoDependencia;
    @XmlElement(required = true)
    protected String tipoDocumento;
    protected String modeloNF;
    protected String serieNF;
    @XmlElement(required = true)
    protected String numeroNF;
    @XmlElement(required = true)
    protected String parcela;
    protected String numeroBoleto;
    @XmlElement(required = true)
    protected String ciaFiscal;

    
    public Long getCodigoClienteAN8() {
        return codigoClienteAN8;
    }

    
    public void setCodigoClienteAN8(Long value) {
        this.codigoClienteAN8 = value;
    }

    
    public String getCentroDeCusto() {
        return centroDeCusto;
    }

    
    public void setCentroDeCusto(String value) {
        this.centroDeCusto = value;
    }

    
    public String getCodigoDependencia() {
        return codigoDependencia;
    }

    
    public void setCodigoDependencia(String value) {
        this.codigoDependencia = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getModeloNF() {
        return modeloNF;
    }

    
    public void setModeloNF(String value) {
        this.modeloNF = value;
    }

    
    public String getSerieNF() {
        return serieNF;
    }

    
    public void setSerieNF(String value) {
        this.serieNF = value;
    }

    
    public String getNumeroNF() {
        return numeroNF;
    }

    
    public void setNumeroNF(String value) {
        this.numeroNF = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    
    public void setNumeroBoleto(String value) {
        this.numeroBoleto = value;
    }

    
    public String getCiaFiscal() {
        return ciaFiscal;
    }

    
    public void setCiaFiscal(String value) {
        this.ciaFiscal = value;
    }

}
