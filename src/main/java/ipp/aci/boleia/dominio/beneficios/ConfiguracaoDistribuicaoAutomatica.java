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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de configuração de distribuição automática
 */
@Audited
@Entity
@Table(name = "CONFIG_DISTRIBUICAO_AUTO")
public class ConfiguracaoDistribuicaoAutomatica implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 161084160262908392L;

    @Id
    @Column(name = "CD_CONFIG_DISTRIBUICAO_AUTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIG_DISTRIBUICAO_AUTO")
    @SequenceGenerator(name = "SEQ_CONFIG_DISTRIBUICAO_AUTO", sequenceName = "SEQ_CONFIG_DISTRIBUICAO_AUTO", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name="ID_STATUS")
    private Integer status;

    @Column(name = "VA_DIA_DISTRIBUICAO")
    private Integer diaDistribuicao;

    @Column(name = "DT_DISTRIBUICAO")
    private Date dataDistribuicao;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Version
    @Column(name="NO_VERSAO")
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDiaDistribuicao() {
        return diaDistribuicao;
    }

    public void setDiaDistribuicao(Integer diaDistribuicao) {
        this.diaDistribuicao = diaDistribuicao;
    }

    public Date getDataDistribuicao() {
        return dataDistribuicao;
    }

    public void setDataDistribuicao(Date dataDistribuicao) {
        this.dataDistribuicao = dataDistribuicao;
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

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}