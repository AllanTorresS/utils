
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaEmailContatoKeyemailContato")
public class PairOflistaEmailContatoKeyemailContato
    extends EmailContato
{

    @XmlAttribute(name = "listaEmailContatoKey", required = true)
    protected String listaEmailContatoKey;

    
    public String getListaEmailContatoKey() {
        return listaEmailContatoKey;
    }

    
    public void setListaEmailContatoKey(String value) {
        this.listaEmailContatoKey = value;
    }

}
