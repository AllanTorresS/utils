
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "criarResult"
})
@XmlRootElement(name = "criarResponse")
public class CriarResponse {

    @XmlElement(required = true)
    protected CriarResp criarResult;

    
    public CriarResp getCriarResult() {
        return criarResult;
    }

    
    public void setCriarResult(CriarResp value) {
        this.criarResult = value;
    }

}
