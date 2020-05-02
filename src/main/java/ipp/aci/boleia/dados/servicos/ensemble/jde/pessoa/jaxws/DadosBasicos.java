
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dadosBasicos", propOrder = {
    "razaoSocial",
    "cnpjBasico",
    "digitoVerificador",
    "nomeFantasia",
    "inscricaoEstadual"
})
public class DadosBasicos {

    protected String razaoSocial;
    protected String cnpjBasico;
    protected String digitoVerificador;
    protected String nomeFantasia;
    protected String inscricaoEstadual;

    
    public String getRazaoSocial() {
        return razaoSocial;
    }

    
    public void setRazaoSocial(String value) {
        this.razaoSocial = value;
    }

    
    public String getCnpjBasico() {
        return cnpjBasico;
    }

    
    public void setCnpjBasico(String value) {
        this.cnpjBasico = value;
    }

    
    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    
    public void setDigitoVerificador(String value) {
        this.digitoVerificador = value;
    }

    
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    
    public void setNomeFantasia(String value) {
        this.nomeFantasia = value;
    }

    
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    
    public void setInscricaoEstadual(String value) {
        this.inscricaoEstadual = value;
    }

}
