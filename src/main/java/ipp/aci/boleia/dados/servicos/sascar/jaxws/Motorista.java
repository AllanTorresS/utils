
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "motorista", propOrder = {
    "celular",
    "dataContratacao",
    "generico",
    "idMotorista",
    "login",
    "nome",
    "numeroDocumento",
    "senha",
    "telefone",
    "tipoCNH",
    "tipoDocumento",
    "tipoMotorista",
    "vencimentoCNH"
})
public class Motorista {

    protected String celular;
    @XmlSchemaType(name = "anySimpleType")
    protected Object dataContratacao;
    protected Boolean generico;
    protected Integer idMotorista;
    protected String login;
    protected String nome;
    protected String numeroDocumento;
    protected String senha;
    protected String telefone;
    protected String tipoCNH;
    protected String tipoDocumento;
    protected String tipoMotorista;
    @XmlSchemaType(name = "anySimpleType")
    protected Object vencimentoCNH;

    
    public String getCelular() {
        return celular;
    }

    
    public void setCelular(String value) {
        this.celular = value;
    }

    
    public Object getDataContratacao() {
        return dataContratacao;
    }

    
    public void setDataContratacao(Object value) {
        this.dataContratacao = value;
    }

    
    public Boolean isGenerico() {
        return generico;
    }

    
    public void setGenerico(Boolean value) {
        this.generico = value;
    }

    
    public Integer getIdMotorista() {
        return idMotorista;
    }

    
    public void setIdMotorista(Integer value) {
        this.idMotorista = value;
    }

    
    public String getLogin() {
        return login;
    }

    
    public void setLogin(String value) {
        this.login = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    
    public String getSenha() {
        return senha;
    }

    
    public void setSenha(String value) {
        this.senha = value;
    }

    
    public String getTelefone() {
        return telefone;
    }

    
    public void setTelefone(String value) {
        this.telefone = value;
    }

    
    public String getTipoCNH() {
        return tipoCNH;
    }

    
    public void setTipoCNH(String value) {
        this.tipoCNH = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getTipoMotorista() {
        return tipoMotorista;
    }

    
    public void setTipoMotorista(String value) {
        this.tipoMotorista = value;
    }

    
    public Object getVencimentoCNH() {
        return vencimentoCNH;
    }

    
    public void setVencimentoCNH(Object value) {
        this.vencimentoCNH = value;
    }

}
