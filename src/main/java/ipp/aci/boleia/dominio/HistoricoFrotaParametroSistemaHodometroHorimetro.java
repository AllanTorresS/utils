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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Histórico do Hodometro ou Horimetro dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_SIS_HD_HR")
public class HistoricoFrotaParametroSistemaHodometroHorimetro implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 7739503034508124428L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_SIS_HD_HR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_SIS_HD_HR")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_SIS_HD_HR", sequenceName = "SEQ_HIST_FROTA_PARAM_SIS_HD_HR", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_HODM_HORM")
    private FrotaParametroSistemaHodometroHorimetro frotaParametroSistemaHodometroHorimetro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @NotNull
    @Column(name = "ID_ATIVO")
    private Boolean ativo;

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

    public FrotaParametroSistemaHodometroHorimetro getFrotaParametroSistemaHodometroHorimetro() {
        return frotaParametroSistemaHodometroHorimetro;
    }

    public void setFrotaParametroSistemaHodometroHorimetro(FrotaParametroSistemaHodometroHorimetro frotaParametroSistemaHodometroHorimetro) {
        this.frotaParametroSistemaHodometroHorimetro = frotaParametroSistemaHodometroHorimetro;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistemaHodometroHorimetro.getFrotaParametroSistema() != null ? frotaParametroSistemaHodometroHorimetro.getFrotaParametroSistema().getFrotas() : null;
    }

}