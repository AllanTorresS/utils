
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "gerarPDF")
public class GerarPDF {

    protected GerarPDFReq pRequest;

    
    public GerarPDFReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(GerarPDFReq value) {
        this.pRequest = value;
    }

}
