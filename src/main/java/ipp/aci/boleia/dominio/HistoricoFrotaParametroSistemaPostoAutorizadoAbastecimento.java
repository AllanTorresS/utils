package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

/**
 * Representa a tabela de Histórico dos Postos Permitidos dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_PV_AUTORI")
public class HistoricoFrotaParametroSistemaPostoAutorizadoAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 7739503034508124428L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_PV_AUTORI")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_PV_AUTORI")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_PV_AUTORI", sequenceName = "SEQ_HIST_FROTA_PARAM_PV_AUTORI", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_PV_AUTORIZ")
    private FrotaParametroSistemaPostoAutorizadoAbastecimento frotaParametroSistemaPostoAutorizadoAbastecimento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Column(name = "VA_MAXIMO_LITROS")
    private BigDecimal maximoLitros;

    @Column(name = "VA_MAXIMO_VALOR")
    private BigDecimal maximoValor;

    @NotNull
    @Column(name = "ID_AUTORIZADO")
    private Boolean autorizado;

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

    public FrotaParametroSistemaPostoAutorizadoAbastecimento getFrotaParametroSistemaPostoAutorizadoAbastecimento() {
        return frotaParametroSistemaPostoAutorizadoAbastecimento;
    }

    public void setFrotaParametroSistemaPostoAutorizadoAbastecimento(FrotaParametroSistemaPostoAutorizadoAbastecimento frotaParametroSistemaPostoAutorizadoAbastecimento) {
        this.frotaParametroSistemaPostoAutorizadoAbastecimento = frotaParametroSistemaPostoAutorizadoAbastecimento;
    }

    public FrotaParametroSistema getFrotaParametroSistema() {
        return frotaParametroSistema;
    }

    public void setFrotaParametroSistema(FrotaParametroSistema frotaParametroSistema) {
        this.frotaParametroSistema = frotaParametroSistema;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public BigDecimal getMaximoLitros() {
        return maximoLitros;
    }

    public void setMaximoLitros(BigDecimal maximoLitros) {
        this.maximoLitros = maximoLitros;
    }

    public BigDecimal getMaximoValor() {
        return maximoValor;
    }

    public void setMaximoValor(BigDecimal maximoValor) {
        this.maximoValor = maximoValor;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
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
        return getFrotaParametroSistema().getFrotas();


    }
}

