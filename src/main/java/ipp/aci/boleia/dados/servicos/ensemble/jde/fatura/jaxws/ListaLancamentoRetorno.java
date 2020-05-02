
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfmarcarTituloASerBaixadoLoteLancamentoRespPairOflistaLancamentoRetornoKeymarcarTituloASerBaixadoLoteLancamentoResp", propOrder = {
    "marcarTituloASerBaixadoLoteLancamentoResp"
})
public class ListaLancamentoRetorno {

    @XmlElement(nillable = true)
    protected List<ParListasLancamentoRetorno> marcarTituloASerBaixadoLoteLancamentoResp;

    
    public List<ParListasLancamentoRetorno> getMarcarTituloASerBaixadoLoteLancamentoResp() {
        if (marcarTituloASerBaixadoLoteLancamentoResp == null) {
            marcarTituloASerBaixadoLoteLancamentoResp = new ArrayList<ParListasLancamentoRetorno>();
        }
        return this.marcarTituloASerBaixadoLoteLancamentoResp;
    }

}
