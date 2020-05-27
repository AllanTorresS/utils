
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orgaoEmissor", propOrder = {
    "codigoOrgaoEmissor",
    "nomeOrgaoEmissor",
    "siglaOrgaoEmissor"
})
@XmlSeeAlso({
    PairOflistaOrgaoEmissorKeyorgaoEmissor.class
})
public class OrgaoEmissor {

    protected Long codigoOrgaoEmissor;
    protected String nomeOrgaoEmissor;
    protected String siglaOrgaoEmissor;

    
    public Long getCodigoOrgaoEmissor() {
        return codigoOrgaoEmissor;
    }

    
    public void setCodigoOrgaoEmissor(Long value) {
        this.codigoOrgaoEmissor = value;
    }

    
    public String getNomeOrgaoEmissor() {
        return nomeOrgaoEmissor;
    }

    
    public void setNomeOrgaoEmissor(String value) {
        this.nomeOrgaoEmissor = value;
    }

    
    public String getSiglaOrgaoEmissor() {
        return siglaOrgaoEmissor;
    }

    
    public void setSiglaOrgaoEmissor(String value) {
        this.siglaOrgaoEmissor = value;
    }

}
