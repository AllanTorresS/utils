
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fatura", propOrder = {
    "filialFabrica",
    "cliente",
    "tipoDocumento",
    "condicaoPgto",
    "dataFatura",
    "subConta",
    "ciaDocumentoOriginal",
    "tipoDocumentoOriginal",
    "numDocumentoOriginal",
    "observacao",
    "sistemaGeradorFatura",
    "usuarioGeradorFatura",
    "explicacaoContestacao",
    "listaItemFatura"
})
@XmlSeeAlso({
    PairOflistaFaturaKeyfatura.class
})
public class Fatura {

    @XmlElement(required = true)
    protected String filialFabrica;
    @XmlElement(required = true)
    protected BigDecimal cliente;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected String condicaoPgto;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFatura;
    protected String subConta;
    protected String ciaDocumentoOriginal;
    protected String tipoDocumentoOriginal;
    protected BigDecimal numDocumentoOriginal;
    protected String observacao;
    protected String sistemaGeradorFatura;
    protected String usuarioGeradorFatura;
    protected String explicacaoContestacao;
    protected ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura listaItemFatura;

    
    public String getFilialFabrica() {
        return filialFabrica;
    }

    
    public void setFilialFabrica(String value) {
        this.filialFabrica = value;
    }

    
    public BigDecimal getCliente() {
        return cliente;
    }

    
    public void setCliente(BigDecimal value) {
        this.cliente = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCondicaoPgto() {
        return condicaoPgto;
    }

    
    public void setCondicaoPgto(String value) {
        this.condicaoPgto = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public String getSubConta() {
        return subConta;
    }

    
    public void setSubConta(String value) {
        this.subConta = value;
    }

    
    public String getCiaDocumentoOriginal() {
        return ciaDocumentoOriginal;
    }

    
    public void setCiaDocumentoOriginal(String value) {
        this.ciaDocumentoOriginal = value;
    }

    
    public String getTipoDocumentoOriginal() {
        return tipoDocumentoOriginal;
    }

    
    public void setTipoDocumentoOriginal(String value) {
        this.tipoDocumentoOriginal = value;
    }

    
    public BigDecimal getNumDocumentoOriginal() {
        return numDocumentoOriginal;
    }

    
    public void setNumDocumentoOriginal(BigDecimal value) {
        this.numDocumentoOriginal = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public String getSistemaGeradorFatura() {
        return sistemaGeradorFatura;
    }

    
    public void setSistemaGeradorFatura(String value) {
        this.sistemaGeradorFatura = value;
    }

    
    public String getUsuarioGeradorFatura() {
        return usuarioGeradorFatura;
    }

    
    public void setUsuarioGeradorFatura(String value) {
        this.usuarioGeradorFatura = value;
    }

    
    public String getExplicacaoContestacao() {
        return explicacaoContestacao;
    }

    
    public void setExplicacaoContestacao(String value) {
        this.explicacaoContestacao = value;
    }

    
    public ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura getListaItemFatura() {
        return listaItemFatura;
    }

    
    public void setListaItemFatura(ArrayOfitemFaturaPairOflistaItemFaturaKeyitemFatura value) {
        this.listaItemFatura = value;
    }

}
