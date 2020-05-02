
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "demostrativoAbatimentoCreditoComb")
public class DemostrativoAbatimentoCreditoComb {

    protected DemostrativoAbatimentoCreditoCombReq pRequest;

    
    public DemostrativoAbatimentoCreditoCombReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(DemostrativoAbatimentoCreditoCombReq value) {
        this.pRequest = value;
    }

}
