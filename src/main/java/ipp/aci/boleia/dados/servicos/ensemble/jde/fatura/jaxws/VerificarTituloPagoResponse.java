
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "verificarTituloPagoResult"
})
@XmlRootElement(name = "verificarTituloPagoResponse")
public class VerificarTituloPagoResponse {

    @XmlElement(required = true)
    protected VerificarTituloPagoResp verificarTituloPagoResult;

    
    public VerificarTituloPagoResp getVerificarTituloPagoResult() {
        return verificarTituloPagoResult;
    }

    
    public void setVerificarTituloPagoResult(VerificarTituloPagoResp value) {
        this.verificarTituloPagoResult = value;
    }

}
