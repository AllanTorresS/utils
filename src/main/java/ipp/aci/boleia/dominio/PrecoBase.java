package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Preco Posto do PV
 */
@Entity
@Audited
@Table(name = "PTOV_PRECO")
public class PrecoBase implements IPersistente, IPertenceRevendedor {

    private static final long serialVersionUID = -4837716452171377600L;

    @Id
    @Column(name = "CD_PTOV_PRECO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PTOV_PRECO")
    @SequenceGenerator(name = "SEQ_PTOV_PRECO", sequenceName = "SEQ_PTOV_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MICROMERCADO_PRECO")
    private PrecoMicromercado precoMicromercado;

    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @Column(name = "VA_PRECO_ANTERIOR")
    private BigDecimal precoAnterior;

    @Column(name = "VA_PRECO_NEGOCIADO")
    private BigDecimal precoNegociado;

    @Size(max=1000)
    @Column(name = "DS_JUSTIFICATIVA")
    private String justificativa;

    @Column(name = "DT_SOLICITACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "DT_AGENDAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;

    @NotAudited
    @Formula(StatusAlteracaoPrecoPosto.DECODE_FORMULA)
    private String statusConvertido;

    @NotAudited
    @Formula("CD_PTOV")
    private Long idPontoVenda;

    @Column(name = "DT_FIM_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimVigencia;

    @Column(name = "ID_INVALIDO")
    private Boolean invalido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PrecoMicromercado getPrecoMicromercado() {
        return precoMicromercado;
    }

    public void setPrecoMicromercado(PrecoMicromercado precoMicromercado) {
        this.precoMicromercado = precoMicromercado;
    }

    public BigDecimal getPrecoAnterior() {
        return precoAnterior;
    }

    public void setPrecoAnterior(BigDecimal precoAnterior) { this.precoAnterior = precoAnterior; }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusConvertido() { return statusConvertido; }

    public void setStatusConvertido(String statusConvertido) { this.statusConvertido = statusConvertido; }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public BigDecimal getPrecoNegociado() {
        return precoNegociado;
    }

    public void setPrecoNegociado(BigDecimal precoNegociado) {
        this.precoNegociado = precoNegociado;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Date     getDataAgendamento() { return dataAgendamento; }

    public void setDataAgendamento(Date dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public Date getDataFimVigencia() { return dataFimVigencia; }

    public void setDataFimVigencia(Date dataFimVigencia) { this.dataFimVigencia = dataFimVigencia; }

    public Boolean getInvalido() {
        return invalido != null ? invalido : false;
    }

    public void setInvalido(Boolean invalido) {
        this.invalido = invalido;
    }


    /**
     * Metodo para tirar o preco de vigencia
     *
     * @param dataFimVigencia data de fim da vigencia
     */
    public void sairDeVigencia(Date dataFimVigencia) {
        this.setStatus(StatusAlteracaoPrecoPosto.EXPIRADO.getValue());
        this.setDataFimVigencia(dataFimVigencia);
    }

    /**
     * Entra em vigencia apos aceitação
     *
     * @param dataAtualizacao data de atualizacao do mesmo
     * @param aceitarNegociacao deve ser atualizado preco com a negociacao
     */
    public void entrarEmVigencia(Date dataAtualizacao, Boolean aceitarNegociacao) {
        if(aceitarNegociacao) {
            this.setPrecoAnterior(this.getPreco());
            this.setPreco(this.getPrecoNegociado());
        }
        this.setDataAtualizacao(dataAtualizacao);
        this.setPrecoNegociado(null);
        this.setDataSolicitacao(null);
        this.setJustificativa(null);
        this.setStatus(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
    }

    /**
     * Seta o status e preco de um preco negociado de acordo com o usuário logado
     *
     * @param usuario Usuario logado na hora da execução dessa função
     * @param preco O preco que deve ser setado caso não requeira aceite da Revenda
     * @param requerAceiteRevenda Se requer aceite da revenda para setar um novo preco negociado
     */
    public void preenchePrecoBasePorPerfil(Usuario usuario, BigDecimal preco, boolean requerAceiteRevenda) {
        if(requerAceiteRevenda && !usuario.isRevendedor()) {
            this.setStatus(StatusAlteracaoPrecoPosto.ACEITE_PENDENTE_REVENDA.getValue());
            this.setPrecoNegociado(preco);
        } else {
            this.setStatus(StatusAlteracaoPrecoPosto.VIGENTE.getValue());
            this.setPreco(preco);
        }
    }

    /**
     * Propoe a renegociacao de um preco pelo revendedor ou usuario interno
     *
     * @param precoSolicitado pelo revendedor ou usuário interno
     * @param dataSolicitacao da alteracao de preco
     * @param status da alteracao de preco
     */
    public void renegociar(BigDecimal precoSolicitado, Date dataSolicitacao, Integer status) {
        this.setPrecoNegociado(precoSolicitado);
        this.setDataSolicitacao(dataSolicitacao);
        this.setStatus(status);
    }

    /**
     * Exclui a alteracao de preco base solicitada
     *
     * @param status do preco base
     */
    public void excluirAlteracao(Integer status) {
        this.setPrecoNegociado(null);
        this.setDataSolicitacao(null);
        this.setStatus(status);
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Long getIdPontoVenda() {
        return idPontoVenda;
    }

    public void setIdPontoVenda(Long idPontoVenda) {
        this.idPontoVenda = idPontoVenda;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoVenda);
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /**
     * Verifica se o preco base informado se refere ao mesmo
     * ponto de venda e ao mesmo preco de referencia
     *
     * @param outro O outro preco base
     * @return true caso possuam o mesmo PV e o mesmo preco de referencia
     */
    public boolean possuiMesmoPontoVendaCombustivel(PrecoBase outro) {
        return
                this.getPontoVenda() != null
                        && outro.getPontoVenda() != null
                        && this.getPrecoMicromercado() != null
                        && outro.getPrecoMicromercado() != null
                        && this.getPontoVenda().getId().equals(outro.getPontoVenda().getId())
                        && this.getPrecoMicromercado().getId().equals(outro.getPrecoMicromercado().getId());
    }
}
