
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucher", propOrder = {
    "condicaoPagamento",
    "valorMoedaNacional",
    "valorMoedaEstrangeira",
    "descontoDispMoedaNacional",
    "descontoDispMoedaEstrangeira",
    "descontoApliMoedaNacional",
    "descontoApliMoedaEstrangeira",
    "valorTribMoedaNacional",
    "valorTribMoedaEstrangeira",
    "valorNaoTribMoedaNacional",
    "valorNaoTribMoedaEstrangeira",
    "valorImpostoMoedaNacional",
    "valorImpostoMoedaEstrangeira",
    "impostoNaoRecMoedaNacional",
    "impostoNaoRecMoedaEstrangeira",
    "dataVencimento",
    "percentualImposto",
    "codigoImposto",
    "frequenciaRecorrencia",
    "numeroPagamentoRecorrencia",
    "item",
    "beneficiario",
    "chavePedido",
    "usoFuturo"
})
public class Voucher {

    protected String condicaoPagamento;
    protected String valorMoedaNacional;
    protected BigDecimal valorMoedaEstrangeira;
    protected BigDecimal descontoDispMoedaNacional;
    protected BigDecimal descontoDispMoedaEstrangeira;
    protected BigDecimal descontoApliMoedaNacional;
    protected BigDecimal descontoApliMoedaEstrangeira;
    protected BigDecimal valorTribMoedaNacional;
    protected BigDecimal valorTribMoedaEstrangeira;
    protected BigDecimal valorNaoTribMoedaNacional;
    protected BigDecimal valorNaoTribMoedaEstrangeira;
    protected BigDecimal valorImpostoMoedaNacional;
    protected BigDecimal valorImpostoMoedaEstrangeira;
    protected BigDecimal impostoNaoRecMoedaNacional;
    protected BigDecimal impostoNaoRecMoedaEstrangeira;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVencimento;
    protected String percentualImposto;
    protected String codigoImposto;
    protected String frequenciaRecorrencia;
    protected String numeroPagamentoRecorrencia;
    protected Item item;
    protected Beneficiario beneficiario;
    protected ChavePedido chavePedido;
    protected UsoFuturo usoFuturo;

    
    public String getCondicaoPagamento() {
        return condicaoPagamento;
    }

    
    public void setCondicaoPagamento(String value) {
        this.condicaoPagamento = value;
    }

    
    public String getValorMoedaNacional() {
        return valorMoedaNacional;
    }

    
    public void setValorMoedaNacional(String value) {
        this.valorMoedaNacional = value;
    }

    
    public BigDecimal getValorMoedaEstrangeira() {
        return valorMoedaEstrangeira;
    }

    
    public void setValorMoedaEstrangeira(BigDecimal value) {
        this.valorMoedaEstrangeira = value;
    }

    
    public BigDecimal getDescontoDispMoedaNacional() {
        return descontoDispMoedaNacional;
    }

    
    public void setDescontoDispMoedaNacional(BigDecimal value) {
        this.descontoDispMoedaNacional = value;
    }

    
    public BigDecimal getDescontoDispMoedaEstrangeira() {
        return descontoDispMoedaEstrangeira;
    }

    
    public void setDescontoDispMoedaEstrangeira(BigDecimal value) {
        this.descontoDispMoedaEstrangeira = value;
    }

    
    public BigDecimal getDescontoApliMoedaNacional() {
        return descontoApliMoedaNacional;
    }

    
    public void setDescontoApliMoedaNacional(BigDecimal value) {
        this.descontoApliMoedaNacional = value;
    }

    
    public BigDecimal getDescontoApliMoedaEstrangeira() {
        return descontoApliMoedaEstrangeira;
    }

    
    public void setDescontoApliMoedaEstrangeira(BigDecimal value) {
        this.descontoApliMoedaEstrangeira = value;
    }

    
    public BigDecimal getValorTribMoedaNacional() {
        return valorTribMoedaNacional;
    }

    
    public void setValorTribMoedaNacional(BigDecimal value) {
        this.valorTribMoedaNacional = value;
    }

    
    public BigDecimal getValorTribMoedaEstrangeira() {
        return valorTribMoedaEstrangeira;
    }

    
    public void setValorTribMoedaEstrangeira(BigDecimal value) {
        this.valorTribMoedaEstrangeira = value;
    }

    
    public BigDecimal getValorNaoTribMoedaNacional() {
        return valorNaoTribMoedaNacional;
    }

    
    public void setValorNaoTribMoedaNacional(BigDecimal value) {
        this.valorNaoTribMoedaNacional = value;
    }

    
    public BigDecimal getValorNaoTribMoedaEstrangeira() {
        return valorNaoTribMoedaEstrangeira;
    }

    
    public void setValorNaoTribMoedaEstrangeira(BigDecimal value) {
        this.valorNaoTribMoedaEstrangeira = value;
    }

    
    public BigDecimal getValorImpostoMoedaNacional() {
        return valorImpostoMoedaNacional;
    }

    
    public void setValorImpostoMoedaNacional(BigDecimal value) {
        this.valorImpostoMoedaNacional = value;
    }

    
    public BigDecimal getValorImpostoMoedaEstrangeira() {
        return valorImpostoMoedaEstrangeira;
    }

    
    public void setValorImpostoMoedaEstrangeira(BigDecimal value) {
        this.valorImpostoMoedaEstrangeira = value;
    }

    
    public BigDecimal getImpostoNaoRecMoedaNacional() {
        return impostoNaoRecMoedaNacional;
    }

    
    public void setImpostoNaoRecMoedaNacional(BigDecimal value) {
        this.impostoNaoRecMoedaNacional = value;
    }

    
    public BigDecimal getImpostoNaoRecMoedaEstrangeira() {
        return impostoNaoRecMoedaEstrangeira;
    }

    
    public void setImpostoNaoRecMoedaEstrangeira(BigDecimal value) {
        this.impostoNaoRecMoedaEstrangeira = value;
    }

    
    public XMLGregorianCalendar getDataVencimento() {
        return dataVencimento;
    }

    
    public void setDataVencimento(XMLGregorianCalendar value) {
        this.dataVencimento = value;
    }

    
    public String getPercentualImposto() {
        return percentualImposto;
    }

    
    public void setPercentualImposto(String value) {
        this.percentualImposto = value;
    }

    
    public String getCodigoImposto() {
        return codigoImposto;
    }

    
    public void setCodigoImposto(String value) {
        this.codigoImposto = value;
    }

    
    public String getFrequenciaRecorrencia() {
        return frequenciaRecorrencia;
    }

    
    public void setFrequenciaRecorrencia(String value) {
        this.frequenciaRecorrencia = value;
    }

    
    public String getNumeroPagamentoRecorrencia() {
        return numeroPagamentoRecorrencia;
    }

    
    public void setNumeroPagamentoRecorrencia(String value) {
        this.numeroPagamentoRecorrencia = value;
    }

    
    public Item getItem() {
        return item;
    }

    
    public void setItem(Item value) {
        this.item = value;
    }

    
    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    
    public void setBeneficiario(Beneficiario value) {
        this.beneficiario = value;
    }

    
    public ChavePedido getChavePedido() {
        return chavePedido;
    }

    
    public void setChavePedido(ChavePedido value) {
        this.chavePedido = value;
    }

    
    public UsoFuturo getUsoFuturo() {
        return usoFuturo;
    }

    
    public void setUsoFuturo(UsoFuturo value) {
        this.usoFuturo = value;
    }

}
