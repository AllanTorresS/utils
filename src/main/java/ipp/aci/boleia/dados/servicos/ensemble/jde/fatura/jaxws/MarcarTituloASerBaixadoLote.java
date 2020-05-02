
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pRequest"
})
@XmlRootElement(name = "marcarTituloASerBaixadoLote")
public class MarcarTituloASerBaixadoLote {

    protected MarcarTituloASerBaixadoLoteReq pRequest;

    
    public MarcarTituloASerBaixadoLoteReq getPRequest() {
        return pRequest;
    }

    
    public void setPRequest(MarcarTituloASerBaixadoLoteReq value) {
        this.pRequest = value;
    }

}
