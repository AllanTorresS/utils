package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Extrato Pedido transacao
 */
@Entity
@Audited
@Table(name = "EXTRATO_PEDIDO_TRANS")
public class ExtratoPedidoTransacao implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -5988383286643194822L;

    @Id
    @Column(name = "CD_EXTRATO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXTRATO_PEDIDO_TRANS")
    @SequenceGenerator(name = "SEQ_EXTRATO_PEDIDO_TRANS", sequenceName = "SEQ_EXTRATO_PEDIDO_TRANS", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANSACAO_FROTA")
    private TransacaoFrota transacaoFrota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PEDIDO_CREDITO")
    private PedidoCreditoFrota pedido;

    @NotNull
    @Column(name = "VA_VALOR")
    private BigDecimal valor;

    @NotAudited
    @Formula("CD_PEDIDO_CREDITO")
    private Long codigoPedido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public TransacaoFrota getTransacaoFrota() {
        return transacaoFrota;
    }

    public void setTransacaoFrota(TransacaoFrota transacaoFrota) {
        this.transacaoFrota = transacaoFrota;
    }

    public PedidoCreditoFrota getPedido() {
        return pedido;
    }

    public void setPedido(PedidoCreditoFrota pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public List<Frota> getFrotas() {
        return transacaoFrota != null ? transacaoFrota.getFrotas() : null;
    }

    public Long getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(Long codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
}
