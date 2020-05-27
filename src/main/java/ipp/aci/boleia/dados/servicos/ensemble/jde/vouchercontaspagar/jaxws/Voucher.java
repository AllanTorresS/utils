
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucher", propOrder = {
    "codigoPagamento",
    "numeroFatura",
    "dataEmissao",
    "dataContabilizacao",
    "codigoFornecedor",
    "codigoBeneficiario",
    "numDocCompra",
    "tipoDocCompra",
    "observacao",
    "listaItens"
})
@XmlSeeAlso({
    PairOflistaVoucherKeyvoucher.class
})
public class Voucher {

    protected String codigoPagamento;
    @XmlElement(required = true)
    protected String numeroFatura;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabilizacao;
    protected long codigoFornecedor;
    protected long codigoBeneficiario;
    protected String numDocCompra;
    protected String tipoDocCompra;
    protected String observacao;
    protected ArrayOfitemPairOflistaItensKeyitem listaItens;

    
    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    
    public void setCodigoPagamento(String value) {
        this.codigoPagamento = value;
    }

    
    public String getNumeroFatura() {
        return numeroFatura;
    }

    
    public void setNumeroFatura(String value) {
        this.numeroFatura = value;
    }

    
    public XMLGregorianCalendar getDataEmissao() {
        return dataEmissao;
    }

    
    public void setDataEmissao(XMLGregorianCalendar value) {
        this.dataEmissao = value;
    }

    
    public XMLGregorianCalendar getDataContabilizacao() {
        return dataContabilizacao;
    }

    
    public void setDataContabilizacao(XMLGregorianCalendar value) {
        this.dataContabilizacao = value;
    }

    
    public long getCodigoFornecedor() {
        return codigoFornecedor;
    }

    
    public void setCodigoFornecedor(long value) {
        this.codigoFornecedor = value;
    }

    
    public long getCodigoBeneficiario() {
        return codigoBeneficiario;
    }

    
    public void setCodigoBeneficiario(long value) {
        this.codigoBeneficiario = value;
    }

    
    public String getNumDocCompra() {
        return numDocCompra;
    }

    
    public void setNumDocCompra(String value) {
        this.numDocCompra = value;
    }

    
    public String getTipoDocCompra() {
        return tipoDocCompra;
    }

    
    public void setTipoDocCompra(String value) {
        this.tipoDocCompra = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

    
    public ArrayOfitemPairOflistaItensKeyitem getListaItens() {
        return listaItens;
    }

    
    public void setListaItens(ArrayOfitemPairOflistaItensKeyitem value) {
        this.listaItens = value;
    }

}
