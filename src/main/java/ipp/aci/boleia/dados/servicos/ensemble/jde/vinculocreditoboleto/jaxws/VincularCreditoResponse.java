
package ipp.aci.boleia.dados.servicos.ensemble.jde.vinculocreditoboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vincularCreditoResponse", propOrder = {
    "e1MessageList",
    "mnDocGerado",
    "szErrorCode",
    "szErrorDescription"
})
public class VincularCreditoResponse
    extends EnsResponse
{

    @XmlElement(name = "E1MessageList")
    protected E1MessageList e1MessageList;
    @XmlElement(name = "MnDocGerado")
    protected Long mnDocGerado;
    @XmlElement(name = "SzErrorCode")
    protected String szErrorCode;
    @XmlElement(name = "SzErrorDescription")
    protected String szErrorDescription;

    
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
