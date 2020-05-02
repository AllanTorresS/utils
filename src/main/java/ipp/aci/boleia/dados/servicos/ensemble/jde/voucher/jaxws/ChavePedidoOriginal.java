
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chavePedidoOriginal", propOrder = {
    "documentoOriginal",
    "tipoDocumentoOriginal",
    "companhiaDocumentoOriginal",
    "sufixoDocumentoOriginal"
})
public class ChavePedidoOriginal {

    protected Long documentoOriginal;
    protected String tipoDocumentoOriginal;
    protected String companhiaDocumentoOriginal;
    protected String sufixoDocumentoOriginal;

    
    public Long getDocumentoOriginal() {
        return documentoOriginal;
    }

    
    public void setDocumentoOriginal(Long value) {
        this.documentoOriginal = value;
    }

    
    public String getTipoDocumentoOriginal() {
        return tipoDocumentoOriginal;
    }

    
    public void setTipoDocumentoOriginal(String value) {
        this.tipoDocumentoOriginal = value;
    }

    
    public String getCompanhiaDocumentoOriginal() {
        return companhiaDocumentoOriginal;
    }

    
    public void setCompanhiaDocumentoOriginal(String value) {
        this.companhiaDocumentoOriginal = value;
    }

    
    public String getSufixoDocumentoOriginal() {
        return sufixoDocumentoOriginal;
    }

    
    public void setSufixoDocumentoOriginal(String value) {
        this.sufixoDocumentoOriginal = value;
    }

}
