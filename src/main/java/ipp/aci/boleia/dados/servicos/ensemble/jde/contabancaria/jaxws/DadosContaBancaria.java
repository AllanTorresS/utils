
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "dadosContaBancaria")
public class DadosContaBancaria {

    protected DadosContaBancariaReq pRequest;

    
    public DadosContaBancariaReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(DadosContaBancariaReq value) {
        this.pRequest = value;
    }

}
