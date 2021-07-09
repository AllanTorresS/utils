package ipp.aci.boleia.dominio.beneficios;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de distribuição automática
 */
@Audited
@Entity
@Table(name = "DISTRIBUICAO_AUTOMATICA")
public class DistribuicaoAutomatica implements IPersistente {

    @Id
    @Column(name = "CD_DISTRIBUICAO_AUTOMATICA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISTRIBUICAO_AUTOMATICA")
    @SequenceGenerator(name = "SEQ_DISTRIBUICAO_AUTOMATICA", sequenceName = "SEQ_DISTRIBUICAO_AUTOMATICA", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BENEFICIARIO")
    private Beneficiario beneficiario;

    @Column(name = "VA_Beneficio")
    private BigDecimal valorBeneficio;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

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

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public BigDecimal getValorBeneficio() {
        return valorBeneficio;
    }

    public void setValorBeneficio(BigDecimal valorBeneficio) {
        this.valorBeneficio = valorBeneficio;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}