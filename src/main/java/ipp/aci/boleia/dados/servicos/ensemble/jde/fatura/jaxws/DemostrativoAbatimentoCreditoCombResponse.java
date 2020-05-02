
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "demostrativoAbatimentoCreditoCombResult"
})
@XmlRootElement(name = "demostrativoAbatimentoCreditoCombResponse")
public class DemostrativoAbatimentoCreditoCombResponse {

    @XmlElement(required = true)
    protected DemostrativoAbatimentoCreditoCombResp demostrativoAbatimentoCreditoCombResult;

    
    public DemostrativoAbatimentoCreditoCombResp getDemostrativoAbatimentoCreditoCombResult() {
        return demostrativoAbatimentoCreditoCombResult;
    }

    
    public void setDemostrativoAbatimentoCreditoCombResult(DemostrativoAbatimentoCreditoCombResp value) {
        this.demostrativoAbatimentoCreditoCombResult = value;
    }

}
