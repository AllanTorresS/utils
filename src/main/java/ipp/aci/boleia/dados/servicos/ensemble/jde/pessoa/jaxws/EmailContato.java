
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emailContato", propOrder = {
    "tipoEmail",
    "email",
    "numeroLinha"
})
@XmlSeeAlso({
    PairOflistaEmailContatoKeyemailContato.class
})
public class EmailContato {

    protected String tipoEmail;
    protected String email;
    protected Long numeroLinha;

    
    public String getTipoEmail() {
        return tipoEmail;
    }

    
    public void setTipoEmail(String value) {
        this.tipoEmail = value;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String value) {
        this.email = value;
    }

    
    public Long getNumeroLinha() {
        return numeroLinha;
    }

    
    public void setNumeroLinha(Long value) {
        this.numeroLinha = value;
    }

}
