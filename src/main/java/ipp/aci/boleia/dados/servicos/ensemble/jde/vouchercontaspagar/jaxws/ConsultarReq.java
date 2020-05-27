
package ipp.aci.boleia.dados.servicos.ensemble.jde.vouchercontaspagar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarReq", propOrder = {
    "companhia",
    "filial",
    "numeroNota",
    "dataEmissao",
    "dataContabil",
    "numeroDocumento",
    "tipoDocumento",
    "ciaDocumento",
    "codigoFornecedor",
    "cpfCnpj",
    "itemPagamento",
    "numeroExtensaoItemPagamento"
})
public class ConsultarReq
    extends EnsRequest
{

    @XmlElement(required = true)
    protected String companhia;
    @XmlElement(required = true)
    protected String filial;
    protected String numeroNota;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataEmissao;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabil;
    protected Long numeroDocumento;
    protected String tipoDocumento;
    protected String ciaDocumento;
    protected Long codigoFornecedor;
    protected String cpfCnpj;
    protected String itemPagamento;
    protected Long numeroExtensaoItemPagamento;

    
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

    
    public String getNumeroNota() {
        return numeroNota;
    }

    
    public void setNumeroNota(String value) {
        this.numeroNota = value;
    }

    
    public XMLGregorianCalendar getDataEmissao() {
        return dataEmissao;
    }

    
    public void setDataEmissao(XMLGregorianCalendar value) {
        this.dataEmissao = value;
    }

    
    public XMLGregorianCalendar getDataContabil() {
        return dataContabil;
    }

    
    public void setDataContabil(XMLGregorianCalendar value) {
        this.dataContabil = value;
    }

    
    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    
    public void setNumeroDocumento(Long value) {
        this.numeroDocumento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCiaDocumento() {
        return ciaDocumento;
    }

    
    public void setCiaDocumento(String value) {
        this.ciaDocumento = value;
    }

    
    public Long getCodigoFornecedor() {
        return codigoFornecedor;
    }

    
    public void setCodigoFornecedor(Long value) {
        this.codigoFornecedor = value;
    }

    
    public String getCpfCnpj() {
        return cpfCnpj;
    }

    
    public void setCpfCnpj(String value) {
        this.cpfCnpj = value;
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

}
