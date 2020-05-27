
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contatoPessoaJuridica", propOrder = {
    "nomeContato",
    "dddTelefone",
    "telefone",
    "dddFax",
    "fax",
    "email"
})
public class ContatoPessoaJuridica {

    protected String nomeContato;
    protected Long dddTelefone;
    protected Long telefone;
    protected Long dddFax;
    protected Long fax;
    protected String email;

    
    public String getNomeContato() {
        return nomeContato;
    }

    
    public void setNomeContato(String value) {
        this.nomeContato = value;
    }

    
    public Long getDddTelefone() {
        return dddTelefone;
    }

    
    public void setDddTelefone(Long value) {
        this.dddTelefone = value;
    }

    
    public Long getTelefone() {
        return telefone;
    }

    
    public void setTelefone(Long value) {
        this.telefone = value;
    }

    
    public Long getDddFax() {
        return dddFax;
    }

    
    public void setDddFax(Long value) {
        this.dddFax = value;
    }

    
    public Long getFax() {
        return fax;
    }

    
    public void setFax(Long value) {
        this.fax = value;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String value) {
        this.email = value;
    }

}
