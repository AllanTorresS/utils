
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parcela", propOrder = {
    "numeroParcela"
})
@XmlSeeAlso({
    PairOflistaParcelasKeyparcela.class
})
public class Parcela {

    @XmlElement(required = true)
    protected String numeroParcela;

    
    public String getNumeroParcela() {
        return numeroParcela;
    }

    
    public void setNumeroParcela(String value) {
        this.numeroParcela = value;
    }

}
