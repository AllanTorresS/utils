
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contato", propOrder = {
    "tipoCadastro",
    "nome",
    "filial",
    "idioma",
    "codigoClassificacaoIndustrial",
    "codigoCadastro",
    "codigoLongoCadastro",
    "cpfCnpj",
    "enderecoContato",
    "listaTelefoneContato",
    "listaEmailContato"
})
@XmlSeeAlso({
    PairOflistaContatoKeycontato.class
})
public class Contato {

    protected String tipoCadastro;
    protected String nome;
    protected String filial;
    protected String idioma;
    protected String codigoClassificacaoIndustrial;
    protected Long codigoCadastro;
    protected String codigoLongoCadastro;
    protected String cpfCnpj;
    protected EnderecoContato enderecoContato;
    protected ArrayOftelefoneContatoPairOflistaTelefoneContatoKeytelefoneContato listaTelefoneContato;
    protected ArrayOfemailContatoPairOflistaEmailContatoKeyemailContato listaEmailContato;

    
    public String getTipoCadastro() {
        return tipoCadastro;
    }

    
    public void setTipoCadastro(String value) {
        this.tipoCadastro = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getIdioma() {
        return idioma;
    }

    
    public void setIdioma(String value) {
        this.idioma = value;
    }

    
    public String getCodigoClassificacaoIndustrial() {
        return codigoClassificacaoIndustrial;
    }

    
    public void setCodigoClassificacaoIndustrial(String value) {
        this.codigoClassificacaoIndustrial = value;
    }

    
    public Long getCodigoCadastro() {
        return codigoCadastro;
    }

    
    public void setCodigoCadastro(Long value) {
        this.codigoCadastro = value;
    }

    
    public String getCodigoLongoCadastro() {
        return codigoLongoCadastro;
    }

    
    public void setCodigoLongoCadastro(String value) {
        this.codigoLongoCadastro = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

    
    public EnderecoContato getEnderecoContato() {
        return enderecoContato;
    }

    
    public void setEnderecoContato(EnderecoContato value) {
        this.enderecoContato = value;
    }

    
    public ArrayOftelefoneContatoPairOflistaTelefoneContatoKeytelefoneContato getListaTelefoneContato() {
        return listaTelefoneContato;
    }

    
    public void setListaTelefoneContato(ArrayOftelefoneContatoPairOflistaTelefoneContatoKeytelefoneContato value) {
        this.listaTelefoneContato = value;
    }

    
    public ArrayOfemailContatoPairOflistaEmailContatoKeyemailContato getListaEmailContato() {
        return listaEmailContato;
    }

    
    public void setListaEmailContato(ArrayOfemailContatoPairOflistaEmailContatoKeyemailContato value) {
        this.listaEmailContato = value;
    }

}
