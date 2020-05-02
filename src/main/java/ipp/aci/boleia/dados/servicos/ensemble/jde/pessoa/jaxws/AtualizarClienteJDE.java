
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "atualizarClienteJDE")
public class AtualizarClienteJDE {

    protected AtualizarJDEReq pRequest;

    
    public AtualizarJDEReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(AtualizarJDEReq value) {
        this.pRequest = value;
    }

}
