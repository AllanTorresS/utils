
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "usoFuturo", propOrder = {
    "codigo",
    "data",
    "valor",
    "numero",
    "referencia"
})
public class UsoFuturo {

    protected String codigo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected BigDecimal valor;
    protected Long numero;
    protected String referencia;

    
    public String getCodigo() {
        return codigo;
    }

    
    public void setCodigo(String value) {
        this.codigo = value;
    }

    
    public XMLGregorianCalendar getData() {
        return data;
    }

    
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    
    public BigDecimal getValor() {
        return valor;
    }

    
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

    
    public Long getNumero() {
        return numero;
    }

    
    public void setNumero(Long value) {
        this.numero = value;
    }

    
    public String getReferencia() {
        return referencia;
    }

    
    public void setReferencia(String value) {
        this.referencia = value;
    }

}
