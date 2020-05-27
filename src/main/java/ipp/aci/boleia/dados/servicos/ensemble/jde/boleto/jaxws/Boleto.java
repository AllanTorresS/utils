
package ipp.aci.boleia.dados.servicos.ensemble.jde.boleto.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "boleto", propOrder = {
    "nomeBanco",
    "codigoBanco",
    "usoBanco",
    "linhaDigitavel",
    "cedente",
    "vencimento",
    "nossoNumero",
    "numeroDocumento",
    "especieDocumento",
    "dataDocumento",
    "agenciaCodigo",
    "valorDocumento",
    "acrescimos",
    "valorCobrado",
    "nomeSacado",
    "cgcCpf",
    "localPagamento",
    "aceite",
    "dataProcessamento",
    "carteira",
    "especificacaoMoeda",
    "quantidade",
    "valorMoeda",
    "descontos",
    "deducoes",
    "multa",
    "instrMsg1",
    "instrMsg2",
    "instrMsg3",
    "instrMsg4",
    "instrMsg5",
    "instrMsg6",
    "instrMsg7",
    "bar",
    "sistemaOrigem"
})
@XmlSeeAlso({
    PairOfboletosKeyboleto.class
})
public class Boleto {

    protected String nomeBanco;
    protected String codigoBanco;
    protected String usoBanco;
    protected String linhaDigitavel;
    protected String cedente;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar vencimento;
    protected String nossoNumero;
    protected String numeroDocumento;
    protected String especieDocumento;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataDocumento;
    protected String agenciaCodigo;
    protected BigDecimal valorDocumento;
    protected BigDecimal acrescimos;
    protected BigDecimal valorCobrado;
    protected String nomeSacado;
    protected String cgcCpf;
    protected String localPagamento;
    protected String aceite;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProcessamento;
    protected String carteira;
    protected String especificacaoMoeda;
    protected BigDecimal quantidade;
    protected BigDecimal valorMoeda;
    protected BigDecimal descontos;
    protected BigDecimal deducoes;
    protected BigDecimal multa;
    protected String instrMsg1;
    protected String instrMsg2;
    protected String instrMsg3;
    protected String instrMsg4;
    protected String instrMsg5;
    protected String instrMsg6;
    protected String instrMsg7;
    protected String bar;
    protected String sistemaOrigem;

    
    public String getNomeBanco() {
        return nomeBanco;
    }

    
    public void setNomeBanco(String value) {
        this.nomeBanco = value;
    }

    
    public String getCodigoBanco() {
        return codigoBanco;
    }

    
    public void setCodigoBanco(String value) {
        this.codigoBanco = value;
    }

    
    public String getUsoBanco() {
        return usoBanco;
    }

    
    public void setUsoBanco(String value) {
        this.usoBanco = value;
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

    
    public XMLGregorianCalendar getVencimento() {
        return vencimento;
    }

    
    public void setVencimento(XMLGregorianCalendar value) {
        this.vencimento = value;
    }

    
    public String getNossoNumero() {
        return nossoNumero;
    }

    
    public void setNossoNumero(String value) {
        this.nossoNumero = value;
    }

    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    
    public String getEspecieDocumento() {
        return especieDocumento;
    }

    
    public void setEspecieDocumento(String value) {
        this.especieDocumento = value;
    }

    
    public XMLGregorianCalendar getDataDocumento() {
        return dataDocumento;
    }

    
    public void setDataDocumento(XMLGregorianCalendar value) {
        this.dataDocumento = value;
    }

    
    public String getAgenciaCodigo() {
        return agenciaCodigo;
    }

    
    public void setAgenciaCodigo(String value) {
        this.agenciaCodigo = value;
    }

    
    public BigDecimal getValorDocumento() {
        return valorDocumento;
    }

    
    public void setValorDocumento(BigDecimal value) {
        this.valorDocumento = value;
    }

    
    public BigDecimal getAcrescimos() {
        return acrescimos;
    }

    
    public void setAcrescimos(BigDecimal value) {
        this.acrescimos = value;
    }

    
    public BigDecimal getValorCobrado() {
        return valorCobrado;
    }

    
    public void setValorCobrado(BigDecimal value) {
        this.valorCobrado = value;
    }

    
    public String getNomeSacado() {
        return nomeSacado;
    }

    
    public void setNomeSacado(String value) {
        this.nomeSacado = value;
    }

    
    public String getCgcCpf() {
        return cgcCpf;
    }

    
    public void setCgcCpf(String value) {
        this.cgcCpf = value;
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

    
    public String getEspecificacaoMoeda() {
        return especificacaoMoeda;
    }

    
    public void setEspecificacaoMoeda(String value) {
        this.especificacaoMoeda = value;
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

    
    public BigDecimal getDescontos() {
        return descontos;
    }

    
    public void setDescontos(BigDecimal value) {
        this.descontos = value;
    }

    
    public BigDecimal getDeducoes() {
        return deducoes;
    }

    
    public void setDeducoes(BigDecimal value) {
        this.deducoes = value;
    }

    
    public BigDecimal getMulta() {
        return multa;
    }

    
    public void setMulta(BigDecimal value) {
        this.multa = value;
    }

    
    public String getInstrMsg1() {
        return instrMsg1;
    }

    
    public void setInstrMsg1(String value) {
        this.instrMsg1 = value;
    }

    
    public String getInstrMsg2() {
        return instrMsg2;
    }

    
    public void setInstrMsg2(String value) {
        this.instrMsg2 = value;
    }

    
    public String getInstrMsg3() {
        return instrMsg3;
    }

    
    public void setInstrMsg3(String value) {
        this.instrMsg3 = value;
    }

    
    public String getInstrMsg4() {
        return instrMsg4;
    }

    
    public void setInstrMsg4(String value) {
        this.instrMsg4 = value;
    }

    
    public String getInstrMsg5() {
        return instrMsg5;
    }

    
    public void setInstrMsg5(String value) {
        this.instrMsg5 = value;
    }

    
    public String getInstrMsg6() {
        return instrMsg6;
    }

    
    public void setInstrMsg6(String value) {
        this.instrMsg6 = value;
    }

    
    public String getInstrMsg7() {
        return instrMsg7;
    }

    
    public void setInstrMsg7(String value) {
        this.instrMsg7 = value;
    }

    
    public String getBar() {
        return bar;
    }

    
    public void setBar(String value) {
        this.bar = value;
    }

    
    public String getSistemaOrigem() {
        return sistemaOrigem;
    }

    
    public void setSistemaOrigem(String value) {
        this.sistemaOrigem = value;
    }

}
