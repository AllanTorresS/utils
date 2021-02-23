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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Audited
@Entity
@Table(name = "FLUXO_ABAS_FROTA")
public class FluxoAbastecimentoFrotaConfig implements IPersistente, IPertenceFrota {

    @Id
    @Column(name = "CD_FLUXO_GLOBAL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FLUXO_ABAS_GLOBAL")
    @SequenceGenerator(name = "SEQ_FLUXO_ABAS_GLOBAL", sequenceName = "SEQ_FLUXO_ABAS_GLOBAL", allocationSize = 1)
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

    @NotNull
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

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

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}
