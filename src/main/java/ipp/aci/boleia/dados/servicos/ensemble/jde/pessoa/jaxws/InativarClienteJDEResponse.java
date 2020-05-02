
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inativarClienteJDEResult"
})
@XmlRootElement(name = "inativarClienteJDEResponse")
public class InativarClienteJDEResponse {

    @XmlElement(required = true)
    protected Response inativarClienteJDEResult;

    
    public Response getInativarClienteJDEResult() {
        return inativarClienteJDEResult;
    }

    
    public void setInativarClienteJDEResult(Response value) {
        this.inativarClienteJDEResult = value;
    }

}
