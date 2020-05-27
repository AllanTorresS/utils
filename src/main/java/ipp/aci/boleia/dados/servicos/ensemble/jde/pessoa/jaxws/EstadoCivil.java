
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoCivil", propOrder = {
    "codigoEstadoCivil",
    "descricaoEstadoCivil"
})
@XmlSeeAlso({
    PairOflistaEstadoCivilKeyestadoCivil.class
})
public class EstadoCivil {

    protected Long codigoEstadoCivil;
    protected String descricaoEstadoCivil;

    
    public Long getCodigoEstadoCivil() {
        return codigoEstadoCivil;
    }

    
    public void setCodigoEstadoCivil(Long value) {
        this.codigoEstadoCivil = value;
    }

    
    public String getDescricaoEstadoCivil() {
        return descricaoEstadoCivil;
    }

    
    public void setDescricaoEstadoCivil(String value) {
        this.descricaoEstadoCivil = value;
    }

}
