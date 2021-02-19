package ipp.aci.boleia.dominio;

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

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FLUXO_ABAS_MOTORISTA")
public class HistoricoFluxoAbastecimentoMotoristaConfig implements IPersistente {

    @Id
    @Column(name = "CD_HIST_FLUXO_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FLUXO_ABAS_MOTORISTA")
    @SequenceGenerator(name = "SEQ_HIST_FLUXO_ABAS_MOTORISTA", sequenceName = "SEQ_HIST_FLUXO_ABAS_MOTORISTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CD_FLUXO_MOTORISTA")
    private FluxoAbastecimentoMotoristaConfig fluxoAbastecimento;

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
    @Column(name="ID_LITRAGEM")
    private Boolean exigirLitragem;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

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

    public FluxoAbastecimentoMotoristaConfig getFluxoAbastecimento() {
        return fluxoAbastecimento;
    }

    public void setFluxoAbastecimento(FluxoAbastecimentoMotoristaConfig fluxoAbastecimento) {
        this.fluxoAbastecimento = fluxoAbastecimento;
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

    public Boolean getExigirLitragem() {
        return exigirLitragem;
    }

    public void setExigirLitragem(Boolean exigirLitragem) {
        this.exigirLitragem = exigirLitragem;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
