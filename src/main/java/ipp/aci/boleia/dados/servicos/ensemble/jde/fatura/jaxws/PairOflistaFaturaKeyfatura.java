
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaFaturaKeyfatura")
public class PairOflistaFaturaKeyfatura
    extends Fatura
{

    @XmlAttribute(name = "listaFaturaKey", required = true)
    protected String listaFaturaKey;

    
    public String getListaFaturaKey() {
        return listaFaturaKey;
    }

    
    public void setListaFaturaKey(String value) {
        this.listaFaturaKey = value;
    }

}
