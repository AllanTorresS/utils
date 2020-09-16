package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.JoinFormula;

/**
 * Representa a tabela de relacao entre Frota e Ponto de Venda
 */
@Entity
@Audited
@Table(name = "FROTA_PTOV")
public class FrotaPontoVenda implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = 2740533603845751255L;
    
    /**
     * Query que busca o histórico mais recente para popular o atributo.
     */
    private static final String HISTORICO_FORMULA = "( SELECT * FROM (SELECT h.CD_HISTORICO_FROTA_PTOV FROM BOLEIA_SCHEMA.HISTORICO_FROTA_PTOV h WHERE h.CD_FROTA_PTOV = CD_FROTA_PTOV ORDER BY h.DT_HISTORICO DESC) WHERE ROWNUM <= 1 )";

    @Id
    @Column(name = "CD_FROTA_PTOV")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PTOV")
    @SequenceGenerator(name = "SEQ_FROTA_PTOV", sequenceName = "SEQ_FROTA_PTOV", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private Negociacao negociacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaPtov")
    private List<TransacaoConsolidada> transacoesConsolidadas;

    @Column(name = "ID_BLOQUEADO")
    private Integer statusBloqueio;
    
    @Column(name = "ID_STATUS")
    private Integer statusVinculo;
    
    @Column(name = "DS_JUSTIFICATIVA_VINCULO")
    private String justificativaVinculo;

    @NotAudited
    @Formula(StatusBloqueio.DECODE_FORMULA)
    private String statusBloqueioConvertido;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name ="DT_ATUALIZACAO")
    private Date dataAtualizacao;
    
    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinFormula(HISTORICO_FORMULA)
    private HistoricoFrotaPontoVenda ultimoHistorico;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Negociacao getNegociacao() {
        return negociacao;
    }

    public void setNegociacao(Negociacao negociacao) {
        this.negociacao = negociacao;
    }

    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return transacoesConsolidadas;
    }

    public void setTransacoesConsolidadas(List<TransacaoConsolidada> transacoesConsolidadas) {
        this.transacoesConsolidadas = transacoesConsolidadas;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoVenda);
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public Integer getStatusBloqueio() {
        return statusBloqueio;
    }

    public void setStatusBloqueio(Integer statusBloqueio) {
        this.statusBloqueio = statusBloqueio;
    }

    public String getStatusBloqueioConvertido() {
        return statusBloqueioConvertido;
    }

    public void setStatusBloqueioConvertido(String statusBloqueioConvertido) {
        this.statusBloqueioConvertido = statusBloqueioConvertido;
    }

    /**
     * Informa se a relação {@link FrotaPontoVenda} está bloqueada.
     *
     * @return true, caso esteja bloqueado.
     */
    @Transient
    public boolean isBloqueado() {
        return statusBloqueio != null && StatusBloqueio.obterPorValor(statusBloqueio).isBloqueado();
    }

    public Integer getStatusVinculo() {
        return statusVinculo;
    }

    public void setStatusVinculo(Integer statusVinculo) {
        this.statusVinculo = statusVinculo;
    }
    
    /**
     * Informa se a relacao {@link FrotaPontoVenda} esta ativa.
     *
     * @return true, caso esteja ativa.
     */
    @Transient
    public boolean isVinculoAtivo(){
        return StatusVinculoFrotaPontoVenda.ATIVO.equals(StatusVinculoFrotaPontoVenda.obterPorValor(this.statusVinculo));
    }
    
    /**
     * Informa se o {@link PontoDeVenda} deve estar visivel para a {@link Frota} 
     * 
     * @return true, caso deva estar visivel.
     */
    @Transient
    public boolean isVisivelParaFrota(){
        return this.isVinculoAtivo() || !this.getPontoVenda().isVisivelApenasParaFrotasVinculadas();
    }

    public String getJustificativaVinculo() {
        return justificativaVinculo;
    }

    public void setJustificativaVinculo(String justificativaVinculo) {
        this.justificativaVinculo = justificativaVinculo;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public HistoricoFrotaPontoVenda getUltimoHistorico() {
        return ultimoHistorico;
    }

    public void setUltimoHistorico(HistoricoFrotaPontoVenda ultimoHistorico) {
        this.ultimoHistorico = ultimoHistorico;
    }
}