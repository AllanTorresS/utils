
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "prorrogarVencimentoResp", propOrder = {
    "codigoErro",
    "status",
    "mensagem",
    "sessionId"
})
public class ProrrogarVencimentoResp
    extends EnsResponse
{

    protected String codigoErro;
    protected Boolean status;
    protected String mensagem;
    protected Long sessionId;

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public Boolean isStatus() {
        return status;
    }

    
    public void setStatus(Boolean value) {
        this.status = value;
    }

    
    public String getMensagem() {
        return mensagem;
    }

    
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    
    public Long getSessionId() {
        return sessionId;
    }

    
    public void setSessionId(Long value) {
        this.sessionId = value;
    }

}
