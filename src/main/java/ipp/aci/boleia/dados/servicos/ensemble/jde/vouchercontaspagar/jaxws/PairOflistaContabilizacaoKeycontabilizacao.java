
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaContabilizacaoKeycontabilizacao")
public class PairOflistaContabilizacaoKeycontabilizacao
    extends Contabilizacao
{

    @XmlAttribute(name = "listaContabilizacaoKey", required = true)
    protected String listaContabilizacaoKey;

    
    public String getListaContabilizacaoKey() {
        return listaContabilizacaoKey;
    }

    
    public void setListaContabilizacaoKey(String value) {
        this.listaContabilizacaoKey = value;
    }

}
