
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "consultarJDE")
public class ConsultarJDE {

    protected ConsultarJDEReq pRequest;

    
    public ConsultarJDEReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(ConsultarJDEReq value) {
        this.pRequest = value;
    }

}
