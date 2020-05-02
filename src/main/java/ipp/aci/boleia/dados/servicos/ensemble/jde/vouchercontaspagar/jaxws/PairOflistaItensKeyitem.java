
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaItensKeyitem")
public class PairOflistaItensKeyitem
    extends Item
{

    @XmlAttribute(name = "listaItensKey", required = true)
    protected String listaItensKey;

    
    public String getListaItensKey() {
        return listaItensKey;
    }

    
    public void setListaItensKey(String value) {
        this.listaItensKey = value;
    }

}
