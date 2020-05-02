
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listarOrgEmisPorDocumentoPFResult"
})
@XmlRootElement(name = "listarOrgEmisPorDocumentoPFResponse")
public class ListarOrgEmisPorDocumentoPFResponse {

    @XmlElement(required = true)
    protected ListarOrgEmisPorDocPFResp listarOrgEmisPorDocumentoPFResult;

    
    public ListarOrgEmisPorDocPFResp getListarOrgEmisPorDocumentoPFResult() {
        return listarOrgEmisPorDocumentoPFResult;
    }

    
    public void setListarOrgEmisPorDocumentoPFResult(ListarOrgEmisPorDocPFResp value) {
        this.listarOrgEmisPorDocumentoPFResult = value;
    }

}
