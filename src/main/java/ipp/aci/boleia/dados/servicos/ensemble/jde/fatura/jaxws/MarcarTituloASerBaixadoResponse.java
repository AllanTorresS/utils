
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "marcarTituloASerBaixadoResult"
})
@XmlRootElement(name = "marcarTituloASerBaixadoResponse")
public class MarcarTituloASerBaixadoResponse {

    @XmlElement(required = true)
    protected MarcarTituloASerBaixadoResp marcarTituloASerBaixadoResult;

    
    public MarcarTituloASerBaixadoResp getMarcarTituloASerBaixadoResult() {
        return marcarTituloASerBaixadoResult;
    }

    
    public void setMarcarTituloASerBaixadoResult(MarcarTituloASerBaixadoResp value) {
        this.marcarTituloASerBaixadoResult = value;
    }

}
