
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "marcarTituloASerBaixadoLoteTituloParcelaReq", propOrder = {
    "lancamentoCR",
    "numeroParcela"
})
@XmlSeeAlso({
    PairOflistaParcelasKeymarcarTituloASerBaixadoLoteTituloParcelaReq.class
})
public class MarcarTituloASerBaixadoLoteTituloParcelaReq {

    protected long lancamentoCR;
    @XmlElement(required = true)
    protected String numeroParcela;

    
    public long getLancamentoCR() {
        return lancamentoCR;
    }

    
    public void setLancamentoCR(long value) {
        this.lancamentoCR = value;
    }

    
    public String getNumeroParcela() {
        return numeroParcela;
    }

    
    public void setNumeroParcela(String value) {
        this.numeroParcela = value;
    }

}
