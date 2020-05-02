
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOfboletosKeyboleto")
public class PairOfboletosKeyboleto
    extends Boleto
{

    @XmlAttribute(name = "boletosKey", required = true)
    protected String boletosKey;

    
    public String getBoletosKey() {
        return boletosKey;
    }

    
    public void setBoletosKey(String value) {
        this.boletosKey = value;
    }

}
