package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Representa a tabela de Item Autorizacao Pagamento Edição
 */
@Entity
@Audited
@Table(name = "ITEM_AUT_PGTO_EDICAO")
public class ItemAutorizacaoPagamentoEdicao implements IPersistente{

    private static final long serialVersionUID = -6011764621502128914L;

    @Id
    @Column(name = "CD_ITEM_AUT_PGTO_EDICAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_AUT_PGTO_EDICAO")
    @SequenceGenerator(name = "SEQ_ITEM_AUT_PGTO_EDICAO", sequenceName = "SEQ_ITEM_AUT_PGTO_EDICAO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PGTO_EDICAO")
    private AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel combustivel;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PRODUTO")
    private Produto produto;

    @Size(max = 250)
    @Column(name = "NM_ITEM")
    private String nome;

    @NotNull
    @Column(name = "ID_TIPO")
    private Integer tipoItem;

    @NotNull
    @Digits(integer = 8, fraction = 12)
    @Column(name = "QT_ITEM")
    private BigDecimal quantidade;

    @Digits(integer = 8, fraction = 12)
    @Column(name = "VA_UNITARIO")
    private BigDecimal valorUnitario;

    @Digits(integer = 8, fraction = 12)
    @Column(name = "VA_TOTAL")
    private BigDecimal valorTotal;

    @Digits(integer = 8, fraction = 12)
    @Column(name = "VA_DESCONTO_TOTAL")
    private BigDecimal valorDescontoTotal;

    @Digits(integer = 3, fraction = 2)
    @Column(name = "VA_PERCENTUAL_DESCONTO")
    private BigDecimal valorPercentualDesconto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CAMPANHA")
    private Campanha campanha;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    /**
     * Construtor de um ItemAutorizacaoPagamentoEdicao
     */
    public ItemAutorizacaoPagamentoEdicao() {

    }

    /**
     * Construtor de um ItemAutorizacaoPagamentoEdicao
     * @param autorizacaoPagamentoEdicao A AutorizacaoPagamentoEdicao associada ao item
     * @param item Um item de uma AutorizacaoPagamento que será associado
     */
    public ItemAutorizacaoPagamentoEdicao(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao, ItemAutorizacaoPagamento item) {
        this.setNome(item.getNome());
        this.setTipoItem(item.getTipoItem());
        this.setCombustivel(item.getCombustivel());
        this.setProduto(item.getProduto());
        this.setQuantidade(item.getQuantidade());
        this.setValorUnitario(item.getValorUnitario());
        this.setValorTotal(item.getValorTotal());
        this.setValorDescontoTotal(item.getValorDescontoTotal());
        this.setValorPercentualDesconto(item.getValorPercentualDesconto());
        this.setCampanha(item.getCampanha());
        this.setAutorizacaoPagamentoEdicao(autorizacaoPagamentoEdicao);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AutorizacaoPagamentoEdicao getAutorizacaoPagamentoEdicao() {
        return autorizacaoPagamentoEdicao;
    }

    public void setAutorizacaoPagamentoEdicao(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao) {
        this.autorizacaoPagamentoEdicao = autorizacaoPagamentoEdicao;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(Integer tipoItem) {
        this.tipoItem = tipoItem;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDescontoTotal() {
        return valorDescontoTotal;
    }

    public void setValorDescontoTotal(BigDecimal valorDescontoTotal) {
        this.valorDescontoTotal = valorDescontoTotal;
    }

    public BigDecimal getValorPercentualDesconto() {
        return valorPercentualDesconto;
    }

    public void setValorPercentualDesconto(BigDecimal valorPercentualDesconto) {
        this.valorPercentualDesconto = valorPercentualDesconto;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
