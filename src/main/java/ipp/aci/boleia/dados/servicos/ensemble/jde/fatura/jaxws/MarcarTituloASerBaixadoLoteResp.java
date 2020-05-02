
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoLoteResp", propOrder = {
    "status",
    "msgErro",
    "listaLancamentoRetorno"
})
public class MarcarTituloASerBaixadoLoteResp
    extends EnsResponse
{

    protected Boolean status;
    protected String msgErro;
    protected ListaLancamentoRetorno listaLancamentoRetorno;

    
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

    
    public ListaLancamentoRetorno getListaLancamentoRetorno() {
        return listaLancamentoRetorno;
    }

    
    public void setListaLancamentoRetorno(ListaLancamentoRetorno value) {
        this.listaLancamentoRetorno = value;
    }

}
