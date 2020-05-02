
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enderecoPessoaJuridica", propOrder = {
    "endereco",
    "bairro",
    "cep",
    "codigoMunicipio",
    "uf"
})
public class EnderecoPessoaJuridica {

    protected String endereco;
    protected String bairro;
    protected String cep;
    protected Long codigoMunicipio;
    protected String uf;

    
    public String getEndereco() {
        return endereco;
    }

    
    public void setEndereco(String value) {
        this.endereco = value;
    }

    
    public String getBairro() {
        return bairro;
    }

    
    public void setBairro(String value) {
        this.bairro = value;
    }

    
    public String getCep() {
        return cep;
    }

    
    public void setCep(String value) {
        this.cep = value;
    }

    
    public Long getCodigoMunicipio() {
        return codigoMunicipio;
    }

    
    public void setCodigoMunicipio(Long value) {
        this.codigoMunicipio = value;
    }

    
    public String getUf() {
        return uf;
    }

    
    public void setUf(String value) {
        this.uf = value;
    }

}
