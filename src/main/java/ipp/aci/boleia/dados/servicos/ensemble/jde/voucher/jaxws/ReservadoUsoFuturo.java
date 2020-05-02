
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reservadoUsoFuturo", propOrder = {
    "codigoUsoFuturo",
    "dataUsoFuturo",
    "valorUsoFuturo",
    "numeroUsoFuturo",
    "refUsoFuturo"
})
public class ReservadoUsoFuturo {

    protected String codigoUsoFuturo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataUsoFuturo;
    protected BigDecimal valorUsoFuturo;
    protected Long numeroUsoFuturo;
    protected String refUsoFuturo;

    
    public String getCodigoUsoFuturo() {
        return codigoUsoFuturo;
    }

    
    public void setCodigoUsoFuturo(String value) {
        this.codigoUsoFuturo = value;
    }

    
    public XMLGregorianCalendar getDataUsoFuturo() {
        return dataUsoFuturo;
    }

    
    public void setDataUsoFuturo(XMLGregorianCalendar value) {
        this.dataUsoFuturo = value;
    }

    
    public BigDecimal getValorUsoFuturo() {
        return valorUsoFuturo;
    }

    
    public void setValorUsoFuturo(BigDecimal value) {
        this.valorUsoFuturo = value;
    }

    
    public Long getNumeroUsoFuturo() {
        return numeroUsoFuturo;
    }

    
    public void setNumeroUsoFuturo(Long value) {
        this.numeroUsoFuturo = value;
    }

    
    public String getRefUsoFuturo() {
        return refUsoFuturo;
    }

    
    public void setRefUsoFuturo(String value) {
        this.refUsoFuturo = value;
    }

}
