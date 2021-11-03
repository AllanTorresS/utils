package ipp.aci.boleia.dominio.beneficios;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entidade relacionada a conta de benefícios pertencente a uma frota.
 *
 * @author pedro.silva
 */
@Audited
@Entity
@Table(name = "CONTA_BENEFICIOS_FROTA")
public class ContaBeneficiosFrota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -6218448967352815566L;

    @Id
    @Column(name = "CD_CONTA_BENEFICIOS_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BENEFICIOS_FROTA")
    @SequenceGenerator(name = "SEQ_CONTA_BENEFICIOS_FROTA", sequenceName = "SEQ_CONTA_BENEFICIOS_FROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "VA_SALDO")
    private BigDecimal saldo;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contaBeneficiosFrota")
    private List<PedidoCreditoBeneficios> pedidoCreditoBeneficios;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<PedidoCreditoBeneficios> getPedidoCreditoBeneficios() {
        return pedidoCreditoBeneficios;
    }

    public void setPedidoCreditoBeneficios(List<PedidoCreditoBeneficios> pedidoCreditoBeneficios) {
        this.pedidoCreditoBeneficios = pedidoCreditoBeneficios;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}
