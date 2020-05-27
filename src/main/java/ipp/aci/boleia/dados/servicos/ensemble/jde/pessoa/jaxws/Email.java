
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "email", propOrder = {
    "tipoEmail",
    "eMail"
})
@XmlSeeAlso({
    PairOflistaEmailKeyemail.class
})
public class Email {

    protected String tipoEmail;
    protected String eMail;

    
    public String getTipoEmail() {
        return tipoEmail;
    }

    
    public void setTipoEmail(String value) {
        this.tipoEmail = value;
    }

    
    public String getEMail() {
        return eMail;
    }

    
    public void setEMail(String value) {
        this.eMail = value;
    }

}
