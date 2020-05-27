
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criarEmLoteResp", propOrder = {
    "listaVoucherRetorno",
    "status",
    "msgErro"
})
public class CriarEmLoteResp
    extends EnsResponse
{

    protected ArrayOfcriarEmLoteVoucherRetornoRespPairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp listaVoucherRetorno;
    protected Boolean status;
    protected String msgErro;

    
    public ArrayOfcriarEmLoteVoucherRetornoRespPairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp getListaVoucherRetorno() {
        return listaVoucherRetorno;
    }

    
    public void setListaVoucherRetorno(ArrayOfcriarEmLoteVoucherRetornoRespPairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp value) {
        this.listaVoucherRetorno = value;
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
