
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listarOrgEmisDocumentosPFResp", propOrder = {
    "listaOrgaoEmissor",
    "status",
    "msgErro"
})
public class ListarOrgEmisDocumentosPFResp
    extends EnsResponse
{

    protected ArrayOforgaoEmissorPairOflistaOrgaoEmissorKeyorgaoEmissor listaOrgaoEmissor;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOforgaoEmissorPairOflistaOrgaoEmissorKeyorgaoEmissor getListaOrgaoEmissor() {
        return listaOrgaoEmissor;
    }

    
    public void setListaOrgaoEmissor(ArrayOforgaoEmissorPairOflistaOrgaoEmissorKeyorgaoEmissor value) {
        this.listaOrgaoEmissor = value;
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
