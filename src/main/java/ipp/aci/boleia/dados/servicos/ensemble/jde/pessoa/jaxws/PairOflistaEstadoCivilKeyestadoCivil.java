
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOflistaEstadoCivilKeyestadoCivil")
public class PairOflistaEstadoCivilKeyestadoCivil
    extends EstadoCivil
{

    @XmlAttribute(name = "listaEstadoCivilKey", required = true)
    protected String listaEstadoCivilKey;

    
    public String getListaEstadoCivilKey() {
        return listaEstadoCivilKey;
    }

    
    public void setListaEstadoCivilKey(String value) {
        this.listaEstadoCivilKey = value;
    }

}
