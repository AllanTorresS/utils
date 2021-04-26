package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Representa a ligacao entra a tabela de Rota e Postos
 */
@Entity
@Audited
@Table(name = "POSTO_INT_TIPO_COMB_PRECO")
public class PostoInternoTipoCombustivelPreco implements IPersistente {

    private static final long serialVersionUID = -2992523418587803244L;

    @Id
    @Column(name = "CD_POSTO_INT_TIPO_COMB_PRECO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_POSTO_INT_TIPO_COMB_PRECO")
    @SequenceGenerator(name = "SEQ_POSTO_INT_TIPO_COMB_PRECO", sequenceName = "SEQ_POSTO_INT_TIPO_COMB_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CD_FROTA")
    private Long idFrota;

    @Column(name = "CD_UNIDADE")
    private Long idUnidade;

    @NotNull
    @Column(name = "CD_TIPO_COMBUSTIVEL")
    private Long idTipoCombustivel;

    @NotNull
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA", insertable = false, updatable = false)
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE", insertable = false, updatable = false)
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL", insertable = false, updatable = false)
    private TipoCombustivel tipoCombustivel;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getIdTipoCombustivel() {
        return idTipoCombustivel;
    }

    public void setIdTipoCombustivel(Long idTipoCombustivel) {
        this.idTipoCombustivel = idTipoCombustivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
