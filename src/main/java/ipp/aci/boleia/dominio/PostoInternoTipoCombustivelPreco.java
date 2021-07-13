package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

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


/**
 * Representa a ligacao entra a tabela de Postos Interno e Tipo de Combustivel com seu respectivo preço
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
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
