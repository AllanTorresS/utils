
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "marcarTituloASerBaixadoLoteResult"
})
@XmlRootElement(name = "marcarTituloASerBaixadoLoteResponse")
public class MarcarTituloASerBaixadoLoteResponse {

    @XmlElement(required = true)
    protected MarcarTituloASerBaixadoLoteResp marcarTituloASerBaixadoLoteResult;

    
    public MarcarTituloASerBaixadoLoteResp getMarcarTituloASerBaixadoLoteResult() {
        return marcarTituloASerBaixadoLoteResult;
    }

    
    public void setMarcarTituloASerBaixadoLoteResult(MarcarTituloASerBaixadoLoteResp value) {
        this.marcarTituloASerBaixadoLoteResult = value;
    }

}
