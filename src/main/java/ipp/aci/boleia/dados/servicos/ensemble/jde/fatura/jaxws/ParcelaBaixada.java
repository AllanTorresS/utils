
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parcelaBaixada", propOrder = {
    "status",
    "msgErro",
    "erroJDE",
    "msgErroJDE"
})
@XmlSeeAlso({
    PairOflistaParcelasBaixadasKeyparcelaBaixada.class
})
public class ParcelaBaixada {

    protected Boolean status;
    protected String msgErro;
    protected String erroJDE;
    protected String msgErroJDE;

    
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

    
    public String getErroJDE() {
        return erroJDE;
    }

    
    public void setErroJDE(String value) {
        this.erroJDE = value;
    }

    
    public String getMsgErroJDE() {
        return msgErroJDE;
    }

    
    public void setMsgErroJDE(String value) {
        this.msgErroJDE = value;
    }

}
