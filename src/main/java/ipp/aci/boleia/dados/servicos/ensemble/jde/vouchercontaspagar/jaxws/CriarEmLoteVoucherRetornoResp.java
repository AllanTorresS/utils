
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criarEmLoteVoucherRetornoResp", propOrder = {
    "numeroVoucher",
    "tipoDocVoucher",
    "status",
    "msgErro"
})
@XmlSeeAlso({
    PairOflistaVoucherRetornoKeycriarEmLoteVoucherRetornoResp.class
})
public class CriarEmLoteVoucherRetornoResp {

    protected Long numeroVoucher;
    protected String tipoDocVoucher;
    protected Boolean status;
    protected String msgErro;

    
    public Long getNumeroVoucher() {
        return numeroVoucher;
    }

    
    public void setNumeroVoucher(Long value) {
        this.numeroVoucher = value;
    }

    
    public String getTipoDocVoucher() {
        return tipoDocVoucher;
    }

    
    public void setTipoDocVoucher(String value) {
        this.tipoDocVoucher = value;
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
