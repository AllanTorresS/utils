
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucherDetalhe", propOrder = {
    "codigoCliente",
    "codigoLongoCliente",
    "cpfCnpj",
    "dataVoucher",
    "dataVencimento",
    "anoFiscal",
    "seculo",
    "mes",
    "valorBrutoInterno",
    "valorEmAbertoInterno",
    "valorBrutoExterno",
    "valorEmAbertoExterno",
    "numeroContaBanco",
    "statusVoucher",
    "codigoBanco",
    "numeroSequencial",
    "detalhe",
    "centroCusto",
    "observacao",
    "pagamentoFinal",
    "quantidade",
    "unidadeMedida",
    "codigoInstrumentoPagamento",
    "originadorTransacao",
    "moeda",
    "itemPagamento",
    "numeroExtensaoItemPagamento",
    "numeroDocumentoOriginal",
    "tipoDocumentoOriginal",
    "companhiaDocumentoOriginal",
    "sufixoDocumentoOriginal",
    "linhaDocumento",
    "numeroDocumentoCompra",
    "tipoDocumentoCompra",
    "companhiaDocumentoCompra",
    "sufixoDocumentoCompra",
    "listaContabilizacao"
})
@XmlSeeAlso({
    PairOflistaVoucherDetalheKeyvoucherDetalhe.class
})
public class VoucherDetalhe {

    protected Long codigoCliente;
    protected String codigoLongoCliente;
    protected String cpfCnpj;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVoucher;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVencimento;
    protected Long anoFiscal;
    protected Long seculo;
    protected Long mes;
    protected BigDecimal valorBrutoInterno;
    protected BigDecimal valorEmAbertoInterno;
    protected BigDecimal valorBrutoExterno;
    protected BigDecimal valorEmAbertoExterno;
    protected String numeroContaBanco;
    protected String statusVoucher;
    protected String codigoBanco;
    protected Long numeroSequencial;
    protected String detalhe;
    protected String centroCusto;
    protected String observacao;
    protected String pagamentoFinal;
    protected BigDecimal quantidade;
    protected String unidadeMedida;
    protected String codigoInstrumentoPagamento;
    protected String originadorTransacao;
    protected String moeda;
    protected String itemPagamento;
    protected Long numeroExtensaoItemPagamento;
    protected Long numeroDocumentoOriginal;
    protected String tipoDocumentoOriginal;
    protected String companhiaDocumentoOriginal;
    protected String sufixoDocumentoOriginal;
    protected String linhaDocumento;
    protected Long numeroDocumentoCompra;
    protected String tipoDocumentoCompra;
    protected String companhiaDocumentoCompra;
    protected String sufixoDocumentoCompra;
    protected ArrayOfcontabilizacaoPairOflistaContabilizacaoKeycontabilizacao listaContabilizacao;

    
    public Long getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(Long value) {
        this.codigoCliente = value;
    }

    
    public String getCodigoLongoCliente() {
        return codigoLongoCliente;
    }

    
    public void setCodigoLongoCliente(String value) {
        this.codigoLongoCliente = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

    
    public XMLGregorianCalendar getDataVoucher() {
        return dataVoucher;
    }

    
    public void setDataVoucher(XMLGregorianCalendar value) {
        this.dataVoucher = value;
    }

    
    public XMLGregorianCalendar getDataVencimento() {
        return dataVencimento;
    }

    
    public void setDataVencimento(XMLGregorianCalendar value) {
        this.dataVencimento = value;
    }

    
    public Long getAnoFiscal() {
        return anoFiscal;
    }

    
    public void setAnoFiscal(Long value) {
        this.anoFiscal = value;
    }

    
    public Long getSeculo() {
        return seculo;
    }

    
    public void setSeculo(Long value) {
        this.seculo = value;
    }

    
    public Long getMes() {
        return mes;
    }

    
    public void setMes(Long value) {
        this.mes = value;
    }

    
    public BigDecimal getValorBrutoInterno() {
        return valorBrutoInterno;
    }

    
    public void setValorBrutoInterno(BigDecimal value) {
        this.valorBrutoInterno = value;
    }

    
    public BigDecimal getValorEmAbertoInterno() {
        return valorEmAbertoInterno;
    }

    
    public void setValorEmAbertoInterno(BigDecimal value) {
        this.valorEmAbertoInterno = value;
    }

    
    public BigDecimal getValorBrutoExterno() {
        return valorBrutoExterno;
    }

    
    public void setValorBrutoExterno(BigDecimal value) {
        this.valorBrutoExterno = value;
    }

    
    public BigDecimal getValorEmAbertoExterno() {
        return valorEmAbertoExterno;
    }

    
    public void setValorEmAbertoExterno(BigDecimal value) {
        this.valorEmAbertoExterno = value;
    }

    
    public String getNumeroContaBanco() {
        return numeroContaBanco;
    }

    
    public void setNumeroContaBanco(String value) {
        this.numeroContaBanco = value;
    }

    
    public String getStatusVoucher() {
        return statusVoucher;
    }

    
    public void setStatusVoucher(String value) {
        this.statusVoucher = value;
    }

    
    public String getCodigoBanco() {
        return codigoBanco;
    }

    
    public void setCodigoBanco(String value) {
        this.codigoBanco = value;
    }

    
    public Long getNumeroSequencial() {
        return numeroSequencial;
    }

    
    public void setNumeroSequencial(Long value) {
        this.numeroSequencial = value;
    }

    
    public String getDetalhe() {
        return detalhe;
    }

    
    public void setDetalhe(String value) {
        this.detalhe = value;
    }

    
    public String getCentroCusto() {
        return centroCusto;
    }

    
    public void setCentroCusto(String value) {
        this.centroCusto = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public String getPagamentoFinal() {
        return pagamentoFinal;
    }

    
    public void setPagamentoFinal(String value) {
        this.pagamentoFinal = value;
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

    
    public String getCodigoInstrumentoPagamento() {
        return codigoInstrumentoPagamento;
    }

    
    public void setCodigoInstrumentoPagamento(String value) {
        this.codigoInstrumentoPagamento = value;
    }

    
    public String getOriginadorTransacao() {
        return originadorTransacao;
    }

    
    public void setOriginadorTransacao(String value) {
        this.originadorTransacao = value;
    }

    
    public String getMoeda() {
        return moeda;
    }

    
    public void setMoeda(String value) {
        this.moeda = value;
    }

    
    public String getItemPagamento() {
        return itemPagamento;
    }

    
    public void setItemPagamento(String value) {
        this.itemPagamento = value;
    }

    
    public Long getNumeroExtensaoItemPagamento() {
        return numeroExtensaoItemPagamento;
    }

    
    public void setNumeroExtensaoItemPagamento(Long value) {
        this.numeroExtensaoItemPagamento = value;
    }

    
    public Long getNumeroDocumentoOriginal() {
        return numeroDocumentoOriginal;
    }

    
    public void setNumeroDocumentoOriginal(Long value) {
        this.numeroDocumentoOriginal = value;
    }

    
    public String getTipoDocumentoOriginal() {
        return tipoDocumentoOriginal;
    }

    
    public void setTipoDocumentoOriginal(String value) {
        this.tipoDocumentoOriginal = value;
    }

    
    public String getCompanhiaDocumentoOriginal() {
        return companhiaDocumentoOriginal;
    }

    
    public void setCompanhiaDocumentoOriginal(String value) {
        this.companhiaDocumentoOriginal = value;
    }

    
    public String getSufixoDocumentoOriginal() {
        return sufixoDocumentoOriginal;
    }

    
    public void setSufixoDocumentoOriginal(String value) {
        this.sufixoDocumentoOriginal = value;
    }

    
    public String getLinhaDocumento() {
        return linhaDocumento;
    }

    
    public void setLinhaDocumento(String value) {
        this.linhaDocumento = value;
    }

    
    public Long getNumeroDocumentoCompra() {
        return numeroDocumentoCompra;
    }

    
    public void setNumeroDocumentoCompra(Long value) {
        this.numeroDocumentoCompra = value;
    }

    
    public String getTipoDocumentoCompra() {
        return tipoDocumentoCompra;
    }

    
    public void setTipoDocumentoCompra(String value) {
        this.tipoDocumentoCompra = value;
    }

    
    public String getCompanhiaDocumentoCompra() {
        return companhiaDocumentoCompra;
    }

    
    public void setCompanhiaDocumentoCompra(String value) {
        this.companhiaDocumentoCompra = value;
    }

    
    public String getSufixoDocumentoCompra() {
        return sufixoDocumentoCompra;
    }

    
    public void setSufixoDocumentoCompra(String value) {
        this.sufixoDocumentoCompra = value;
    }

    
    public ArrayOfcontabilizacaoPairOflistaContabilizacaoKeycontabilizacao getListaContabilizacao() {
        return listaContabilizacao;
    }

    
    public void setListaContabilizacao(ArrayOfcontabilizacaoPairOflistaContabilizacaoKeycontabilizacao value) {
        this.listaContabilizacao = value;
    }

}
