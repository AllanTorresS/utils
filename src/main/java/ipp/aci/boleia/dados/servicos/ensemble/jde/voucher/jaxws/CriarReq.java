
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "criarReq", propOrder = {
    "cenario",
    "filial",
    "companhia",
    "codigoPagamento",
    "instrumentoPagamento",
    "numeroFatura",
    "dataFatura",
    "dataContabil",
    "dataTributacaoServico",
    "dataProcessamento",
    "codigoMoeda",
    "taxaConversao",
    "tipoMoeda",
    "observacao",
    "processamento",
    "chaveDocumento",
    "chavePedido",
    "fornecedor",
    "aprovador",
    "voucher",
    "distribuicaoContabil"
})
public class CriarReq
    extends EnsRequest
{

    protected long cenario;
    protected String filial;
    protected String companhia;
    protected String codigoPagamento;
    protected String instrumentoPagamento;
    protected String numeroFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabil;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTributacaoServico;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProcessamento;
    protected String codigoMoeda;
    protected BigDecimal taxaConversao;
    protected String tipoMoeda;
    protected String observacao;
    protected Processamento processamento;
    protected ChaveDocumento chaveDocumento;
    protected ChavePedido chavePedido;
    protected Fornecedor fornecedor;
    protected Aprovador aprovador;
    protected ArrayOfvouchervoucher voucher;
    protected ArrayOfdistribuicaoContabildistribuicaoContabil distribuicaoContabil;

    
    public long getCenario() {
        return cenario;
    }

    
    public void setCenario(long value) {
        this.cenario = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    
    public void setCodigoPagamento(String value) {
        this.codigoPagamento = value;
    }

    
    public String getInstrumentoPagamento() {
        return instrumentoPagamento;
    }

    
    public void setInstrumentoPagamento(String value) {
        this.instrumentoPagamento = value;
    }

    
    public String getNumeroFatura() {
        return numeroFatura;
    }

    
    public void setNumeroFatura(String value) {
        this.numeroFatura = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public XMLGregorianCalendar getDataContabil() {
        return dataContabil;
    }

    
    public void setDataContabil(XMLGregorianCalendar value) {
        this.dataContabil = value;
    }

    
    public XMLGregorianCalendar getDataTributacaoServico() {
        return dataTributacaoServico;
    }

    
    public void setDataTributacaoServico(XMLGregorianCalendar value) {
        this.dataTributacaoServico = value;
    }

    
    public XMLGregorianCalendar getDataProcessamento() {
        return dataProcessamento;
    }

    
    public void setDataProcessamento(XMLGregorianCalendar value) {
        this.dataProcessamento = value;
    }

    
    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    
    public void setCodigoMoeda(String value) {
        this.codigoMoeda = value;
    }

    
    public BigDecimal getTaxaConversao() {
        return taxaConversao;
    }

    
    public void setTaxaConversao(BigDecimal value) {
        this.taxaConversao = value;
    }

    
    public String getTipoMoeda() {
        return tipoMoeda;
    }

    
    public void setTipoMoeda(String value) {
        this.tipoMoeda = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public Processamento getProcessamento() {
        return processamento;
    }

    
    public void setProcessamento(Processamento value) {
        this.processamento = value;
    }

    
    public ChaveDocumento getChaveDocumento() {
        return chaveDocumento;
    }

    
    public void setChaveDocumento(ChaveDocumento value) {
        this.chaveDocumento = value;
    }

    
    public ChavePedido getChavePedido() {
        return chavePedido;
    }

    
    public void setChavePedido(ChavePedido value) {
        this.chavePedido = value;
    }

    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    
    public void setFornecedor(Fornecedor value) {
        this.fornecedor = value;
    }

    
    public Aprovador getAprovador() {
        return aprovador;
    }

    
    public void setAprovador(Aprovador value) {
        this.aprovador = value;
    }

    
    public ArrayOfvouchervoucher getVoucher() {
        return voucher;
    }

    
    public void setVoucher(ArrayOfvouchervoucher value) {
        this.voucher = value;
    }

    
    public ArrayOfdistribuicaoContabildistribuicaoContabil getDistribuicaoContabil() {
        return distribuicaoContabil;
    }

    
    public void setDistribuicaoContabil(ArrayOfdistribuicaoContabildistribuicaoContabil value) {
        this.distribuicaoContabil = value;
    }

}
