
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscaChavePedidoCompra", propOrder = {
    "pedidoCompras",
    "tipoPedidoCompras",
    "companhiaPedidoCompras",
    "sufixoPedidoCompras"
})
public class BuscaChavePedidoCompra {

    protected String pedidoCompras;
    protected String tipoPedidoCompras;
    protected String companhiaPedidoCompras;
    protected String sufixoPedidoCompras;

    
    public String getPedidoCompras() {
        return pedidoCompras;
    }

    
    public void setPedidoCompras(String value) {
        this.pedidoCompras = value;
    }

    
    public String getTipoPedidoCompras() {
        return tipoPedidoCompras;
    }

    
    public void setTipoPedidoCompras(String value) {
        this.tipoPedidoCompras = value;
    }

    
    public String getCompanhiaPedidoCompras() {
        return companhiaPedidoCompras;
    }

    
    public void setCompanhiaPedidoCompras(String value) {
        this.companhiaPedidoCompras = value;
    }

    
    public String getSufixoPedidoCompras() {
        return sufixoPedidoCompras;
    }

    
    public void setSufixoPedidoCompras(String value) {
        this.sufixoPedidoCompras = value;
    }

}
