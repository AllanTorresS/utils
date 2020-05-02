
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarTipoDocumentoPFResp", propOrder = {
    "listaTipoDocumentoPF",
    "status",
    "msgErro"
})
public class ListarTipoDocumentoPFResp
    extends EnsResponse
{

    protected ArrayOftipoDocumentoPairOflistaTipoDocumentoPFKeytipoDocumento listaTipoDocumentoPF;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOftipoDocumentoPairOflistaTipoDocumentoPFKeytipoDocumento getListaTipoDocumentoPF() {
        return listaTipoDocumentoPF;
    }

    
    public void setListaTipoDocumentoPF(ArrayOftipoDocumentoPairOflistaTipoDocumentoPFKeytipoDocumento value) {
        this.listaTipoDocumentoPF = value;
    }

    
    public Boolean isStatus() {
        return status;
    }

    
    public void setStatus(Boolean value) {
        this.status = value;
    }

    
    public String getMsgErro() {
        return msgErro;
    }

    
    public void setMsgErro(String value) {
        this.msgErro = value;
    }

}
