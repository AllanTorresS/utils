
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vincularCreditoRequest", propOrder = {
    "szConta",
    "mnCliente",
    "szCondPagamento",
    "szObservacao",
    "mnCenario",
    "mnValorTransacao",
    "jdDataDaFatura",
    "szPosto",
    "mnNumBoleto",
    "jdDataVencimentoDDJ",
    "e1MessageList",
    "mnDocGerado",
    "szErrorCode",
    "szErrorDescription"
})
public class VincularCreditoRequest
    extends EnsRequest
{

    @XmlElement(name = "SzConta")
    protected String szConta;
    @XmlElement(name = "MnCliente")
    protected Integer mnCliente;
    @XmlElement(name = "SzCondPagamento")
    protected String szCondPagamento;
    @XmlElement(name = "SzObservacao")
    protected String szObservacao;
    @XmlElement(name = "MnCenario")
    protected Integer mnCenario;
    @XmlElement(name = "MnValorTransacao")
    protected BigDecimal mnValorTransacao;
    @XmlElement(name = "JdDataDaFatura")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jdDataDaFatura;
    @XmlElement(name = "SzPosto")
    protected String szPosto;
    @XmlElement(name = "MnNumBoleto")
    protected Long mnNumBoleto;
    @XmlElement(name = "JdDataVencimentoDDJ")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jdDataVencimentoDDJ;
    @XmlElement(name = "E1MessageList")
    protected E1MessageList e1MessageList;
    @XmlElement(name = "MnDocGerado")
    protected Long mnDocGerado;
    @XmlElement(name = "SzErrorCode")
    protected String szErrorCode;
    @XmlElement(name = "SzErrorDescription")
    protected String szErrorDescription;

    
    public String getSzConta() {
        return szConta;
    }

    
    public void setSzConta(String value) {
        this.szConta = value;
    }

    
    public Integer getMnCliente() {
        return mnCliente;
    }

    
    public void setMnCliente(Integer value) {
        this.mnCliente = value;
    }

    
    public String getSzCondPagamento() {
        return szCondPagamento;
    }

    
    public void setSzCondPagamento(String value) {
        this.szCondPagamento = value;
    }

    
    public String getSzObservacao() {
        return szObservacao;
    }

    
    public void setSzObservacao(String value) {
        this.szObservacao = value;
    }

    
    public Integer getMnCenario() {
        return mnCenario;
    }

    
    public void setMnCenario(Integer value) {
        this.mnCenario = value;
    }

    
    public BigDecimal getMnValorTransacao() {
        return mnValorTransacao;
    }

    
    public void setMnValorTransacao(BigDecimal value) {
        this.mnValorTransacao = value;
    }

    
    public XMLGregorianCalendar getJdDataDaFatura() {
        return jdDataDaFatura;
    }

    
    public void setJdDataDaFatura(XMLGregorianCalendar value) {
        this.jdDataDaFatura = value;
    }

    
    public String getSzPosto() {
        return szPosto;
    }

    
    public void setSzPosto(String value) {
        this.szPosto = value;
    }

    
    public Long getMnNumBoleto() {
        return mnNumBoleto;
    }

    
    public void setMnNumBoleto(Long value) {
        this.mnNumBoleto = value;
    }

    
    public XMLGregorianCalendar getJdDataVencimentoDDJ() {
        return jdDataVencimentoDDJ;
    }

    
    public void setJdDataVencimentoDDJ(XMLGregorianCalendar value) {
        this.jdDataVencimentoDDJ = value;
    }

    
    public E1MessageList getE1MessageList() {
        return e1MessageList;
    }

    
    public void setE1MessageList(E1MessageList value) {
        this.e1MessageList = value;
    }

    
    public Long getMnDocGerado() {
        return mnDocGerado;
    }

    
    public void setMnDocGerado(Long value) {
        this.mnDocGerado = value;
    }

    
    public String getSzErrorCode() {
        return szErrorCode;
    }

    
    public void setSzErrorCode(String value) {
        this.szErrorCode = value;
    }

    
    public String getSzErrorDescription() {
        return szErrorDescription;
    }

    
    public void setSzErrorDescription(String value) {
        this.szErrorDescription = value;
    }

}
