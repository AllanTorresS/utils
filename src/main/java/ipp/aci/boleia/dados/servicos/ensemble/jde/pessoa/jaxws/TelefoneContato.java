
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "telefoneContato", propOrder = {
    "codigoCadastro",
    "linhaCadastro",
    "ddd",
    "tipoTelefone",
    "numeroTelefone"
})
@XmlSeeAlso({
    PairOflistaTelefoneContatoKeytelefoneContato.class
})
public class TelefoneContato {

    protected Long codigoCadastro;
    protected String linhaCadastro;
    protected String ddd;
    protected String tipoTelefone;
    protected String numeroTelefone;

    
    public Long getCodigoCadastro() {
        return codigoCadastro;
    }

    
    public void setCodigoCadastro(Long value) {
        this.codigoCadastro = value;
    }

    
    public String getLinhaCadastro() {
        return linhaCadastro;
    }

    
    public void setLinhaCadastro(String value) {
        this.linhaCadastro = value;
    }

    
    public String getDdd() {
        return ddd;
    }

    
    public void setDdd(String value) {
        this.ddd = value;
    }

    
    public String getTipoTelefone() {
        return tipoTelefone;
    }

    
    public void setTipoTelefone(String value) {
        this.tipoTelefone = value;
    }

    
    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    
    public void setNumeroTelefone(String value) {
        this.numeroTelefone = value;
    }

}
