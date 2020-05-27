
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "demostrativoAbatimentoCreditoCombReq", propOrder = {
    "codigoCliente",
    "dataInicial",
    "dataFinal"
})
public class DemostrativoAbatimentoCreditoCombReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected BigDecimal codigoCliente;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInicial;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFinal;

    
    public BigDecimal getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(BigDecimal value) {
        this.codigoCliente = value;
    }

    
    public XMLGregorianCalendar getDataInicial() {
        return dataInicial;
    }

    
    public void setDataInicial(XMLGregorianCalendar value) {
        this.dataInicial = value;
    }

    
    public XMLGregorianCalendar getDataFinal() {
        return dataFinal;
    }

    
    public void setDataFinal(XMLGregorianCalendar value) {
        this.dataFinal = value;
    }

}
