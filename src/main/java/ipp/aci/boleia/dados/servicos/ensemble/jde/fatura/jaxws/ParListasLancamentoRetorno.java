
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaLancamentoRetornoKeymarcarTituloASerBaixadoLoteLancamentoResp")
public class ParListasLancamentoRetorno
    extends MarcarTituloASerBaixadoLoteLancamentoResp
{

    @XmlAttribute(name = "listaLancamentoRetornoKey", required = true)
    protected String listaLancamentoRetornoKey;

    
    public String getListaLancamentoRetornoKey() {
        return listaLancamentoRetornoKey;
    }

    
    public void setListaLancamentoRetornoKey(String value) {
        this.listaLancamentoRetornoKey = value;
    }

}
