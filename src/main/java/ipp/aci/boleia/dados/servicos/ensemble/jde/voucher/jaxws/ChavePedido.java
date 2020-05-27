
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chavePedido", propOrder = {
    "numeroDocumentoPedido",
    "tipoDocumentoPedido",
    "companhiaDocumentoPedido"
})
public class ChavePedido {

    protected String numeroDocumentoPedido;
    protected String tipoDocumentoPedido;
    protected String companhiaDocumentoPedido;

    
    public String getNumeroDocumentoPedido() {
        return numeroDocumentoPedido;
    }

    
    public void setNumeroDocumentoPedido(String value) {
        this.numeroDocumentoPedido = value;
    }

    
    public String getTipoDocumentoPedido() {
        return tipoDocumentoPedido;
    }

    
    public void setTipoDocumentoPedido(String value) {
        this.tipoDocumentoPedido = value;
    }

    
    public String getCompanhiaDocumentoPedido() {
        return companhiaDocumentoPedido;
    }

    
    public void setCompanhiaDocumentoPedido(String value) {
        this.companhiaDocumentoPedido = value;
    }

}
