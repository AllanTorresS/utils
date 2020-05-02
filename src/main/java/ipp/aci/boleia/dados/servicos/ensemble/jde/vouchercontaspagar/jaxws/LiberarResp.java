
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "liberarResp", propOrder = {
    "status",
    "msgErro",
    "errorCode",
    "errorDescription"
})
public class LiberarResp
    extends EnsResponse
{

    protected Long status;
    protected String msgErro;
    protected String errorCode;
    protected String errorDescription;

    
    public Long getStatus() {
        return status;
    }

    
    public void setStatus(Long value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

    
    public String getErrorCode() {
        return errorCode;
    }

    
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    
    public String getErrorDescription() {
        return errorDescription;
    }

    
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

}
