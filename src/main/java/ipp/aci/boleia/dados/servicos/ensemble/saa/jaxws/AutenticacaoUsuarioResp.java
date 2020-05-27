
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "autenticacaoUsuarioResp", propOrder = {
    "usuarioAutenticou",
    "status",
    "msgErro"
})
public class AutenticacaoUsuarioResp
    extends EnsResponse
{

    protected Long usuarioAutenticou;
    protected byte[] status;
    protected String msgErro;

    
    public Long getUsuarioAutenticou() {
        return usuarioAutenticou;
    }

    
    public void setUsuarioAutenticou(Long value) {
        this.usuarioAutenticou = value;
    }

    
    public byte[] getStatus() {
        return status;
    }

    
    public void setStatus(byte[] value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

}
