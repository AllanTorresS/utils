
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request", propOrder = {
    "mnCliente",
    "szCondPagamento",
    "szObservacao",
    "mnCenario",
    "mnValorTransacao",
    "mnNumBoleto",
    "jdDataDaFatura",
    "jdDateDueJulian",
    "mnDocGerado"
})
public class Request
    extends EnsRequest
{

    protected Integer mnCliente;
    protected String szCondPagamento;
    protected String szObservacao;
    protected Integer mnCenario;
    protected BigDecimal mnValorTransacao;
    protected Long mnNumBoleto;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jdDataDaFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar jdDateDueJulian;
    protected Long mnDocGerado;

    
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

    
    public Long getMnNumBoleto() {
        return mnNumBoleto;
    }

    
    public void setMnNumBoleto(Long value) {
        this.mnNumBoleto = value;
    }

    
    public XMLGregorianCalendar getJdDataDaFatura() {
        return jdDataDaFatura;
    }

    
    public void setJdDataDaFatura(XMLGregorianCalendar value) {
        this.jdDataDaFatura = value;
    }

    
    public XMLGregorianCalendar getJdDateDueJulian() {
        return jdDateDueJulian;
    }

    
    public void setJdDateDueJulian(XMLGregorianCalendar value) {
        this.jdDateDueJulian = value;
    }

    
    public Long getMnDocGerado() {
        return mnDocGerado;
    }

    
    public void setMnDocGerado(Long value) {
        this.mnDocGerado = value;
    }

}
