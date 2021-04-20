package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IFluxoAbastecimentoConfig;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Audited
@Entity
@Table(name = "FLUXO_ABAS_MOTORISTA")
public class FluxoAbastecimentoMotoristaConfig implements IPersistente, IPertenceFrota, IPertenceMotorista, IExclusaoLogica, IFluxoAbastecimentoConfig {

    @Id
    @Column(name = "CD_FLUXO_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FLUXO_ABAS_MOTORISTA")
    @SequenceGenerator(name = "SEQ_FLUXO_ABAS_MOTORISTA", sequenceName = "SEQ_FLUXO_ABAS_MOTORISTA", allocationSize = 1)
    private Long id;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

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

    @Override
    public List<Frota> getFrotas() {
        return motorista != null ?  motorista.getFrotas() : Collections.emptyList();
    }

    @Override
    public Boolean getExigirTelaHodometroHorimetro() {
        return exigirTelaHodometroHorimetro;
    }

    public void setExigirTelaHodometroHorimetro(Boolean exigirTelaHodometroHorimetro) {
        this.exigirTelaHodometroHorimetro = exigirTelaHodometroHorimetro;
    }

    @Override
    public Boolean getExigirFotoHodometroHorimetro() {
        return exigirFotoHodometroHorimetro;
    }

    public void setExigirFotoHodometroHorimetro(Boolean exigirFotoHodometroHorimetro) {
        this.exigirFotoHodometroHorimetro = exigirFotoHodometroHorimetro;
    }

    @Override
    public Boolean getExigirLeituraAutoHodometroHorimetro() {
        return exigirLeituraAutoHodometroHorimetro;
    }

    public void setExigirLeituraAutoHodometroHorimetro(Boolean exigirLeituraAutoHodometroHorimetro) {
        this.exigirLeituraAutoHodometroHorimetro = exigirLeituraAutoHodometroHorimetro;
    }

    @Override
    public Boolean getExigirPosto() {
        return exigirPosto;
    }

    public void setExigirPosto(Boolean exigirPosto) {
        this.exigirPosto = exigirPosto;
    }

    @Override
    public Boolean getExigirCombustivel() {
        return exigirCombustivel;
    }

    public void setExigirCombustivel(Boolean exigirCombustivel) {
        this.exigirCombustivel = exigirCombustivel;
    }

    @Override
    public Boolean getExigirLitragem() {
        return exigirLitragem;
    }

    public void setExigirLitragem(Boolean exigirLitragem) {
        this.exigirLitragem = exigirLitragem;
    }

    @Override
    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    @Override
    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
