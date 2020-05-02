package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entidade que mantém as informações de ajuste de cobrança.
 */
@Audited
@Entity
@Table(name = "AJUSTE_COBRANCA")
public class AjusteCobranca implements IPersistente {

    private static final long serialVersionUID = -4627182934132175922L;

    @Id
    @Column(name = "CD_AJUSTE_COBRANCA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AJUSTE_COBRANCA")
    @SequenceGenerator(name = "SEQ_AJUSTE_COBRANCA", sequenceName = "SEQ_AJUSTE_COBRANCA", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "VR_TOTAL_AJUSTE")
    private BigDecimal valorAjuste;

    @Column(name = "DT_VENC_AJUSTE")
    private Date dataVencimentoAjuste;

    @NotNull
    @Size(max = 200)
    @Column(name = "DS_JUSTIFICATIVA")
    private String justificativa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ajusteCobranca")
    private List<ItemAjusteCobranca> itensAjuste;

    @NotNull
    @Column(name = "DT_AJUSTE")
    private Date dataAjuste;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA")
    private Cobranca cobranca;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

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

    public BigDecimal getValorAjuste() {
        return valorAjuste;
    }

    public void setValorAjuste(BigDecimal valorAjuste) {
        this.valorAjuste = valorAjuste;
    }

    public Date getDataVencimentoAjuste() {
        return dataVencimentoAjuste;
    }

    public void setDataVencimentoAjuste(Date dataVencimentoAjuste) {
        this.dataVencimentoAjuste = dataVencimentoAjuste;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public List<ItemAjusteCobranca> getItensAjuste() {
        return itensAjuste;
    }

    public void setItensAjuste(List<ItemAjusteCobranca> itensAjuste) {
        this.itensAjuste = itensAjuste;
    }

    public Date getDataAjuste() {
        return dataAjuste;
    }

    public void setDataAjuste(Date dataAjuste) {
        this.dataAjuste = dataAjuste;
    }

    @Transient
    public String getDataAjusteFormatada() {
        return dataAjuste != null ? UtilitarioFormatacaoData.formatarDataCurta(dataAjuste) : null;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    public boolean isDesconto() {
        return valorAjuste.compareTo(BigDecimal.ZERO) < 0;
    }

    @Transient
    public boolean isAcrescimo() {
        return valorAjuste.compareTo(BigDecimal.ZERO) > 0;
    }
}
