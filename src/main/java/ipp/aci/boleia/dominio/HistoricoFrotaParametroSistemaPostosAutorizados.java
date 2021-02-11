package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Histórico dos Postos Permitidos dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_POST_PERM")
public class HistoricoFrotaParametroSistemaPostosAutorizados implements IPersistente, IPertenceFrota {

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
    @Column(name = "DT_AUTORIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAutorizacao;

    @Column(name = "DT_DESAUTORIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDesautorizacao;


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

    public Date getDataAutorizacao() {
        return dataAutorizacao;
    }

    public void setDataAutorizacao(Date dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    public Date getDataDesautorizacao() {
        return dataDesautorizacao;
    }

    public void setDataDesautorizacao(Date dataDesautorizacao) {
        this.dataDesautorizacao = dataDesautorizacao;
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

    public BigDecimal getValorMaximoLitros() {
        return valorMaximoLitros;
    }

    public void setValorMaximoLitros(BigDecimal valorMaximoLitros) {
        this.valorMaximoLitros = valorMaximoLitros;
    }
}

