
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "atualizarClienteJDEResult"
})
@XmlRootElement(name = "atualizarClienteJDEResponse")
public class AtualizarClienteJDEResponse {

    @XmlElement(required = true)
    protected Response atualizarClienteJDEResult;

    
    public Response getAtualizarClienteJDEResult() {
        return atualizarClienteJDEResult;
    }

    
    public void setAtualizarClienteJDEResult(Response value) {
        this.atualizarClienteJDEResult = value;
    }

}
