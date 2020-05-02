package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.enums.TipoItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Item Autorizacao Pagamento
 */
@Entity
@Audited
@Table(name = "ITEM_AUTORIZACAO_PAGAMENTO")
public class ItemAutorizacaoPagamento implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = -2682441382521773020L;

    @Id
    @Column(name = "CD_ITEM_AUTORIZACAO_PAGAMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_AUTORIZACAO_PAGAMENTO")
    @SequenceGenerator(name = "SEQ_ITEM_AUTORIZACAO_PAGAMENTO", sequenceName = "SEQ_ITEM_AUTORIZACAO_PAGAMENTO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamento;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @AuditJoinTable(name = "ITEM_AUTORIZACAO_CAMPANHA_AUD")
    @JoinTable(name = "ITEM_AUTORIZACAO_CAMPANHA", schema = "BOLEIA_CAMPANHA_SCHEMA", joinColumns = {@JoinColumn(name = "CD_ITEM_AUTORIZACAO_PAGAMENTO")}, inverseJoinColumns = {@JoinColumn(name = "CD_CAMPANHA")})
    private List<Campanha> campanhasElegiveis;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
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

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return autorizacaoPagamento!=null ? autorizacaoPagamento.getPontosDeVenda() : Collections.emptyList();
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return autorizacaoPagamento!=null ? autorizacaoPagamento.getFrotas() : Collections.emptyList();
    }

    /**
     * Método utilitário para obter o código do combustível ({@link TipoCombustivel}) ou do
     * produto ({@link Produto}) a que o item se refere.
     *
     * @return código do combustível ou do produto, de acordo com o tipo do item
     */
    @Transient
    public Long getCodigoCombustivelOuProduto() {
        if (isAbastecimento()) {
            return getCombustivel() != null ? getCombustivel().getId() : null;
        }
        return getProduto() != null ? getProduto().getId() : null;
    }

    /**
     * Retorna o número de casas decimais que devem ser utilizadas para representar o campo <b>quantidade</b>.
     * Esse número leva em consideração se o item é um Combustível ou um Produto/Serviço.
     *
     * @return número de casas decimais que devem ser utilizadas para representar o campo <b>quantidade</b>
     */
    @Transient
    public Integer getPrecisaoCampoQuantidade() {
        return isAbastecimento() ? 3 : (getProduto().isLitragem() ? 3 : 2);
    }

    /**
     * Indica se o item é um Abastecimento ou Produto/Serviço
     *
     * @return indica se o item é um Abastecimento
     */
    @Transient
    public boolean isAbastecimento() {
        return TipoItemAutorizacaoPagamento.ABASTECIMENTO.getValue().equals(getTipoItem());
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

    public List<Campanha> getCampanhasElegiveis() {
        return campanhasElegiveis;
    }

    public void setCampanhasElegiveis(List<Campanha> campanhasElegiveis) {
        this.campanhasElegiveis = campanhasElegiveis;
    }
}
