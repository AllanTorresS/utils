
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaDistribuicaoContabilKeydistribuicaoContabil")
public class PairOflistaDistribuicaoContabilKeydistribuicaoContabil
    extends DistribuicaoContabil
{

    @XmlAttribute(name = "listaDistribuicaoContabilKey", required = true)
    protected String listaDistribuicaoContabilKey;

    
    public String getListaDistribuicaoContabilKey() {
        return listaDistribuicaoContabilKey;
    }

    
    public void setListaDistribuicaoContabilKey(String value) {
        this.listaDistribuicaoContabilKey = value;
    }

}
