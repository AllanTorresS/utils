
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "listarOrgEmisPorDocumentoPF")
public class ListarOrgEmisPorDocumentoPF {

    protected ListarOrgEmisPorDocPFReq pRequest;

    
    public ListarOrgEmisPorDocPFReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(ListarOrgEmisPorDocPFReq value) {
        this.pRequest = value;
    }

}
