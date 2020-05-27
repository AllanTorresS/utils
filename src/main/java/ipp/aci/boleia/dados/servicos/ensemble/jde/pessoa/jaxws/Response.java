
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", propOrder = {
    "codigoERP",
    "status",
    "msgErro",
    "statusInformacoesTributaria",
    "msgErroInformacoesTributaria"
})
public class Response
    extends EnsResponse
{

    protected Long codigoERP;
    protected Boolean status;
    protected String msgErro;
    protected Boolean statusInformacoesTributaria;
    protected String msgErroInformacoesTributaria;

    
    public Long getCodigoERP() {
        return codigoERP;
    }

    
    public void setCodigoERP(Long value) {
        this.codigoERP = value;
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

    
    public Boolean isStatusInformacoesTributaria() {
        return statusInformacoesTributaria;
    }

    
    public void setStatusInformacoesTributaria(Boolean value) {
        this.statusInformacoesTributaria = value;
    }

    
    public String getMsgErroInformacoesTributaria() {
        return msgErroInformacoesTributaria;
    }

    
    public void setMsgErroInformacoesTributaria(String value) {
        this.msgErroInformacoesTributaria = value;
    }

}
