package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.LocalDestinoPadroNfe;
import ipp.aci.boleia.dominio.interfaces.IParametroNotaFiscal;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Column(name= "CD_UNIDADE_LOCAL_DEST_PADRAO")
    private Long unidadeLocalDestinoPadrao;

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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
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

    /**
     * O local destino configurado é no ato do abastecimento?
     * @return true se positivo
     */
    @Transient
    public boolean isDestinoNotaFiscalNoLocalDoAbastecimento(){
        return LocalDestinoPadroNfe.ABASTECIMENTO.getValue()
                .equals(this.getLocalDestino());
    }
}