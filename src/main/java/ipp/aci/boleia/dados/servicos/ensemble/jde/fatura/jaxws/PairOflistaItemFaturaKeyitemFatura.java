
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaItemFaturaKeyitemFatura")
public class PairOflistaItemFaturaKeyitemFatura
    extends ItemFatura
{

    @XmlAttribute(name = "listaItemFaturaKey", required = true)
    protected String listaItemFaturaKey;

    
    public String getListaItemFaturaKey() {
        return listaItemFaturaKey;
    }

    
    public void setListaItemFaturaKey(String value) {
        this.listaItemFaturaKey = value;
    }

}
