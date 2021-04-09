package ipp.aci.boleia.dominio.historico;

import ipp.aci.boleia.dominio.ParametroNotaFiscal;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.enums.LocalDestinoPadroNfe;
import ipp.aci.boleia.dominio.interfaces.IParametroNotaFiscal;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

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
import javax.persistence.Transient;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

/**
 * Entidade que mantém o histórico de {@link ParametroNotaFiscal}.
 */
@Entity
@Table(name = "HISTORICO_PARAM_NF")
public class HistoricoParametroNotaFiscal implements IPersistente, IParametroNotaFiscal {

    @Id
    @Column(name = "CD_HISTORICO_PARAM_NF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_PARAM_NF")
    @SequenceGenerator(name = "SEQ_HISTORICO_PARAM_NF", sequenceName = "SEQ_HISTORICO_PARAM_NF", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PARAMETRO_NF")
    private ParametroNotaFiscal parametroNotaFiscal;

    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;

    @Column(name = "ID_LOCAL_DESTINO")
    private Integer localDestino;

    @Column(name = "ID_TIPO_AGRUPAMENTO")
    private Integer nfTipoAgrupamento;

    @Column(name = "ID_COMB_PROD_SERV")
    private Boolean separarPorCombustivelProdutoServico;

    @Column(name = "NM_DADOS")
    private String dadosAdicionais;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "CD_UNIDADE_LOCAL_DEST_PADRAO")
    private Unidade unidadeLocalDestinoPadrao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy= "parametroNotaFiscal", orphanRemoval = true)
    private List<HistoricoParametroNotaFiscalUf> parametroNotaFiscalUfs;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public ParametroNotaFiscal getParametroNotaFiscal() {
        return parametroNotaFiscal;
    }

    public void setParametroNotaFiscal(ParametroNotaFiscal parametroNotaFiscal) {
        this.parametroNotaFiscal = parametroNotaFiscal;
    }

    @Override
    public Integer getLocalDestino() {
        return localDestino;
    }

    @Override
    public void setLocalDestino(Integer localDestino) {
        this.localDestino = localDestino;
    }

    @Override
    public Integer getNfTipoAgrupamento() {
        return nfTipoAgrupamento;
    }

    @Override
    public void setNfTipoAgrupamento(Integer nfTipoAgrupamento) {
        this.nfTipoAgrupamento = nfTipoAgrupamento;
    }

    @Override
    public String getDadosAdicionais() {
        return dadosAdicionais;
    }

    @Override
    public void setDadosAdicionais(String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @Override
    public Boolean getSepararPorCombustivelProdutoServico() {
        return separarPorCombustivelProdutoServico;
    }

    @Override
    public void setSepararPorCombustivelProdutoServico(Boolean separarPorCombustivelProdutoServico) {
        this.separarPorCombustivelProdutoServico = separarPorCombustivelProdutoServico;
    }

    @Override
    public Unidade getUnidadeLocalDestinoPadrao() {
        return unidadeLocalDestinoPadrao;
    }

    @Override
    public void setUnidadeLocalDestinoPadrao(Unidade unidadeLocalDestinoPadrao) {
        this.unidadeLocalDestinoPadrao = unidadeLocalDestinoPadrao;
    }

    public List<HistoricoParametroNotaFiscalUf> getParametroNotaFiscalUfs() {
        return parametroNotaFiscalUfs;
    }

    public void setParametroNotaFiscalUfs(List<HistoricoParametroNotaFiscalUf> parametroNotaFiscalUfs) {
        this.parametroNotaFiscalUfs = parametroNotaFiscalUfs;
    }

    @Transient
    @Override
    public boolean isDestinoNotaFiscalNoLocalDoAbastecimento(){
        return LocalDestinoPadroNfe.ABASTECIMENTO.getValue()
                .equals(this.getLocalDestino());
    }

    @Transient
    @Override
    public boolean isParametroDeFrotaUnidadesSemExigenciaNotaFiscal(){
        return this.localDestino == null && this.separarPorCombustivelProdutoServico == null
                && nfTipoAgrupamento == null && this.dadosAdicionais == null && this.unidadeLocalDestinoPadrao == null;
    }

    @Override
    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }
}
