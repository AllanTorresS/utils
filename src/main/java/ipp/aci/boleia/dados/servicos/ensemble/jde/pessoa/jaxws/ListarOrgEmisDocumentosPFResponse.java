
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listarOrgEmisDocumentosPFResult"
})
@XmlRootElement(name = "listarOrgEmisDocumentosPFResponse")
public class ListarOrgEmisDocumentosPFResponse {

    @XmlElement(required = true)
    protected ListarOrgEmisDocumentosPFResp listarOrgEmisDocumentosPFResult;

    
    public ListarOrgEmisDocumentosPFResp getListarOrgEmisDocumentosPFResult() {
        return listarOrgEmisDocumentosPFResult;
    }

    
    public void setListarOrgEmisDocumentosPFResult(ListarOrgEmisDocumentosPFResp value) {
        this.listarOrgEmisDocumentosPFResult = value;
    }

}
