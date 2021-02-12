package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.envers.Audited;
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
@Table(name = "HIST_FROTA_PARAM_POSTO_AUT")
public class HistoricoFrotaParametroSistemaPostosAutorizados implements IPersistente {

    private static final long serialVersionUID = 7739503034508124428L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_POSTO_AUT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_POSTO_AUT")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_POSTO_AUT", sequenceName = "SEQ_HIST_FROTA_PARAM_POSTO_AUT", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_POSTO_AUT")
    private FrotaParametroSistemaPostoAutorizadoAbastecimento frotaParametroSistemaPostoAutorizadoAbastecimento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @NotNull
    @Column(name = "ID_TIPO_RESTRICAO")
    private Integer tipoRestricao;

    @Column(name = "VA_MAXIMO_PERMITIDO")
    private BigDecimal valorMaximoRestricao;

    @Column(name = "VA_MAXIMO_LITROS")
    private BigDecimal valorMaximoLitros;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @NotNull
    @Column(name = "ID_AUTORIZADO")
    private Boolean autorizado;

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

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Integer getTipoRestricao() {
        return tipoRestricao;
    }

    public void setTipoRestricao(Integer tipoRestricao) {
        this.tipoRestricao = tipoRestricao;
    }

    public BigDecimal getValorMaximoRestricao() {
        return valorMaximoRestricao;
    }

    public void setValorMaximoRestricao(BigDecimal valorMaximoRestricao) {
        this.valorMaximoRestricao = valorMaximoRestricao;
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

    public BigDecimal getValorMaximoLitros() {
        return valorMaximoLitros;
    }

    public void setValorMaximoLitros(BigDecimal valorMaximoLitros) {
        this.valorMaximoLitros = valorMaximoLitros;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }
}

