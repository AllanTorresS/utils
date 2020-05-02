
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gerarPDFResult"
})
@XmlRootElement(name = "gerarPDFResponse")
public class GerarPDFResponse {

    @XmlElement(required = true)
    protected GerarPDFResp gerarPDFResult;

    
    public GerarPDFResp getGerarPDFResult() {
        return gerarPDFResult;
    }

    
    public void setGerarPDFResult(GerarPDFResp value) {
        this.gerarPDFResult = value;
    }

}
