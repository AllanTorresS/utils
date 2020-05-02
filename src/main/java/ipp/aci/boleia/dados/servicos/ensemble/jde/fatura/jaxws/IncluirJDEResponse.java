
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "incluirJDEResult"
})
@XmlRootElement(name = "incluirJDEResponse")
public class IncluirJDEResponse {

    @XmlElement(required = true)
    protected IncluirJDEResp incluirJDEResult;

    
    public IncluirJDEResp getIncluirJDEResult() {
        return incluirJDEResult;
    }

    
    public void setIncluirJDEResult(IncluirJDEResp value) {
        this.incluirJDEResult = value;
    }

}
