
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "recuperarResult"
})
@XmlRootElement(name = "recuperarResponse")
public class RecuperarResponse {

    @XmlElement(required = true)
    protected RecuperarBoletoResp recuperarResult;

    
    public RecuperarBoletoResp getRecuperarResult() {
        return recuperarResult;
    }

    
    public void setRecuperarResult(RecuperarBoletoResp value) {
        this.recuperarResult = value;
    }

}
