package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de Prazo de Geração de Cobrança.
 */
@Entity
@Audited
@Table(name = "PRAZO_GER_COBRANCA")
public class PrazoGeracaoCobranca implements IPersistente {

    private static final long serialVersionUID = 1121494160971663664L;

    @Id
    @Column(name = "CD_PRAZO_GER_COBRANCA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRAZO_GER_COBRANCA")
    @SequenceGenerator(name = "SEQ_PRAZO_GER_COBRANCA", sequenceName = "SEQ_PRAZO_GER_COBRANCA", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "QT_PRAZO_PGTO_MIN")
    private Long prazoPagamentoMin;

    @Column(name = "QT_PRAZO_PGTO_MAX")
    private Long prazoPagamentoMax;

    @NotNull
    @Column(name = "QT_PRAZO_GER_COBRANCA")
    private Long prazoGeracaoCobranca;

    @NotNull
    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioVigencia;

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

    public Long getPrazoPagamentoMin() {
        return prazoPagamentoMin;
    }

    public void setPrazoPagamentoMin(Long prazoPagamentoMin) {
        this.prazoPagamentoMin = prazoPagamentoMin;
    }

    public Long getPrazoPagamentoMax() {
        return prazoPagamentoMax;
    }

    public void setPrazoPagamentoMax(Long prazoPagamentoMax) {
        this.prazoPagamentoMax = prazoPagamentoMax;
    }

    public Long getPrazoGeracaoCobranca() {
        return prazoGeracaoCobranca;
    }

    public void setPrazoGeracaoCobranca(Long prazoGeracaoCobranca) {
        this.prazoGeracaoCobranca = prazoGeracaoCobranca;
    }

    public Date getDataInicioVigencia() {
        return dataInicioVigencia;
    }

    public void setDataInicioVigencia(Date dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}