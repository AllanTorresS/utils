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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Preco Maximo de abastecimento dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "FROTA_PARAM_SIS_PRECO_ABST")
public class FrotaParametroSistemaPrecoMaximoAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 7462453144013449568L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS_PRECO_ABST")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_PRECO_ABST")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_PRECO_ABST", sequenceName = "SEQ_FROTA_PARAM_SIS_PRECO_ABST", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema != null ? frotaParametroSistema.getFrotas() : null;
    }


    public BigDecimal getQuantidadePermitida() {
        return quantidadePermitida;
    }

    public void setQuantidadePermitida(BigDecimal quantidadePermitida) {
        this.quantidadePermitida = quantidadePermitida;
    }
}