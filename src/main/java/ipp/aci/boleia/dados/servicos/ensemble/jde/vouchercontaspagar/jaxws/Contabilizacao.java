
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contabilizacao", propOrder = {
    "codigoContabilizacaoLote",
    "numeroLote",
    "tipoLote",
    "dataLote",
    "dataLoteSistema",
    "horaLote",
    "companhia",
    "codigoConta",
    "codigoLongoConta",
    "mes",
    "seculo",
    "anoFiscal",
    "moeda",
    "taxaCambio",
    "data",
    "valorReal",
    "valorConvertido",
    "quantidade",
    "unidadeMedida",
    "descricao",
    "comentario",
    "originadorTransacao",
    "numeroDocumento",
    "tipoDocumento",
    "companhiaDocumento",
    "linhaDocumento",
    "dataContabilizacao",
    "filial",
    "conta",
    "codigoCusto",
    "subconta",
    "tipoSubconta"
})
@XmlSeeAlso({
    PairOflistaContabilizacaoKeycontabilizacao.class
})
public class Contabilizacao {

    protected String codigoContabilizacaoLote;
    protected Long numeroLote;
    protected String tipoLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLoteSistema;
    protected String horaLote;
    protected String companhia;
    protected String codigoConta;
    protected String codigoLongoConta;
    protected Long mes;
    protected Long seculo;
    protected Long anoFiscal;
    protected String moeda;
    protected Long taxaCambio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    protected BigDecimal valorReal;
    protected BigDecimal valorConvertido;
    protected BigDecimal quantidade;
    protected String unidadeMedida;
    protected String descricao;
    protected String comentario;
    protected String originadorTransacao;
    protected Long numeroDocumento;
    protected String tipoDocumento;
    protected String companhiaDocumento;
    protected Long linhaDocumento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabilizacao;
    protected String filial;
    protected String conta;
    protected String codigoCusto;
    protected String subconta;
    protected String tipoSubconta;

    
    public String getCodigoContabilizacaoLote() {
        return codigoContabilizacaoLote;
    }

    
    public void setCodigoContabilizacaoLote(String value) {
        this.codigoContabilizacaoLote = value;
    }

    
    public Long getNumeroLote() {
        return numeroLote;
    }

    
    public void setNumeroLote(Long value) {
        this.numeroLote = value;
    }

    
    public String getTipoLote() {
        return tipoLote;
    }

    
    public void setTipoLote(String value) {
        this.tipoLote = value;
    }

    
    public XMLGregorianCalendar getDataLote() {
        return dataLote;
    }

    
    public void setDataLote(XMLGregorianCalendar value) {
        this.dataLote = value;
    }

    
    public XMLGregorianCalendar getDataLoteSistema() {
        return dataLoteSistema;
    }

    
    public void setDataLoteSistema(XMLGregorianCalendar value) {
        this.dataLoteSistema = value;
    }

    
    public String getHoraLote() {
        return horaLote;
    }

    
    public void setHoraLote(String value) {
        this.horaLote = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getCodigoConta() {
        return codigoConta;
    }

    
    public void setCodigoConta(String value) {
        this.codigoConta = value;
    }

    
    public String getCodigoLongoConta() {
        return codigoLongoConta;
    }

    
    public void setCodigoLongoConta(String value) {
        this.codigoLongoConta = value;
    }

    
    public Long getMes() {
        return mes;
    }

    
    public void setMes(Long value) {
        this.mes = value;
    }

    
    public Long getSeculo() {
        return seculo;
    }

    
    public void setSeculo(Long value) {
        this.seculo = value;
    }

    
    public Long getAnoFiscal() {
        return anoFiscal;
    }

    
    public void setAnoFiscal(Long value) {
        this.anoFiscal = value;
    }

    
    public String getMoeda() {
        return moeda;
    }

    
    public void setMoeda(String value) {
        this.moeda = value;
    }

    
    public Long getTaxaCambio() {
        return taxaCambio;
    }

    
    public void setTaxaCambio(Long value) {
        this.taxaCambio = value;
    }

    
    public XMLGregorianCalendar getData() {
        return data;
    }

    
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    
    public BigDecimal getValorReal() {
        return valorReal;
    }

    
    public void setValorReal(BigDecimal value) {
        this.valorReal = value;
    }

    
    public BigDecimal getValorConvertido() {
        return valorConvertido;
    }

    
    public void setValorConvertido(BigDecimal value) {
        this.valorConvertido = value;
    }

    
    public BigDecimal getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(BigDecimal value) {
        this.quantidade = value;
    }

    
    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    
    public void setUnidadeMedida(String value) {
        this.unidadeMedida = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public String getComentario() {
        return comentario;
    }

    
    public void setComentario(String value) {
        this.comentario = value;
    }

    
    public String getOriginadorTransacao() {
        return originadorTransacao;
    }

    
    public void setOriginadorTransacao(String value) {
        this.originadorTransacao = value;
    }

    
    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(Long value) {
        this.numeroDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public Long getLinhaDocumento() {
        return linhaDocumento;
    }

    
    public void setLinhaDocumento(Long value) {
        this.linhaDocumento = value;
    }

    
    public XMLGregorianCalendar getDataContabilizacao() {
        return dataContabilizacao;
    }

    
    public void setDataContabilizacao(XMLGregorianCalendar value) {
        this.dataContabilizacao = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getConta() {
        return conta;
    }

    
    public void setConta(String value) {
        this.conta = value;
    }

    
    public String getCodigoCusto() {
        return codigoCusto;
    }

    
    public void setCodigoCusto(String value) {
        this.codigoCusto = value;
    }

    
    public String getSubconta() {
        return subconta;
    }

    
    public void setSubconta(String value) {
        this.subconta = value;
    }

    
    public String getTipoSubconta() {
        return tipoSubconta;
    }

    
    public void setTipoSubconta(String value) {
        this.tipoSubconta = value;
    }

}
