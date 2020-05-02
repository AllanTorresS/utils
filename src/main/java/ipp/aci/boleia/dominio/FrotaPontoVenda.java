package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusBloqueio;
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
import java.util.List;

/**
 * Representa a tabela de relacao entre Frota e Ponto de Venda
 */
@Entity
@Audited
@Table(name = "FROTA_PTOV")
public class FrotaPontoVenda implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = 2740533603845751255L;

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

    @NotAudited
    @Formula(StatusBloqueio.DECODE_FORMULA)
    private String statusBloqueioConvertido;

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

}