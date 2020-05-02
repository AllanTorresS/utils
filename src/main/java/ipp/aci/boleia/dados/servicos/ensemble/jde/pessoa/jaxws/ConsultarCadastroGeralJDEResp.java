
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarCadastroGeralJDEResp", propOrder = {
    "listaContato",
    "status",
    "msgErro"
})
public class ConsultarCadastroGeralJDEResp
    extends EnsResponse
{

    protected ArrayOfcontatoPairOflistaContatoKeycontato listaContato;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOfcontatoPairOflistaContatoKeycontato getListaContato() {
        return listaContato;
    }

    
    public void setListaContato(ArrayOfcontatoPairOflistaContatoKeycontato value) {
        this.listaContato = value;
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
