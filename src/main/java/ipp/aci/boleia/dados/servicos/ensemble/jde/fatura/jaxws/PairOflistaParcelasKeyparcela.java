
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaParcelasKeyparcela")
public class PairOflistaParcelasKeyparcela
    extends Parcela
{

    @XmlAttribute(name = "listaParcelasKey", required = true)
    protected String listaParcelasKey;

    
    public String getListaParcelasKey() {
        return listaParcelasKey;
    }

    
    public void setListaParcelasKey(String value) {
        this.listaParcelasKey = value;
    }

}
