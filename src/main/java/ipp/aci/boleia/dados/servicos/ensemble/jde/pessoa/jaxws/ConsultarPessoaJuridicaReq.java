
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarPessoaJuridicaReq", propOrder = {
    "cnpjBasico",
    "razaoSocial",
    "uf",
    "listaCodigoPessoa"
})
public class ConsultarPessoaJuridicaReq
    extends EnsRequest
{

    protected String cnpjBasico;
    protected String razaoSocial;
    protected String uf;
    protected ArrayOflistaCodigoPessoaItemLong listaCodigoPessoa;

    
    public String getCnpjBasico() {
        return cnpjBasico;
    }

    
    public void setCnpjBasico(String value) {
        this.cnpjBasico = value;
    }

    
    public String getRazaoSocial() {
        return razaoSocial;
    }

    
    public void setRazaoSocial(String value) {
        this.razaoSocial = value;
    }

    
    public String getUf() {
        return uf;
    }

    
    public void setUf(String value) {
        this.uf = value;
    }

    
    public ArrayOflistaCodigoPessoaItemLong getListaCodigoPessoa() {
        return listaCodigoPessoa;
    }

    
    public void setListaCodigoPessoa(ArrayOflistaCodigoPessoaItemLong value) {
        this.listaCodigoPessoa = value;
    }

}
