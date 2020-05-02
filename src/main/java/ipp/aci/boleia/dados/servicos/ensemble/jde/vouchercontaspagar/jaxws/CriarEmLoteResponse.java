
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "criarEmLoteResult"
})
@XmlRootElement(name = "criarEmLoteResponse")
public class CriarEmLoteResponse {

    @XmlElement(required = true)
    protected CriarEmLoteResp criarEmLoteResult;

    
    public CriarEmLoteResp getCriarEmLoteResult() {
        return criarEmLoteResult;
    }

    
    public void setCriarEmLoteResult(CriarEmLoteResp value) {
        this.criarEmLoteResult = value;
    }

}
