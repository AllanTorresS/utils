
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperarBoletoResp", propOrder = {
    "nomeBanco",
    "usoDoBanco",
    "linhaDigitavel",
    "cedente",
    "dataDeVencimento",
    "nossoNumeroFormatado",
    "numeroDocumento",
    "tipoDocumento",
    "dataCocumento",
    "codigoAgencia",
    "valorDocumento",
    "valorAcrescimos",
    "valorCobrado",
    "sacado",
    "cpfCnpj",
    "localPagamento",
    "aceite",
    "dataProcessamento",
    "carteira",
    "moeda",
    "quantidade",
    "valorMoeda",
    "valorDesconto",
    "valorDeducoes",
    "valorMulta",
    "codigoBarras",
    "instrucoesLinha1",
    "instrucoesLinha2",
    "instrucoesLinha3",
    "instrucoesLinha4",
    "instrucoesLinha5",
    "instrucoesLinha6",
    "instrucoesLinha7",
    "instrucoesLinha8",
    "codigoCliente",
    "numeroBoleto",
    "codigoErro",
    "encontrouBoleto",
    "codigoBanco",
    "sacador",
    "companhia",
    "documento",
    "parcela",
    "tipoDocumentoAbadi"
})
public class RecuperarBoletoResp
    extends V2
{

    protected String nomeBanco;
    protected String usoDoBanco;
    protected String linhaDigitavel;
    protected String cedente;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDeVencimento;
    protected String nossoNumeroFormatado;
    protected String numeroDocumento;
    protected String tipoDocumento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCocumento;
    protected String codigoAgencia;
    protected BigDecimal valorDocumento;
    protected BigDecimal valorAcrescimos;
    protected BigDecimal valorCobrado;
    protected String sacado;
    protected String cpfCnpj;
    protected String localPagamento;
    protected String aceite;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataProcessamento;
    protected String carteira;
    protected String moeda;
    protected BigDecimal quantidade;
    protected BigDecimal valorMoeda;
    protected BigDecimal valorDesconto;
    protected BigDecimal valorDeducoes;
    protected BigDecimal valorMulta;
    protected String codigoBarras;
    protected String instrucoesLinha1;
    protected String instrucoesLinha2;
    protected String instrucoesLinha3;
    protected String instrucoesLinha4;
    protected String instrucoesLinha5;
    protected String instrucoesLinha6;
    protected String instrucoesLinha7;
    protected String instrucoesLinha8;
    protected BigDecimal codigoCliente;
    protected String numeroBoleto;
    protected String codigoErro;
    protected String encontrouBoleto;
    protected String codigoBanco;
    protected String sacador;
    protected String companhia;
    protected BigDecimal documento;
    protected String parcela;
    protected String tipoDocumentoAbadi;

    
    public String getNomeBanco() {
        return nomeBanco;
    }

    
    public void setNomeBanco(String value) {
        this.nomeBanco = value;
    }

    
    public String getUsoDoBanco() {
        return usoDoBanco;
    }

    
    public void setUsoDoBanco(String value) {
        this.usoDoBanco = value;
    }

    
    public String getLinhaDigitavel() {
        return linhaDigitavel;
    }

    
    public void setLinhaDigitavel(String value) {
        this.linhaDigitavel = value;
    }

    
    public String getCedente() {
        return cedente;
    }

    
    public void setCedente(String value) {
        this.cedente = value;
    }

    
    public XMLGregorianCalendar getDataDeVencimento() {
        return dataDeVencimento;
    }

    
    public void setDataDeVencimento(XMLGregorianCalendar value) {
        this.dataDeVencimento = value;
    }

    
    public String getNossoNumeroFormatado() {
        return nossoNumeroFormatado;
    }

    
    public void setNossoNumeroFormatado(String value) {
        this.nossoNumeroFormatado = value;
    }

    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public XMLGregorianCalendar getDataCocumento() {
        return dataCocumento;
    }

    
    public void setDataCocumento(XMLGregorianCalendar value) {
        this.dataCocumento = value;
    }

    
    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    
    public void setCodigoAgencia(String value) {
        this.codigoAgencia = value;
    }

    
    public BigDecimal getValorDocumento() {
        return valorDocumento;
    }

    
    public void setValorDocumento(BigDecimal value) {
        this.valorDocumento = value;
    }

    
    public BigDecimal getValorAcrescimos() {
        return valorAcrescimos;
    }

    
    public void setValorAcrescimos(BigDecimal value) {
        this.valorAcrescimos = value;
    }

    
    public BigDecimal getValorCobrado() {
        return valorCobrado;
    }

    
    public void setValorCobrado(BigDecimal value) {
        this.valorCobrado = value;
    }

    
    public String getSacado() {
        return sacado;
    }

    
    public void setSacado(String value) {
        this.sacado = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
    }

    
    public String getLocalPagamento() {
        return localPagamento;
    }

    
    public void setLocalPagamento(String value) {
        this.localPagamento = value;
    }

    
    public String getAceite() {
        return aceite;
    }

    
    public void setAceite(String value) {
        this.aceite = value;
    }

    
    public XMLGregorianCalendar getDataProcessamento() {
        return dataProcessamento;
    }

    
    public void setDataProcessamento(XMLGregorianCalendar value) {
        this.dataProcessamento = value;
    }

    
    public String getCarteira() {
        return carteira;
    }

    
    public void setCarteira(String value) {
        this.carteira = value;
    }

    
    public String getMoeda() {
        return moeda;
    }

    
    public void setMoeda(String value) {
        this.moeda = value;
    }

    
    public BigDecimal getQuantidade() {
        return quantidade;
    }

    
    public void setQuantidade(BigDecimal value) {
        this.quantidade = value;
    }

    
    public BigDecimal getValorMoeda() {
        return valorMoeda;
    }

    
    public void setValorMoeda(BigDecimal value) {
        this.valorMoeda = value;
    }

    
    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    
    public void setValorDesconto(BigDecimal value) {
        this.valorDesconto = value;
    }

    
    public BigDecimal getValorDeducoes() {
        return valorDeducoes;
    }

    
    public void setValorDeducoes(BigDecimal value) {
        this.valorDeducoes = value;
    }

    
    public BigDecimal getValorMulta() {
        return valorMulta;
    }

    
    public void setValorMulta(BigDecimal value) {
        this.valorMulta = value;
    }

    
    public String getCodigoBarras() {
        return codigoBarras;
    }

    
    public void setCodigoBarras(String value) {
        this.codigoBarras = value;
    }

    
    public String getInstrucoesLinha1() {
        return instrucoesLinha1;
    }

    
    public void setInstrucoesLinha1(String value) {
        this.instrucoesLinha1 = value;
    }

    
    public String getInstrucoesLinha2() {
        return instrucoesLinha2;
    }

    
    public void setInstrucoesLinha2(String value) {
        this.instrucoesLinha2 = value;
    }

    
    public String getInstrucoesLinha3() {
        return instrucoesLinha3;
    }

    
    public void setInstrucoesLinha3(String value) {
        this.instrucoesLinha3 = value;
    }

    
    public String getInstrucoesLinha4() {
        return instrucoesLinha4;
    }

    
    public void setInstrucoesLinha4(String value) {
        this.instrucoesLinha4 = value;
    }

    
    public String getInstrucoesLinha5() {
        return instrucoesLinha5;
    }

    
    public void setInstrucoesLinha5(String value) {
        this.instrucoesLinha5 = value;
    }

    
    public String getInstrucoesLinha6() {
        return instrucoesLinha6;
    }

    
    public void setInstrucoesLinha6(String value) {
        this.instrucoesLinha6 = value;
    }

    
    public String getInstrucoesLinha7() {
        return instrucoesLinha7;
    }

    
    public void setInstrucoesLinha7(String value) {
        this.instrucoesLinha7 = value;
    }

    
    public String getInstrucoesLinha8() {
        return instrucoesLinha8;
    }

    
    public void setInstrucoesLinha8(String value) {
        this.instrucoesLinha8 = value;
    }

    
    public BigDecimal getCodigoCliente() {
        return codigoCliente;
    }

    
    public void setCodigoCliente(BigDecimal value) {
        this.codigoCliente = value;
    }

    
    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    
    public void setNumeroBoleto(String value) {
        this.numeroBoleto = value;
    }

    
    public String getCodigoErro() {
        return codigoErro;
    }

    
    public void setCodigoErro(String value) {
        this.codigoErro = value;
    }

    
    public String getEncontrouBoleto() {
        return encontrouBoleto;
    }

    
    public void setEncontrouBoleto(String value) {
        this.encontrouBoleto = value;
    }

    
    public String getCodigoBanco() {
        return codigoBanco;
    }

    
    public void setCodigoBanco(String value) {
        this.codigoBanco = value;
    }

    
    public String getSacador() {
        return sacador;
    }

    
    public void setSacador(String value) {
        this.sacador = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public BigDecimal getDocumento() {
        return documento;
    }

    
    public void setDocumento(BigDecimal value) {
        this.documento = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public String getTipoDocumentoAbadi() {
        return tipoDocumentoAbadi;
    }

    
    public void setTipoDocumentoAbadi(String value) {
        this.tipoDocumentoAbadi = value;
    }

}
