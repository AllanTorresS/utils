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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de conta benefício usuário
 */
@Audited
@Entity
@Table(name = "CONTA_BENEFICIARIO")
public class ContaBeneficiario implements IPersistente {

    private static final long serialVersionUID = -3754261212845174880L;

    @Id
    @Column(name = "CD_CONTA_BENEFICIARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BENEFICIARIO")
    @SequenceGenerator(name = "SEQ_CONTA_BENEFICIARIO", sequenceName = "SEQ_CONTA_BENEFICIARIO", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BENEFICIARIO")
    private Beneficiario beneficiario;

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

    @Column(name = "DT_ENCERRAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEncerramento;

    @OneToMany(mappedBy = "contaBeneficiario", fetch = FetchType.LAZY)
    private List<ConfiguracaoTipoBeneficio> configuracoesTipoBeneficio;

    @OneToMany(mappedBy = "contaBeneficiario", fetch = FetchType.LAZY)
    private List<OperacaoContaBeneficiario> operacoesContaBeneficio;

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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public List<ConfiguracaoTipoBeneficio> getConfiguracoesTipoBeneficio() {
        return configuracoesTipoBeneficio;
    }

    public void setConfiguracoesTipoBeneficio(List<ConfiguracaoTipoBeneficio> configuracoesTipoBeneficio) {
        this.configuracoesTipoBeneficio = configuracoesTipoBeneficio;
    }

    public List<OperacaoContaBeneficiario> getOperacoesContaBeneficio() {
        return operacoesContaBeneficio;
    }

    public void setOperacoesContaBeneficio(List<OperacaoContaBeneficiario> operacoesContaBeneficio) {
        this.operacoesContaBeneficio = operacoesContaBeneficio;
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

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}