
package ipp.aci.boleia.dados.servicos.ensemble.jde.voucher.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chavePedidoCompraLinha", propOrder = {
    "linhaPedidoCompras",
    "buscaChavePedidoCompra"
})
public class ChavePedidoCompraLinha {

    protected Long linhaPedidoCompras;
    protected BuscaChavePedidoCompra buscaChavePedidoCompra;

    
    public Long getLinhaPedidoCompras() {
        return linhaPedidoCompras;
    }

    
    public void setLinhaPedidoCompras(Long value) {
        this.linhaPedidoCompras = value;
    }

    
    public BuscaChavePedidoCompra getBuscaChavePedidoCompra() {
        return buscaChavePedidoCompra;
    }

    
    public void setBuscaChavePedidoCompra(BuscaChavePedidoCompra value) {
        this.buscaChavePedidoCompra = value;
    }

}
