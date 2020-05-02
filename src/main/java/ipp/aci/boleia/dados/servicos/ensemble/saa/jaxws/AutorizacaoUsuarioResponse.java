
package ipp.aci.boleia.dados.servicos.ensemble.saa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "autorizacaoUsuarioResult"
})
@XmlRootElement(name = "autorizacaoUsuarioResponse")
public class AutorizacaoUsuarioResponse {

    @XmlElement(required = true)
    protected AutorizacaoResp autorizacaoUsuarioResult;

    
    public AutorizacaoResp getAutorizacaoUsuarioResult() {
        return autorizacaoUsuarioResult;
    }

    
    public void setAutorizacaoUsuarioResult(AutorizacaoResp value) {
        this.autorizacaoUsuarioResult = value;
    }

}
