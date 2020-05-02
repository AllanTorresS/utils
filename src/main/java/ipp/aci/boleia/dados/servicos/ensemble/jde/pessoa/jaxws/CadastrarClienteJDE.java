
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "cadastrarClienteJDE")
public class CadastrarClienteJDE {

    protected CadastrarJDEReq pRequest;

    
    public CadastrarJDEReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(CadastrarJDEReq value) {
        this.pRequest = value;
    }

}
