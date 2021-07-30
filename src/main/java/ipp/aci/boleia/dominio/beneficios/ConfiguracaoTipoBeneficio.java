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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de conta benefício
 */
@Audited
@Entity
@Table(name = "CONTA_BENEFICIO")
public class ConfiguracaoTipoBeneficio implements IPersistente {

    @Id
    @Column(name = "CD_CONTA_BENEFICIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BENEFICIO")
    @SequenceGenerator(name = "SEQ_CONTA_BENEFICIO", sequenceName = "SEQ_CONTA_BENEFICIO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTA_BENEFICIARIO")
    private ContaBeneficiario contaBeneficiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BENEFICIO")
    private TipoBeneficio tipoBeneficio;

    @NotNull
    @Column(name="ID_CONFIGURADO")
    private Boolean beneficioConfigurado;

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

    public ContaBeneficiario getContaBeneficiario() {
        return contaBeneficiario;
    }

    public void setContaBeneficiario(ContaBeneficiario contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
    }

    public TipoBeneficio getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(TipoBeneficio tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public Boolean getBeneficioConfigurado() {
        return beneficioConfigurado;
    }

    public void setBeneficioConfigurado(Boolean beneficioConfigurado) {
        this.beneficioConfigurado = beneficioConfigurado;
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