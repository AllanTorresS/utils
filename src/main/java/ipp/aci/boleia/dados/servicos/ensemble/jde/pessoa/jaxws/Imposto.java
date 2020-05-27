
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "imposto", propOrder = {
    "codigoImposto"
})
@XmlSeeAlso({
    PairOflistaImpostoKeyimposto.class
})
public class Imposto {

    @XmlElement(required = true)
    protected String codigoImposto;

    
    public String getCodigoImposto() {
        return codigoImposto;
    }

    
    public void setCodigoImposto(String value) {
        this.codigoImposto = value;
    }

}
