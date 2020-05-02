
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cabecalho", propOrder = {
    "chaveVoucher",
    "companhia",
    "filial",
    "numeroFatura",
    "taxaConversaoMoeda",
    "codigoMoeda",
    "tipoMoeda",
    "numeroLote",
    "tipoLote",
    "dataLote",
    "contabilGeral",
    "detalhe"
})
public class Cabecalho {

    protected ChaveVoucher chaveVoucher;
    protected String companhia;
    protected String filial;
    protected String numeroFatura;
    protected Long taxaConversaoMoeda;
    protected String codigoMoeda;
    protected String tipoMoeda;
    protected Long numeroLote;
    protected String tipoLote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLote;
    protected ArrayOfcontabilGeralcontabilGeral contabilGeral;
    protected ArrayOfdetalhedetalhe detalhe;

    
    public ChaveVoucher getChaveVoucher() {
        return chaveVoucher;
    }

    
    public void setChaveVoucher(ChaveVoucher value) {
        this.chaveVoucher = value;
    }

    
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

    
    public Long getTaxaConversaoMoeda() {
        return taxaConversaoMoeda;
    }

    
    public void setTaxaConversaoMoeda(Long value) {
        this.taxaConversaoMoeda = value;
    }

    
    public String getCodigoMoeda() {
        return codigoMoeda;
    }

    
    public void setCodigoMoeda(String value) {
        this.codigoMoeda = value;
    }

    
    public String getTipoMoeda() {
        return tipoMoeda;
    }

    
    public void setTipoMoeda(String value) {
        this.tipoMoeda = value;
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

    
    public ArrayOfcontabilGeralcontabilGeral getContabilGeral() {
        return contabilGeral;
    }

    
    public void setContabilGeral(ArrayOfcontabilGeralcontabilGeral value) {
        this.contabilGeral = value;
    }

    
    public ArrayOfdetalhedetalhe getDetalhe() {
        return detalhe;
    }

    
    public void setDetalhe(ArrayOfdetalhedetalhe value) {
        this.detalhe = value;
    }

}
