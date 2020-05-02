
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pessoaJuridica", propOrder = {
    "codigoPessoa",
    "dadosBasicos",
    "endereco",
    "contato"
})
public class PessoaJuridica {

    protected Long codigoPessoa;
    protected DadosBasicos dadosBasicos;
    protected EnderecoPessoaJuridica endereco;
    protected ContatoPessoaJuridica contato;

    
    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    
    public void setCodigoPessoa(Long value) {
        this.codigoPessoa = value;
    }

    
    public DadosBasicos getDadosBasicos() {
        return dadosBasicos;
    }

    
    public void setDadosBasicos(DadosBasicos value) {
        this.dadosBasicos = value;
    }

    
    public EnderecoPessoaJuridica getEndereco() {
        return endereco;
    }

    
    public void setEndereco(EnderecoPessoaJuridica value) {
        this.endereco = value;
    }

    
    public ContatoPessoaJuridica getContato() {
        return contato;
    }

    
    public void setContato(ContatoPessoaJuridica value) {
        this.contato = value;
    }

}
