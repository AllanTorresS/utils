
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "consultarJDEResult"
})
@XmlRootElement(name = "consultarJDEResponse")
public class ConsultarJDEResponse {

    @XmlElement(required = true)
    protected ConsultarJDEResp consultarJDEResult;

    
    public ConsultarJDEResp getConsultarJDEResult() {
        return consultarJDEResult;
    }

    
    public void setConsultarJDEResult(ConsultarJDEResp value) {
        this.consultarJDEResult = value;
    }

}
