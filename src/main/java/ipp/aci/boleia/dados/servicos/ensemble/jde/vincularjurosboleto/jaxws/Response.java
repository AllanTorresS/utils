
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", namespace = "http://ipiranga.com.br/statusIntegracao", propOrder = {
    "e1MessageList",
    "mnDocGerado",
    "szErrorCode",
    "szErrorDescription"
})
public class Response
    extends V3
{

    protected E1MessageList e1MessageList;
    protected Long mnDocGerado;
    protected String szErrorCode;
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
