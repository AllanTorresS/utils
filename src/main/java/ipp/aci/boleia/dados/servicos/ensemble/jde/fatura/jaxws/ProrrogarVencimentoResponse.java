
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "prorrogarVencimentoResult"
})
@XmlRootElement(name = "prorrogarVencimentoResponse")
public class ProrrogarVencimentoResponse {

    @XmlElement(required = true)
    protected ProrrogarVencimentoResp prorrogarVencimentoResult;

    
    public ProrrogarVencimentoResp getProrrogarVencimentoResult() {
        return prorrogarVencimentoResult;
    }

    
    public void setProrrogarVencimentoResult(ProrrogarVencimentoResp value) {
        this.prorrogarVencimentoResult = value;
    }

}
