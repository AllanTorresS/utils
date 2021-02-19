package ipp.aci.boleia.dominio.historico;

import ipp.aci.boleia.dominio.ParametroNotaFiscal;
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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entidade que mantém o histórico de {@link ParametroNotaFiscal}.
 */
@Entity
@Table(name = "HISTORICO_PARAM_NF")
public class HistoricoParametroNotaFiscal implements IPersistente {

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

    @Column(name= "CD_UNIDADE_LOCAL_DEST_PADRAO")
    private Long unidadeLocalDestinoPadrao;

    public Long getId() {
        return id;
    }

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

    public Integer getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(Integer localDestino) {
        this.localDestino = localDestino;
    }

    public Integer getNfTipoAgrupamento() {
        return nfTipoAgrupamento;
    }

    public void setNfTipoAgrupamento(Integer nfTipoAgrupamento) {
        this.nfTipoAgrupamento = nfTipoAgrupamento;
    }

    public String getDadosAdicionais() {
        return dadosAdicionais;
    }

    public void setDadosAdicionais(String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    public Boolean getSepararPorCombustivelProdutoServico() {
        return separarPorCombustivelProdutoServico;
    }

    public void setSepararPorCombustivelProdutoServico(Boolean separarPorCombustivelProdutoServico) {
        this.separarPorCombustivelProdutoServico = separarPorCombustivelProdutoServico;
    }

    public Long getUnidadeLocalDestinoPadrao() {
        return unidadeLocalDestinoPadrao;
    }

    public void setUnidadeLocalDestinoPadrao(Long unidadeLocalDestinoPadrao) {
        this.unidadeLocalDestinoPadrao = unidadeLocalDestinoPadrao;
    }
}
