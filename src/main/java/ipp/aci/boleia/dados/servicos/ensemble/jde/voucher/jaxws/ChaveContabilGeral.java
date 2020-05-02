
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chaveContabilGeral", propOrder = {
    "documentoContabil",
    "tipoDocumentoContabil",
    "companhiaDocumentoContabil",
    "linhaDocumentoContabil",
    "dataContabilizacao",
    "codigoExtensaoLinha",
    "tipoRazao"
})
public class ChaveContabilGeral {

    protected Long documentoContabil;
    protected String tipoDocumentoContabil;
    protected String companhiaDocumentoContabil;
    protected Long linhaDocumentoContabil;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabilizacao;
    protected String codigoExtensaoLinha;
    protected String tipoRazao;

    
    public Long getDocumentoContabil() {
        return documentoContabil;
    }

    
    public void setDocumentoContabil(Long value) {
        this.documentoContabil = value;
    }

    
    public String getTipoDocumentoContabil() {
        return tipoDocumentoContabil;
    }

    
    public void setTipoDocumentoContabil(String value) {
        this.tipoDocumentoContabil = value;
    }

    
    public String getCompanhiaDocumentoContabil() {
        return companhiaDocumentoContabil;
    }

    
    public void setCompanhiaDocumentoContabil(String value) {
        this.companhiaDocumentoContabil = value;
    }

    
    public Long getLinhaDocumentoContabil() {
        return linhaDocumentoContabil;
    }

    
    public void setLinhaDocumentoContabil(Long value) {
        this.linhaDocumentoContabil = value;
    }

    
    public XMLGregorianCalendar getDataContabilizacao() {
        return dataContabilizacao;
    }

    
    public void setDataContabilizacao(XMLGregorianCalendar value) {
        this.dataContabilizacao = value;
    }

    
    public String getCodigoExtensaoLinha() {
        return codigoExtensaoLinha;
    }

    
    public void setCodigoExtensaoLinha(String value) {
        this.codigoExtensaoLinha = value;
    }

    
    public String getTipoRazao() {
        return tipoRazao;
    }

    
    public void setTipoRazao(String value) {
        this.tipoRazao = value;
    }

}
