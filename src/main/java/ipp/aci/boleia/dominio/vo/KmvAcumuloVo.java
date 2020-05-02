package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade de acumulo de pontos para integração com o Km de Vantagens (KMV)
 */
public class KmvAcumuloVo {

    private KmvAcumuloPedidoVo pedido;

    public KmvAcumuloPedidoVo getPedido() {
        return pedido;
    }

    public void setPedido(KmvAcumuloPedidoVo pedido) {
        this.pedido = pedido;
    }
}
