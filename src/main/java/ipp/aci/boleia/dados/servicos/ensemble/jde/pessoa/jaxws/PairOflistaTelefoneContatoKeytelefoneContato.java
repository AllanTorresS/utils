
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaTelefoneContatoKeytelefoneContato")
public class PairOflistaTelefoneContatoKeytelefoneContato
    extends TelefoneContato
{

    @XmlAttribute(name = "listaTelefoneContatoKey", required = true)
    protected String listaTelefoneContatoKey;

    
    public String getListaTelefoneContatoKey() {
        return listaTelefoneContatoKey;
    }

    
    public void setListaTelefoneContatoKey(String value) {
        this.listaTelefoneContatoKey = value;
    }

}
