package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.LocalDestinoPadroNfe;
import ipp.aci.boleia.dominio.interfaces.IParametroNotaFiscal;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Parametrização de Nota Fiscal
 */
@Audited
@Entity
@Table(name = "PARAMETRO_NF")
public class ParametroNotaFiscal implements IPersistente, IParametroNotaFiscal {

    @Id
    @Column(name = "CD_PARAMETRO_NF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAM_NF")
    @SequenceGenerator(name = "SEQ_PARAM_NF", sequenceName = "SEQ_PARAM_NF", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Column(name = "ID_COMB_PROD_SERV")
    private Boolean separarPorCombustivelProdutoServico;

    @Column(name = "ID_LOCAL_DESTINO")
    private Integer localDestino;

    @Column(name = "ID_TIPO_AGRUPAMENTO")
    private Integer nfTipoAgrupamento;

    @Size(max = 250)
    @Column(name = "NM_DADOS")
    private String dadosAdicionais;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "CD_UNIDADE_LOCAL_DEST_PADRAO")
    private Unidade unidadeLocalDestinoPadrao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy= "parametroNotaFiscal", orphanRemoval = true)
    private List<ParametroNotaFiscalUf> parametroNotaFiscalUfs;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;

    /**
     * Construtor padrão
     */
    public ParametroNotaFiscal() {
        parametroNotaFiscalUfs = new ArrayList();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
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

    public List<ParametroNotaFiscalUf> getParametroNotaFiscalUfs() {
        return parametroNotaFiscalUfs;
    }

    public void setParametroNotaFiscalUfs(List<ParametroNotaFiscalUf> parametroNotaFiscalUfs) {
        this.parametroNotaFiscalUfs = parametroNotaFiscalUfs;
    }

    @Override
    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
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
}