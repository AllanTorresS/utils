
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaEmailKeyemail")
public class PairOflistaEmailKeyemail
    extends Email
{

    @XmlAttribute(name = "listaEmailKey", required = true)
    protected String listaEmailKey;

    
    public String getListaEmailKey() {
        return listaEmailKey;
    }

    
    public void setListaEmailKey(String value) {
        this.listaEmailKey = value;
    }

}
