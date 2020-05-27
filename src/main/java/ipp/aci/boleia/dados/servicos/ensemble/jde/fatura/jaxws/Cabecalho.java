
package ipp.aci.boleia.dados.servicos.ensemble.jde.fatura.jaxws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Cabecalho", propOrder = {
    "numeroNF",
    "serieNF",
    "tipoDocumento",
    "descDocumento",
    "ciaFiscal",
    "centroDeCusto",
    "modeloNF",
    "parcela",
    "codClienteAN8",
    "valorBruto",
    "valorTotal",
    "saldo",
    "numeroBoleto",
    "dataVencimento",
    "dataBaixa",
    "listaDetalhes"
})
@XmlSeeAlso({
    PairOflistaCabecalhosKeyCabecalho.class
})
public class Cabecalho {

    protected BigDecimal numeroNF;
    protected String serieNF;
    protected String tipoDocumento;
    protected String descDocumento;
    protected String ciaFiscal;
    protected String centroDeCusto;
    protected String modeloNF;
    protected String parcela;
    protected BigDecimal codClienteAN8;
    protected BigDecimal valorBruto;
    protected BigDecimal valorTotal;
    protected BigDecimal saldo;
    protected String numeroBoleto;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataVencimento;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataBaixa;
    protected ArrayOfDetalhePairOflistaDetalhesKeyDetalhe listaDetalhes;

    
    public BigDecimal getNumeroNF() {
        return numeroNF;
    }

    
    public void setNumeroNF(BigDecimal value) {
        this.numeroNF = value;
    }

    
    public String getSerieNF() {
        return serieNF;
    }

    
    public void setSerieNF(String value) {
        this.serieNF = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getDescDocumento() {
        return descDocumento;
    }

    
    public void setDescDocumento(String value) {
        this.descDocumento = value;
    }

    
    public String getCiaFiscal() {
        return ciaFiscal;
    }

    
    public void setCiaFiscal(String value) {
        this.ciaFiscal = value;
    }

    
    public String getCentroDeCusto() {
        return centroDeCusto;
    }

    
    public void setCentroDeCusto(String value) {
        this.centroDeCusto = value;
    }

    
    public String getModeloNF() {
        return modeloNF;
    }

    
    public void setModeloNF(String value) {
        this.modeloNF = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public BigDecimal getCodClienteAN8() {
        return codClienteAN8;
    }

    
    public void setCodClienteAN8(BigDecimal value) {
        this.codClienteAN8 = value;
    }

    
    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    
    public void setValorBruto(BigDecimal value) {
        this.valorBruto = value;
    }

    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    
    public void setValorTotal(BigDecimal value) {
        this.valorTotal = value;
    }

    
    public BigDecimal getSaldo() {
        return saldo;
    }

    
    public void setSaldo(BigDecimal value) {
        this.saldo = value;
    }

    
    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    
    public void setNumeroBoleto(String value) {
        this.numeroBoleto = value;
    }

    
    public XMLGregorianCalendar getDataVencimento() {
        return dataVencimento;
    }

    
    public void setDataVencimento(XMLGregorianCalendar value) {
        this.dataVencimento = value;
    }

    
    public XMLGregorianCalendar getDataBaixa() {
        return dataBaixa;
    }

    
    public void setDataBaixa(XMLGregorianCalendar value) {
        this.dataBaixa = value;
    }

    
    public ArrayOfDetalhePairOflistaDetalhesKeyDetalhe getListaDetalhes() {
        return listaDetalhes;
    }

    
    public void setListaDetalhes(ArrayOfDetalhePairOflistaDetalhesKeyDetalhe value) {
        this.listaDetalhes = value;
    }

}
