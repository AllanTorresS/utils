package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de conta benefício usuário
 */
@Entity
@Table(name = "CONTA_BENEFICIO_USUARIO")
public class ContaBeneficioUsuario implements IPersistente {

    @Id
    @Column(name = "CD_CONTA_BENEFICIO_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BENEFICIO_USUARIO")
    @SequenceGenerator(name = "SEQ_CONTA_BENEFICIO_USUARIO", sequenceName = "SEQ_CONTA_BENEFICIO_USUARIO", allocationSize = 1)
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

    @OneToMany(mappedBy = "contaBeneficioUsuario", fetch = FetchType.LAZY)
    private List<ContaBeneficio> contasBeneficio;

    @OneToMany(mappedBy = "contaBeneficioUsuario", fetch = FetchType.LAZY)
    private List<OperacaoContaBeneficio> operacoesContaBeneficio;

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

    public List<ContaBeneficio> getContasBeneficio() {
        return contasBeneficio;
    }

    public void setContasBeneficio(List<ContaBeneficio> contasBeneficio) {
        this.contasBeneficio = contasBeneficio;
    }

    public List<OperacaoContaBeneficio> getOperacoesContaBeneficio() {
        return operacoesContaBeneficio;
    }

    public void setOperacoesContaBeneficio(List<OperacaoContaBeneficio> operacoesContaBeneficio) {
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
}