
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarResp", propOrder = {
    "status",
    "msgErro",
    "listaVoucherRetorno"
})
public class ConsultarResp
    extends EnsResponse
{

    protected Boolean status;
    protected String msgErro;
    protected ArrayOfconsultarVoucherRetornoRespPairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp listaVoucherRetorno;

    
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

    
    public ArrayOfconsultarVoucherRetornoRespPairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp getListaVoucherRetorno() {
        return listaVoucherRetorno;
    }

    
    public void setListaVoucherRetorno(ArrayOfconsultarVoucherRetornoRespPairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp value) {
        this.listaVoucherRetorno = value;
    }

}
