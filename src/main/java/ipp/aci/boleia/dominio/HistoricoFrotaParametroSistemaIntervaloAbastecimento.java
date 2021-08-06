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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Classe que representa o Histórico da configuração de permissão de intervalo de abastecimento por veiculo
 */
@Audited
@Entity
@Table(name = "HIST_FROTA_PARAM_SIS_IN_AB")
public class HistoricoFrotaParametroSistemaIntervaloAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -3949000066189767492L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_SIS_INT_AB")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_SIS_IN_AB")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_SIS_IN_AB", sequenceName = "SEQ_HIST_FROTA_PARAM_SIS_IN_AB", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_INT_AB")
    private FrotaParametroSistemaIntervaloAbastecimento frotaParametroSistemaIntervaloAbastecimento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @Column(name = "VA_MINS_INTRV_ABAST")
    private Integer minutosIntervaloAbastecimento;

    @Column(name = "VA_KM_INTRV_ABAST")
    private Long quilometrosIntervaloAbastecimento;

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

    public FrotaParametroSistemaIntervaloAbastecimento getFrotaParametroSistemaIntervaloAbastecimento() {
        return frotaParametroSistemaIntervaloAbastecimento;
    }

    public void setFrotaParametroSistemaIntervaloAbastecimento(FrotaParametroSistemaIntervaloAbastecimento frotaParametroSistemaIntervaloAbastecimento) {
        this.frotaParametroSistemaIntervaloAbastecimento = frotaParametroSistemaIntervaloAbastecimento;
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

    @Transient
    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistemaIntervaloAbastecimento.getFrotaParametroSistema().getFrotas();
    }

    public Long getQuilometrosIntervaloAbastecimento() {
        return quilometrosIntervaloAbastecimento;
    }

    public void setQuilometrosIntervaloAbastecimento(Long quilometrosIntervaloAbastecimento) {
        this.quilometrosIntervaloAbastecimento = quilometrosIntervaloAbastecimento;
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
