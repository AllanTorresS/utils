
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarVoucherRetornoResp", propOrder = {
    "companhia",
    "filial",
    "numeroFatura",
    "taxaCambio",
    "moeda",
    "numeroLote",
    "tipoLote",
    "dataLote",
    "numeroVoucher",
    "tipoVoucher",
    "companhiaVoucher",
    "listaVoucherDetalhe"
})
@XmlSeeAlso({
    PairOflistaVoucherRetornoKeyconsultarVoucherRetornoResp.class
})
public class ConsultarVoucherRetornoResp {

    protected String companhia;
    protected String filial;
    protected String numeroFatura;
    protected Long taxaCambio;
    protected String moeda;
    protected Long numeroLote;
    protected String tipoLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLote;
    protected Long numeroVoucher;
    protected String tipoVoucher;
    protected String companhiaVoucher;
    protected ArrayOfvoucherDetalhePairOflistaVoucherDetalheKeyvoucherDetalhe listaVoucherDetalhe;

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getNumeroFatura() {
        return numeroFatura;
    }

    
    public void setNumeroFatura(String value) {
        this.numeroFatura = value;
    }

    
    public Long getTaxaCambio() {
        return taxaCambio;
    }

    
    public void setTaxaCambio(Long value) {
        this.taxaCambio = value;
    }

    
    public String getMoeda() {
        return moeda;
    }

    
    public void setMoeda(String value) {
        this.moeda = value;
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

    
    public Long getNumeroVoucher() {
        return numeroVoucher;
    }

    
    public void setNumeroVoucher(Long value) {
        this.numeroVoucher = value;
    }

    
    public String getTipoVoucher() {
        return tipoVoucher;
    }

    
    public void setTipoVoucher(String value) {
        this.tipoVoucher = value;
    }

    
    public String getCompanhiaVoucher() {
        return companhiaVoucher;
    }

    
    public void setCompanhiaVoucher(String value) {
        this.companhiaVoucher = value;
    }

    
    public ArrayOfvoucherDetalhePairOflistaVoucherDetalheKeyvoucherDetalhe getListaVoucherDetalhe() {
        return listaVoucherDetalhe;
    }

    
    public void setListaVoucherDetalhe(ArrayOfvoucherDetalhePairOflistaVoucherDetalheKeyvoucherDetalhe value) {
        this.listaVoucherDetalhe = value;
    }

}
