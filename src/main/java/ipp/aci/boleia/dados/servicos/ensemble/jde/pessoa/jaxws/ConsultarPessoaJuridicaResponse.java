
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consultarPessoaJuridicaResult"
})
@XmlRootElement(name = "consultarPessoaJuridicaResponse")
public class ConsultarPessoaJuridicaResponse {

    @XmlElement(required = true)
    protected ConsultarPessoaJuridicaeResp consultarPessoaJuridicaResult;

    
    public ConsultarPessoaJuridicaeResp getConsultarPessoaJuridicaResult() {
        return consultarPessoaJuridicaResult;
    }

    
    public void setConsultarPessoaJuridicaResult(ConsultarPessoaJuridicaeResp value) {
        this.consultarPessoaJuridicaResult = value;
    }

}
