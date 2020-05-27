
package ipp.aci.boleia.dados.servicos.ensemble.jde.pessoa.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enderecoContato", propOrder = {
    "nomeCadastro",
    "endereco1",
    "endereco2",
    "endereco3",
    "endereco4",
    "cidade",
    "municipio",
    "siglaEstado",
    "cep",
    "siglaPais",
    "dataCadastro"
})
public class EnderecoContato {

    protected String nomeCadastro;
    protected String endereco1;
    protected String endereco2;
    protected String endereco3;
    protected String endereco4;
    protected String cidade;
    protected String municipio;
    protected String siglaEstado;
    protected String cep;
    protected String siglaPais;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCadastro;

    
    public String getNomeCadastro() {
        return nomeCadastro;
    }

    
    public void setNomeCadastro(String value) {
        this.nomeCadastro = value;
    }

    
    public String getEndereco1() {
        return endereco1;
    }

    
    public void setEndereco1(String value) {
        this.endereco1 = value;
    }

    
    public String getEndereco2() {
        return endereco2;
    }

    
    public void setEndereco2(String value) {
        this.endereco2 = value;
    }

    
    public String getEndereco3() {
        return endereco3;
    }

    
    public void setEndereco3(String value) {
        this.endereco3 = value;
    }

    
    public String getEndereco4() {
        return endereco4;
    }

    
    public void setEndereco4(String value) {
        this.endereco4 = value;
    }

    
    public String getCidade() {
        return cidade;
    }

    
    public void setCidade(String value) {
        this.cidade = value;
    }

    
    public String getMunicipio() {
        return municipio;
    }

    
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    
    public String getSiglaEstado() {
        return siglaEstado;
    }

    
    public void setSiglaEstado(String value) {
        this.siglaEstado = value;
    }

    
    public String getCep() {
        return cep;
    }

    
    public void setCep(String value) {
        this.cep = value;
    }

    
    public String getSiglaPais() {
        return siglaPais;
    }

    
    public void setSiglaPais(String value) {
        this.siglaPais = value;
    }

    
    public XMLGregorianCalendar getDataCadastro() {
        return dataCadastro;
    }

    
    public void setDataCadastro(XMLGregorianCalendar value) {
        this.dataCadastro = value;
    }

}
