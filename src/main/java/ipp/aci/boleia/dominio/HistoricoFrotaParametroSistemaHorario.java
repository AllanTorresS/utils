package ipp.aci.boleia.dominio;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa a configuração de permissão de horário.
 *
 */
@Audited
@Entity
@Table(name = "HIST_FROTA_PARAM_SIS_HORA")
public class HistoricoFrotaParametroSistemaHorario implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 6539006428277343378L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_HORA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_SIS_HORA")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_SIS_HORA", sequenceName = "SEQ_HIST_FROTA_PARAM_SIS_HORA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_HORARIO")
    private FrotaParametroSistemaHorario frotaParametroSistemaHorario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_VEICULO")
    private TipoVeiculo tipoVeiculo;

    @NotNull
    @Column(name = "ID_DIA_SEMANA")
    @Min(value = 1)
    @Max(value = 7)
    private Integer diaSemana;

    @NotNull
    @Column(name = "ID_PERMITIDO")
    private Boolean permitido;

    @NotNull
    @Column(name = "ID_DE")
    @Min(value = 0)
    @Max(value = 24)
    private Integer de;

    @NotNull
    @Column(name = "ID_ATE")
    @Min(value = 0)
    @Max(value = 24)
    private Integer ate;

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

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema.getFrotas();
    }

    public FrotaParametroSistema getFrotaParametroSistema() {
        return frotaParametroSistema;
    }

    public void setFrotaParametroSistema(FrotaParametroSistema frotaParametroSistema) {
        this.frotaParametroSistema = frotaParametroSistema;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }

    public boolean isPermitido() {
        return permitido != null && permitido;
    }

    public Integer getDe() {
        return de;
    }

    public void setDe(Integer de) {
        this.de = de;
    }

    public Integer getAte() {
        return ate;
    }

    public void setAte(Integer ate) {
        this.ate = ate;
    }

    public FrotaParametroSistemaHorario getFrotaParametroSistemaHorario() {
        return frotaParametroSistemaHorario;
    }

    public void setFrotaParametroSistemaHorario(FrotaParametroSistemaHorario frotaParametroSistemaHorario) {
        this.frotaParametroSistemaHorario = frotaParametroSistemaHorario;
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
