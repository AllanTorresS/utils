
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarReq", propOrder = {
    "codigoFornecedor",
    "documento",
    "tipoDocumento",
    "companhiaDocumento",
    "parcela",
    "extensaoParcela",
    "filial",
    "companhia",
    "numeroFatura",
    "dataFatura",
    "dataContabilizacao",
    "observacao"
})
public class BuscarReq
    extends EnsRequest
{

    protected long codigoFornecedor;
    protected long documento;
    @XmlElement(required = true)
    protected String tipoDocumento;
    @XmlElement(required = true)
    protected String companhiaDocumento;
    protected String parcela;
    protected Long extensaoParcela;
    protected String filial;
    protected String companhia;
    protected String numeroFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFatura;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataContabilizacao;
    protected String observacao;

    
    public long getCodigoFornecedor() {
        return codigoFornecedor;
    }

    
    public void setCodigoFornecedor(long value) {
        this.codigoFornecedor = value;
    }

    
    public long getDocumento() {
        return documento;
    }

    
    public void setDocumento(long value) {
        this.documento = value;
    }

    
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    
    public void setTipoDocumento(String value) {
        this.tipoDocumento = value;
    }

    
    public String getCompanhiaDocumento() {
        return companhiaDocumento;
    }

    
    public void setCompanhiaDocumento(String value) {
        this.companhiaDocumento = value;
    }

    
    public String getParcela() {
        return parcela;
    }

    
    public void setParcela(String value) {
        this.parcela = value;
    }

    
    public Long getExtensaoParcela() {
        return extensaoParcela;
    }

    
    public void setExtensaoParcela(Long value) {
        this.extensaoParcela = value;
    }

    
    public String getFilial() {
        return filial;
    }

    
    public void setFilial(String value) {
        this.filial = value;
    }

    
    public String getCompanhia() {
        return companhia;
    }

    
    public void setCompanhia(String value) {
        this.companhia = value;
    }

    
    public String getNumeroFatura() {
        return numeroFatura;
    }

    
    public void setNumeroFatura(String value) {
        this.numeroFatura = value;
    }

    
    public XMLGregorianCalendar getDataFatura() {
        return dataFatura;
    }

    
    public void setDataFatura(XMLGregorianCalendar value) {
        this.dataFatura = value;
    }

    
    public XMLGregorianCalendar getDataContabilizacao() {
        return dataContabilizacao;
    }

    
    public void setDataContabilizacao(XMLGregorianCalendar value) {
        this.dataContabilizacao = value;
    }

    
    public String getObservacao() {
        return observacao;
    }

    
    public void setObservacao(String value) {
        this.observacao = value;
    }

}
