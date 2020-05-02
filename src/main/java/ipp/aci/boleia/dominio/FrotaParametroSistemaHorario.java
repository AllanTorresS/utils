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
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Classe que representa a configuração de permissão de horário.
 *
 */
@Audited
@Entity
@Table(name = "FROTA_PARAM_SIS_HORARIO")
public class FrotaParametroSistemaHorario implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 6539006428277343378L;

    @Id
    @Column(name = "CD_FROTA_PARAM_HORARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_HORARIO")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_HORARIO", sequenceName = "SEQ_FROTA_PARAM_SIS_HORARIO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @AssertTrue
    public boolean isItervaloValido() {
        return de != null && ate != null && de < ate;
    }

}
