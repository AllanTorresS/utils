package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de de Historico para Preco Maximo de abastecimento dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_SIS_PC_AB")
public class HistoricoFrotaParametroSistemaPrecoMaximoAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 7462453144013449568L;

    @Id
    @Column(name = "CD_HIS_FROTA_PARAM_SIS_PC_AB")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_SIS_PC_AB")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_SIS_PC_AB", sequenceName = "SEQ_HIST_FROTA_PARAM_SIS_PC_AB", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_PRECO_ABST")
    private FrotaParametroSistemaPrecoMaximoAbastecimento frotaParametroSistemaPrecoMaximoAbastecimento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel combustivel;

    @Column(name = "VA_PRECO_PERMITIDO")
    private BigDecimal precoPermitido;

    @Column(name = "VA_PRECO_PRATICADO")
    private BigDecimal precoPraticado;

    @Column(name = "VA_QUANTIDADE_PERMITIDA")
    private BigDecimal quantidadePermitida;

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

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistemaPrecoMaximoAbastecimento.getFrotaParametroSistema().getFrotas();
    }

    public FrotaParametroSistemaPrecoMaximoAbastecimento getFrotaParametroSistemaPrecoMaximoAbastecimento() {
        return frotaParametroSistemaPrecoMaximoAbastecimento;
    }

    public void setFrotaParametroSistemaPrecoMaximoAbastecimento(FrotaParametroSistemaPrecoMaximoAbastecimento frotaParametroSistemaPrecoMaximoAbastecimento) {
        this.frotaParametroSistemaPrecoMaximoAbastecimento = frotaParametroSistemaPrecoMaximoAbastecimento;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public BigDecimal getPrecoPermitido() {
        return precoPermitido;
    }

    public void setPrecoPermitido(BigDecimal precoPermitido) {
        this.precoPermitido = precoPermitido;
    }

    public BigDecimal getPrecoPraticado() {
        return precoPraticado;
    }

    public void setPrecoPraticado(BigDecimal precoPraticado) {
        this.precoPraticado = precoPraticado;
    }

    public BigDecimal getQuantidadePermitida() {
        return quantidadePermitida;
    }

    public void setQuantidadePermitida(BigDecimal quantidadePermitida) {
        this.quantidadePermitida = quantidadePermitida;
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