
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "buscarResult"
})
@XmlRootElement(name = "buscarResponse")
public class BuscarResponse {

    @XmlElement(required = true)
    protected BuscarResp buscarResult;

    
    public BuscarResp getBuscarResult() {
        return buscarResult;
    }

    
    public void setBuscarResult(BuscarResp value) {
        this.buscarResult = value;
    }

}
