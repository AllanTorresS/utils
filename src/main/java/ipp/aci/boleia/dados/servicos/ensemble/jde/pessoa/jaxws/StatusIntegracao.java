
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statusIntegracao", propOrder = {
    "status",
    "mensagem",
    "sessionId"
})
public class StatusIntegracao
    extends EnsResponse
{

    protected Boolean status;
    protected String mensagem;
    protected Long sessionId;

    
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
