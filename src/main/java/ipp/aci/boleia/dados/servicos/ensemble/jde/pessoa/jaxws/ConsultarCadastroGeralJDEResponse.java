
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consultarCadastroGeralJDEResult"
})
@XmlRootElement(name = "consultarCadastroGeralJDEResponse")
public class ConsultarCadastroGeralJDEResponse {

    @XmlElement(required = true)
    protected ConsultarCadastroGeralJDEResp consultarCadastroGeralJDEResult;

    
    public ConsultarCadastroGeralJDEResp getConsultarCadastroGeralJDEResult() {
        return consultarCadastroGeralJDEResult;
    }

    
    public void setConsultarCadastroGeralJDEResult(ConsultarCadastroGeralJDEResp value) {
        this.consultarCadastroGeralJDEResult = value;
    }

}
