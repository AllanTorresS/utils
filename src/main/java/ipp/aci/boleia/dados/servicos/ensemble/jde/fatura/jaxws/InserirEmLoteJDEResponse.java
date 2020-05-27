
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inserirEmLoteJDEResult"
})
@XmlRootElement(name = "inserirEmLoteJDEResponse")
public class InserirEmLoteJDEResponse {

    @XmlElement(required = true)
    protected InserirEmLoteJDEResp inserirEmLoteJDEResult;

    
    public InserirEmLoteJDEResp getInserirEmLoteJDEResult() {
        return inserirEmLoteJDEResult;
    }

    
    public void setInserirEmLoteJDEResult(InserirEmLoteJDEResp value) {
        this.inserirEmLoteJDEResult = value;
    }

}
