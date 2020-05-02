
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gerarJDEResp", propOrder = {
    "codigoErro",
    "descricaoErro",
    "status",
    "msgErro"
})
public class GerarJDEResp
    extends EnsResponse
{

    protected String codigoErro;
    protected String descricaoErro;
    protected Boolean status;
    protected String msgErro;

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public String getDescricaoErro() {
        return descricaoErro;
    }

    
    public void setDescricaoErro(String value) {
        this.descricaoErro = value;
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
