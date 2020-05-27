
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "incluirJDEResp", propOrder = {
    "tipoDocumento",
    "numeroDocumento",
    "ciaDocumento",
    "qtdeParcelas",
    "status",
    "msgErro",
    "codigoErro",
    "descricaoErro"
})
public class IncluirJDEResp
    extends EnsResponse
{

    protected String tipoDocumento;
    protected BigDecimal numeroDocumento;
    protected String ciaDocumento;
    protected BigDecimal qtdeParcelas;
    protected Boolean status;
    protected String msgErro;
    protected String codigoErro;
    protected String descricaoErro;

    
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

    
    public String getCiaDocumento() {
        return ciaDocumento;
    }

    
    public void setCiaDocumento(String value) {
        this.ciaDocumento = value;
    }

    
    public BigDecimal getQtdeParcelas() {
        return qtdeParcelas;
    }

    
    public void setQtdeParcelas(BigDecimal value) {
        this.qtdeParcelas = value;
    }

    
    public Boolean isStatus() {
        return status;
    }

    
    public void setStatus(Boolean value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public String getDescricaoErro() {
        return descricaoErro;
    }

    
    public void setDescricaoErro(String value) {
        this.descricaoErro = value;
    }

}
