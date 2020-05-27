
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dadosContaBancariaResult"
})
@XmlRootElement(name = "dadosContaBancariaResponse")
public class DadosContaBancariaResponse {

    @XmlElement(required = true)
    protected DadosContaBancariaResp dadosContaBancariaResult;

    
    public DadosContaBancariaResp getDadosContaBancariaResult() {
        return dadosContaBancariaResult;
    }

    
    public void setDadosContaBancariaResult(DadosContaBancariaResp value) {
        this.dadosContaBancariaResult = value;
    }

}
