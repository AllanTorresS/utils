
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificarTituloPagoResp", propOrder = {
    "dataBaixa",
    "status",
    "msgErro"
})
public class VerificarTituloPagoResp
    extends EnsResponse
{

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataBaixa;
    protected Boolean status;
    protected String msgErro;

    
    public XMLGregorianCalendar getDataBaixa() {
        return dataBaixa;
    }

    
    public void setDataBaixa(XMLGregorianCalendar value) {
        this.dataBaixa = value;
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

}
