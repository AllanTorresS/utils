package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * Representa a tabela de Parametros do Sistema Consumo para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_CONSUMO")
public class HistoricoFrotaParametroSistemaConsumo implements IPersistente {

    private static final long serialVersionUID = -8744864612379584028L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_CONSUMO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_CONSUMO")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_CONSUMO", sequenceName = "SEQ_HIST_FROTA_PARAM_CONSUMO", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_CONSUMO")
    private FrotaParametroSistemaConsumo frotaParametroSistemaConsumo;

    @NotNull
    @Column(name= "VA_PORCENTAGEM_LIMITE_MINIMO")
    private BigDecimal porcentagemLimiteMinimo;

    @NotNull
    @Column(name= "VA_PORCENTAGEM_LIMITE_MAXIMO")
    private BigDecimal porcentagemLimiteMaximo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaParametroSistemaConsumo getFrotaParametroSistemaConsumo() {
        return frotaParametroSistemaConsumo;
    }

    public void setFrotaParametroSistemaConsumo(FrotaParametroSistemaConsumo frotaParametroSistemaConsumo) {
        this.frotaParametroSistemaConsumo = frotaParametroSistemaConsumo;
    }

    public BigDecimal getPorcentagemLimiteMinimo() {
        return porcentagemLimiteMinimo;
    }

    public void setPorcentagemLimiteMinimo(BigDecimal porcentagemLimiteMinimo) {
        this.porcentagemLimiteMinimo = porcentagemLimiteMinimo;
    }

    public BigDecimal getPorcentagemLimiteMaximo() {
        return porcentagemLimiteMaximo;
    }

    public void setPorcentagemLimiteMaximo(BigDecimal porcentagemLimiteMaximo) {
        this.porcentagemLimiteMaximo = porcentagemLimiteMaximo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}