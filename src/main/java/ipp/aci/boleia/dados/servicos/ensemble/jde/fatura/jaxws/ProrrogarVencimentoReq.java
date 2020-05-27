
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prorrogarVencimentoReq", propOrder = {
    "documento",
    "tipo",
    "companhia",
    "sufixo",
    "acrecimo",
    "vencimento",
    "alteracao",
    "auditoria",
    "aprovacao",
    "usuario"
})
public class ProrrogarVencimentoReq
    extends EnsRequest
{

    protected BigDecimal documento;
    protected String tipo;
    protected String companhia;
    protected String sufixo;
    protected BigDecimal acrecimo;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vencimento;
    protected String alteracao;
    protected String auditoria;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar aprovacao;
    protected String usuario;

    
    public BigDecimal getDocumento() {
        return documento;
    }

    
    public void setDocumento(BigDecimal value) {
        this.documento = value;
    }

    
    public String getTipo() {
        return tipo;
    }

    
    public void setTipo(String value) {
        this.tipo = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getSufixo() {
        return sufixo;
    }

    
    public void setSufixo(String value) {
        this.sufixo = value;
    }

    
    public BigDecimal getAcrecimo() {
        return acrecimo;
    }

    
    public void setAcrecimo(BigDecimal value) {
        this.acrecimo = value;
    }

    
    public XMLGregorianCalendar getVencimento() {
        return vencimento;
    }

    
    public void setVencimento(XMLGregorianCalendar value) {
        this.vencimento = value;
    }

    
    public String getAlteracao() {
        return alteracao;
    }

    
    public void setAlteracao(String value) {
        this.alteracao = value;
    }

    
    public String getAuditoria() {
        return auditoria;
    }

    
    public void setAuditoria(String value) {
        this.auditoria = value;
    }

    
    public XMLGregorianCalendar getAprovacao() {
        return aprovacao;
    }

    
    public void setAprovacao(XMLGregorianCalendar value) {
        this.aprovacao = value;
    }

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

}
