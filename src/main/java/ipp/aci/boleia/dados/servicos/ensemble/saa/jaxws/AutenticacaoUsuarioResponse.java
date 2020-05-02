
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "autenticacaoUsuarioResult"
})
@XmlRootElement(name = "autenticacaoUsuarioResponse")
public class AutenticacaoUsuarioResponse {

    @XmlElement(required = true)
    protected AutenticacaoUsuarioResp autenticacaoUsuarioResult;

    
    public AutenticacaoUsuarioResp getAutenticacaoUsuarioResult() {
        return autenticacaoUsuarioResult;
    }

    
    public void setAutenticacaoUsuarioResult(AutenticacaoUsuarioResp value) {
        this.autenticacaoUsuarioResult = value;
    }

}
