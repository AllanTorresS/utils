
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaTelefoneKeytelefone")
public class PairOflistaTelefoneKeytelefone
    extends Telefone
{

    @XmlAttribute(name = "listaTelefoneKey", required = true)
    protected String listaTelefoneKey;

    
    public String getListaTelefoneKey() {
        return listaTelefoneKey;
    }

    
    public void setListaTelefoneKey(String value) {
        this.listaTelefoneKey = value;
    }

}
