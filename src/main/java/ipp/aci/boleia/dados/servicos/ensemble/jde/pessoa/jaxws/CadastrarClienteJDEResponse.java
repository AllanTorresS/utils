
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cadastrarClienteJDEResult"
})
@XmlRootElement(name = "cadastrarClienteJDEResponse")
public class CadastrarClienteJDEResponse {

    @XmlElement(required = true)
    protected CadastrarJDEResp cadastrarClienteJDEResult;

    
    public CadastrarJDEResp getCadastrarClienteJDEResult() {
        return cadastrarClienteJDEResult;
    }

    
    public void setCadastrarClienteJDEResult(CadastrarJDEResp value) {
        this.cadastrarClienteJDEResult = value;
    }

}
