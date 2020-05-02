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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Classe que representa a configuração de permissão de intervalo de abastecimento por veiculo
 */
@Audited
@Entity
@Table(name = "FROTA_PARAM_SIS_INTV_ABAST")
public class FrotaParametroSistemaIntervaloAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -3949000066189767492L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS_INTV_ABAST")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_INTV_ABAST")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_INTV_ABAST", sequenceName = "SEQ_FROTA_PARAM_SIS_INTV_ABAST", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @Column(name = "VA_MINS_INTRV_ABAST")
    private Integer minutosIntervaloAbastecimento;

    @NotNull
    @Column(name = "ID_ATIVO")
    private Boolean ativo;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaParametroSistema getFrotaParametroSistema() {
        return frotaParametroSistema;
    }

    public void setFrotaParametroSistema(FrotaParametroSistema frotaParametroSistema) {
        this.frotaParametroSistema = frotaParametroSistema;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getMinutosIntervaloAbastecimento() {
        return minutosIntervaloAbastecimento;
    }

    public void setMinutosIntervaloAbastecimento(Integer minutosIntervaloAbastecimento) {
        this.minutosIntervaloAbastecimento = minutosIntervaloAbastecimento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema.getFrotas();
    }

}
