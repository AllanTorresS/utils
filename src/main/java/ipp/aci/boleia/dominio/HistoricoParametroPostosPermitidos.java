package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa um registro de histórico da autorização de abastecimento em um pv
 * a partir do parametro de uso Postos Permitidos.
 *
 * @author pedro.silva
 */
@Entity
@Audited
@Table(name = "HISTORICO_POSTOS_PERMIT")
public class HistoricoParametroPostosPermitidos implements IPersistente {

    private static final long serialVersionUID = -6228526028840399134L;

    @Id
    @Column(name = "CD_HISTORICO_POSTOS_PERMIT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_POSTOS_PERMIT")
    @SequenceGenerator(name = "SEQ_HISTORICO_POSTOS_PERMIT", sequenceName = "SEQ_HISTORICO_POSTOS_PERMIT", allocationSize = 1)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;
    @NotNull
    @Column(name = "ID_TIPO_RESTRICAO")
    private Integer tipoRestricao;
    @Column(name = "VA_MAXIMO_PERMITIDO")
    private BigDecimal valorMaximoRestricao;
    @NotNull
    @Column(name = "DT_AUTORIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAutorizacao;
    @Column(name = "DT_DESAUTORIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDesautorizacao;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_HIST_PARAM_USO")
    private HistoricoParametroUso historicoParametroUso;
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

    public HistoricoParametroUso getHistoricoParametroUso() {
        return historicoParametroUso;
    }

    public void setHistoricoParametroUso(HistoricoParametroUso historicoParametroUso) {
        this.historicoParametroUso = historicoParametroUso;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
