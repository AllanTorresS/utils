
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "itemFatura", propOrder = {
    "valorTransacao",
    "indiceConta",
    "numItemDocOriginal",
    "notaFiscalFornecedor",
    "numeroPedidoCompra",
    "tipoDocPedidoCompra",
    "ciaPedidoCompra",
    "numLinhaPedidoCompra",
    "numDocVendaFornecedor",
    "tipoDocVendaFornecedor",
    "ciaDocVendaFornecedor",
    "seqDocVendaFornecedor",
    "instrumentoPagamento",
    "dataVencimentoFatura",
    "codigoPosto"
})
@XmlSeeAlso({
    PairOflistaItemFaturaKeyitemFatura.class
})
public class ItemFatura {

    protected BigDecimal valorTransacao;
    protected BigDecimal indiceConta;
    protected String numItemDocOriginal;
    protected String notaFiscalFornecedor;
    protected String numeroPedidoCompra;
    protected String tipoDocPedidoCompra;
    protected String ciaPedidoCompra;
    protected Long numLinhaPedidoCompra;
    protected Long numDocVendaFornecedor;
    protected String tipoDocVendaFornecedor;
    protected String ciaDocVendaFornecedor;
    protected String seqDocVendaFornecedor;
    protected String instrumentoPagamento;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVencimentoFatura;
    protected String codigoPosto;

    
    public BigDecimal getValorTransacao() {
        return valorTransacao;
    }

    
    public void setValorTransacao(BigDecimal value) {
        this.valorTransacao = value;
    }

    
    public BigDecimal getIndiceConta() {
        return indiceConta;
    }

    
    public void setIndiceConta(BigDecimal value) {
        this.indiceConta = value;
    }

    
    public String getNumItemDocOriginal() {
        return numItemDocOriginal;
    }

    
    public void setNumItemDocOriginal(String value) {
        this.numItemDocOriginal = value;
    }

    
    public String getNotaFiscalFornecedor() {
        return notaFiscalFornecedor;
    }

    
    public void setNotaFiscalFornecedor(String value) {
        this.notaFiscalFornecedor = value;
    }

    
    public String getNumeroPedidoCompra() {
        return numeroPedidoCompra;
    }

    
    public void setNumeroPedidoCompra(String value) {
        this.numeroPedidoCompra = value;
    }

    
    public String getTipoDocPedidoCompra() {
        return tipoDocPedidoCompra;
    }

    
    public void setTipoDocPedidoCompra(String value) {
        this.tipoDocPedidoCompra = value;
    }

    
    public String getCiaPedidoCompra() {
        return ciaPedidoCompra;
    }

    
    public void setCiaPedidoCompra(String value) {
        this.ciaPedidoCompra = value;
    }

    
    public Long getNumLinhaPedidoCompra() {
        return numLinhaPedidoCompra;
    }

    
    public void setNumLinhaPedidoCompra(Long value) {
        this.numLinhaPedidoCompra = value;
    }

    
    public Long getNumDocVendaFornecedor() {
        return numDocVendaFornecedor;
    }

    
    public void setNumDocVendaFornecedor(Long value) {
        this.numDocVendaFornecedor = value;
    }

    
    public String getTipoDocVendaFornecedor() {
        return tipoDocVendaFornecedor;
    }

    
    public void setTipoDocVendaFornecedor(String value) {
        this.tipoDocVendaFornecedor = value;
    }

    
    public String getCiaDocVendaFornecedor() {
        return ciaDocVendaFornecedor;
    }

    
    public void setCiaDocVendaFornecedor(String value) {
        this.ciaDocVendaFornecedor = value;
    }

    
    public String getSeqDocVendaFornecedor() {
        return seqDocVendaFornecedor;
    }

    
    public void setSeqDocVendaFornecedor(String value) {
        this.seqDocVendaFornecedor = value;
    }

    
    public String getInstrumentoPagamento() {
        return instrumentoPagamento;
    }

    
    public void setInstrumentoPagamento(String value) {
        this.instrumentoPagamento = value;
    }

    
    public XMLGregorianCalendar getDataVencimentoFatura() {
        return dataVencimentoFatura;
    }

    
    public void setDataVencimentoFatura(XMLGregorianCalendar value) {
        this.dataVencimentoFatura = value;
    }

    
    public String getCodigoPosto() {
        return codigoPosto;
    }

    
    public void setCodigoPosto(String value) {
        this.codigoPosto = value;
    }

}
