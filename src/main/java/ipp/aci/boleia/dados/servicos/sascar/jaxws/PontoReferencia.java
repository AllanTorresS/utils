
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pontoReferencia", propOrder = {
    "codigo",
    "data",
    "descricao",
    "endereco",
    "idPontoReferencia",
    "latitudeI",
    "latitudeS",
    "longitudeI",
    "longitudeS",
    "nome"
})
public class PontoReferencia {

    protected Integer codigo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected String descricao;
    protected String endereco;
    protected Integer idPontoReferencia;
    protected Double latitudeI;
    protected Double latitudeS;
    protected Double longitudeI;
    protected Double longitudeS;
    protected String nome;

    
    public Integer getCodigo() {
        return codigo;
    }

    
    public void setCodigo(Integer value) {
        this.codigo = value;
    }

    
    public XMLGregorianCalendar getData() {
        return data;
    }

    
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public String getEndereco() {
        return endereco;
    }

    
    public void setEndereco(String value) {
        this.endereco = value;
    }

    
    public Integer getIdPontoReferencia() {
        return idPontoReferencia;
    }

    
    public void setIdPontoReferencia(Integer value) {
        this.idPontoReferencia = value;
    }

    
    public Double getLatitudeI() {
        return latitudeI;
    }

    
    public void setLatitudeI(Double value) {
        this.latitudeI = value;
    }

    
    public Double getLatitudeS() {
        return latitudeS;
    }

    
    public void setLatitudeS(Double value) {
        this.latitudeS = value;
    }

    
    public Double getLongitudeI() {
        return longitudeI;
    }

    
    public void setLongitudeI(Double value) {
        this.longitudeI = value;
    }

    
    public Double getLongitudeS() {
        return longitudeS;
    }

    
    public void setLongitudeS(Double value) {
        this.longitudeS = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
