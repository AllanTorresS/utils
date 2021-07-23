package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IFluxoAbastecimentoConfig;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
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

/**
 * Entidade que guarda o histórico do fluxo de abastecimento da frota.
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FLUXO_ABAS_FROTA")
public class HistoricoFluxoAbastecimentoFrotaConfig implements IPersistente, IFluxoAbastecimentoConfig {

    private static final long serialVersionUID = -5552633964580822448L;

    @Id
    @Column(name = "CD_HIST_FLUXO_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FLUXO_ABAS_FROTA")
    @SequenceGenerator(name = "SEQ_HIST_FLUXO_ABAS_FROTA", sequenceName = "SEQ_HIST_FLUXO_ABAS_FROTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CD_FLUXO_FROTA")
    private FluxoAbastecimentoFrotaConfig fluxoAbastecimento;

    @Column(name="ID_TELA_HODO_HORI")
    private Boolean exigirTelaHodometroHorimetro;
    @Column(name="ID_FOTO_HODO_HORI")
    private Boolean exigirFotoHodometroHorimetro;
    @Column(name="ID_LEITURA_AUTO_HODO_HORI")
    private Boolean exigirLeituraAutoHodometroHorimetro;
    @Column(name="ID_POSTO")
    private Boolean exigirPosto;
    @Column(name="ID_COMBUSTIVEL")
    private Boolean exigirCombustivel;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    /**
     * Construtor default.
     */
    public HistoricoFluxoAbastecimentoFrotaConfig() {}

    public HistoricoFluxoAbastecimentoFrotaConfig(FluxoAbastecimentoFrotaConfig fluxo, Usuario usuario, Date dataAlteracao) {
        this.fluxoAbastecimento = fluxo;
        this.frota = fluxo.getFrota();
        this.dataAlteracao = dataAlteracao;

        this.exigirTelaHodometroHorimetro = fluxo.getExigirTelaHodometroHorimetro();
        this.exigirFotoHodometroHorimetro = fluxo.getExigirFotoHodometroHorimetro();
        this.exigirLeituraAutoHodometroHorimetro = fluxo.getExigirLeituraAutoHodometroHorimetro();
        this.exigirPosto = fluxo.getExigirPosto();
        this.exigirCombustivel = fluxo.getExigirCombustivel();

        this.usuario = usuario;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExigirTelaHodometroHorimetro() {
        return exigirTelaHodometroHorimetro;
    }

    public void setExigirTelaHodometroHorimetro(Boolean exigirTelaHodometroHorimetro) {
        this.exigirTelaHodometroHorimetro = exigirTelaHodometroHorimetro;
    }

    public Boolean getExigirFotoHodometroHorimetro() {
        return exigirFotoHodometroHorimetro;
    }

    public void setExigirFotoHodometroHorimetro(Boolean exigirFotoHodometroHorimetro) {
        this.exigirFotoHodometroHorimetro = exigirFotoHodometroHorimetro;
    }

    public Boolean getExigirLeituraAutoHodometroHorimetro() {
        return exigirLeituraAutoHodometroHorimetro;
    }

    public void setExigirLeituraAutoHodometroHorimetro(Boolean exigirLeituraAutoHodometroHorimetro) {
        this.exigirLeituraAutoHodometroHorimetro = exigirLeituraAutoHodometroHorimetro;
    }

    public Boolean getExigirPosto() {
        return exigirPosto;
    }

    public void setExigirPosto(Boolean exigirPosto) {
        this.exigirPosto = exigirPosto;
    }

    public Boolean getExigirCombustivel() {
        return exigirCombustivel;
    }

    public void setExigirCombustivel(Boolean exigirCombustivel) {
        this.exigirCombustivel = exigirCombustivel;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public FluxoAbastecimentoFrotaConfig getFluxoAbastecimento() {
        return fluxoAbastecimento;
    }

    public void setFluxoAbastecimento(FluxoAbastecimentoFrotaConfig fluxoAbastecimento) {
        this.fluxoAbastecimento = fluxoAbastecimento;
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
