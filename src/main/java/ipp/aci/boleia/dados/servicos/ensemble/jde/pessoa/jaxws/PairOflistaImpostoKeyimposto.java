
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaImpostoKeyimposto")
public class PairOflistaImpostoKeyimposto
    extends Imposto
{

    @XmlAttribute(name = "listaImpostoKey", required = true)
    protected String listaImpostoKey;

    
    public String getListaImpostoKey() {
        return listaImpostoKey;
    }

    
    public void setListaImpostoKey(String value) {
        this.listaImpostoKey = value;
    }

}
