
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inserirEmLoteJDEResp", propOrder = {
    "listaFaturaResp",
    "status",
    "msgErro"
})
public class InserirEmLoteJDEResp
    extends EnsResponse
{

    protected ArrayOffaturaRespPairOflistaFaturaRespKeyfaturaResp listaFaturaResp;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOffaturaRespPairOflistaFaturaRespKeyfaturaResp getListaFaturaResp() {
        return listaFaturaResp;
    }

    
    public void setListaFaturaResp(ArrayOffaturaRespPairOflistaFaturaRespKeyfaturaResp value) {
        this.listaFaturaResp = value;
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
