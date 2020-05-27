
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaContatoKeycontato")
public class PairOflistaContatoKeycontato
    extends Contato
{

    @XmlAttribute(name = "listaContatoKey", required = true)
    protected String listaContatoKey;

    
    public String getListaContatoKey() {
        return listaContatoKey;
    }

    
    public void setListaContatoKey(String value) {
        this.listaContatoKey = value;
    }

}
