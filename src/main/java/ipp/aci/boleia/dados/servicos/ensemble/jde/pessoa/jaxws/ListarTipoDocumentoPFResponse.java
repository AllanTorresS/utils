
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listarTipoDocumentoPFResult"
})
@XmlRootElement(name = "listarTipoDocumentoPFResponse")
public class ListarTipoDocumentoPFResponse {

    @XmlElement(required = true)
    protected ListarTipoDocumentoPFResp listarTipoDocumentoPFResult;

    
    public ListarTipoDocumentoPFResp getListarTipoDocumentoPFResult() {
        return listarTipoDocumentoPFResult;
    }

    
    public void setListarTipoDocumentoPFResult(ListarTipoDocumentoPFResp value) {
        this.listarTipoDocumentoPFResult = value;
    }

}
