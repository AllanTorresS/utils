
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfmarcarTituloASerBaixadoLoteTituloParcelaReqPairOflistaParcelasKeymarcarTituloASerBaixadoLoteTituloParcelaReq", propOrder = {
    "marcarTituloASerBaixadoLoteTituloParcelaReq"
})
public class ListaParcelas {

    @XmlElement(nillable = true)
    protected List<PairOflistaParcelasKeymarcarTituloASerBaixadoLoteTituloParcelaReq> marcarTituloASerBaixadoLoteTituloParcelaReq;

    
    public List<PairOflistaParcelasKeymarcarTituloASerBaixadoLoteTituloParcelaReq> getMarcarTituloASerBaixadoLoteTituloParcelaReq() {
        if (marcarTituloASerBaixadoLoteTituloParcelaReq == null) {
            marcarTituloASerBaixadoLoteTituloParcelaReq = new ArrayList<PairOflistaParcelasKeymarcarTituloASerBaixadoLoteTituloParcelaReq>();
        }
        return this.marcarTituloASerBaixadoLoteTituloParcelaReq;
    }

}
