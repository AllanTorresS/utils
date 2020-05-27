
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gerarJDEResult"
})
@XmlRootElement(name = "gerarJDEResponse")
public class GerarJDEResponse {

    @XmlElement(required = true)
    protected GerarJDEResp gerarJDEResult;

    
    public GerarJDEResp getGerarJDEResult() {
        return gerarJDEResult;
    }

    
    public void setGerarJDEResult(GerarJDEResp value) {
        this.gerarJDEResult = value;
    }

}
