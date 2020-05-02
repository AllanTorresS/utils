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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Preco do Micromercado
 */
@Entity
@Audited
@Table(name = "MICROMERCADO_PRECO")
public class PrecoMicromercado implements IPersistente {

    private static final long serialVersionUID = 5920259136439581642L;

    @Id
    @Column(name = "CD_MICROMERCADO_PRECO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MICROMERCADO_PRECO")
    @SequenceGenerator(name = "SEQ_MICROMERCADO_PRECO", sequenceName = "SEQ_MICROMERCADO_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MICROMERCADO")
    private Micromercado micromercado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @NotNull
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @Column(name = "VA_PRECO_ANTERIOR")
    private BigDecimal precoAnterior;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Micromercado getMicromercado() {
        return micromercado;
    }

    public void setMicromercado(Micromercado micromercado) {
        this.micromercado = micromercado;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public BigDecimal getPrecoAnterior() {
        return precoAnterior;
    }

    public void setPrecoAnterior(BigDecimal precoAnterior) {
        this.precoAnterior = precoAnterior;
    }

    /**
     * Verifica se o preco micromercado informado se refere ao mesmo
     * micromercado e ao mesmo tipo de combustivel
     *
     * @param outro O outro perco micromercado
     * @return true caso possuam o mesmo micromercado e o mesmo tipo combustivel
     */
    public boolean possuiMesmoMicromercadoTipoCombustivel(PrecoMicromercado outro) {
        return
            this.getMicromercado() != null
            && outro.getMicromercado() != null
            && this.getTipoCombustivel() != null
            && outro.getTipoCombustivel() != null
            && this.getMicromercado().getId().equals(outro.getMicromercado().getId())
            && this.getTipoCombustivel().getId().equals(outro.getTipoCombustivel().getId());
    }
}
