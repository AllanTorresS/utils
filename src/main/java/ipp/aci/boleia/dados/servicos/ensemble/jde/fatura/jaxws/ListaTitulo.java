
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfmarcarTituloASerBaixadoLoteTituloReqPairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq", propOrder = {
    "marcarTituloASerBaixadoLoteTituloReq"
})
public class ListaTitulo {

    @XmlElement(nillable = true)
    protected List<PairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq> marcarTituloASerBaixadoLoteTituloReq;

    
    public List<PairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq> getMarcarTituloASerBaixadoLoteTituloReq() {
        if (marcarTituloASerBaixadoLoteTituloReq == null) {
            marcarTituloASerBaixadoLoteTituloReq = new ArrayList<PairOflistaTituloKeymarcarTituloASerBaixadoLoteTituloReq>();
        }
        return this.marcarTituloASerBaixadoLoteTituloReq;
    }

}
