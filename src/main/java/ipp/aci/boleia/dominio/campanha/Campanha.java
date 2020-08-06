package ipp.aci.boleia.dominio.campanha;

import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusCampanha;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representa a tabela de Campanha
 */
@Audited
@Entity
@Table(name = "CAMPANHA", schema = "BOLEIA_CAMPANHA_SCHEMA")
public class Campanha implements IPersistente, IExclusaoLogica, Cloneable {

    private static final long serialVersionUID = 72078305247273678L;

    @Id
    @Column(name = "CD_CAMPANHA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAMPANHA")
    @SequenceGenerator(name = "SEQ_CAMPANHA", sequenceName = "BOLEIA_CAMPANHA_SCHEMA.SEQ_CAMPANHA", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NM_CAMPANHA")
    private String nome;

    @NotNull
    @Column(name = "DT_INICIO")
    private Date dataInicio;

    @NotNull
    @Column(name = "DT_FIM")
    private Date dataFim;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotNull
    @Column(name = "ID_TIPO_DESCONTO")
    private Integer tipoDesconto;

    @NotNull
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO")
    private BigDecimal valorTotal;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "DT_ATUALIZACAO")
    private Date dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_CRIADOR")
    private Usuario usuarioCriador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_APROVADOR")
    private Usuario usuarioAprovador;

    @Column(name = "DS_JUSTIFICATIVA_REJEICAO")
    private String ultimaJustificativaRejeicao;

    @Column(name = "DT_FIM_ORIGINAL")
    private Date dataFimOriginal;

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @AuditJoinTable(name = "CAMPANHA_COMBUSTIVEL_AUD")
    @JoinTable(name = "CAMPANHA_COMBUSTIVEL", schema = "BOLEIA_CAMPANHA_SCHEMA", joinColumns = {@JoinColumn(name = "CD_CAMPANHA")}, inverseJoinColumns = {@JoinColumn(name = "CD_TIPO_COMBUSTIVEL")})
    private List<TipoCombustivel> combustiveis;

    //O mapeamento como EAGER se deve ao fato de as respectivas condições sempre serem necessárias ao se buscar uma campanha
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campanha", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CondicaoCampanha> condicoes;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTipoDesconto() {
        return tipoDesconto;
    }

    public void setTipoDesconto(Integer tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public void setUsuarioCriador(Usuario usuarioCriador) {
        this.usuarioCriador = usuarioCriador;
    }

    public Usuario getUsuarioAprovador() {
        return usuarioAprovador;
    }

    public void setUsuarioAprovador(Usuario usuarioAprovador) {
        this.usuarioAprovador = usuarioAprovador;
    }

    public List<TipoCombustivel> getCombustiveis() {
        return combustiveis;
    }

    public void setCombustiveis(List<TipoCombustivel> combustiveis) {
        this.combustiveis = combustiveis;
    }

    public Set<CondicaoCampanha> getCondicoes() {
        return condicoes;
    }

    public void setCondicoes(Set<CondicaoCampanha> condicoes) {
        if(this.condicoes != null) {
            this.condicoes.clear();
        }
        else  {
            this.condicoes = new HashSet<>();
        }
        this.condicoes.addAll(condicoes);
    }

    public String getUltimaJustificativaRejeicao() {
        return ultimaJustificativaRejeicao;
    }

    public void setUltimaJustificativaRejeicao(String ultimaJustificativaRejeicao) {
        this.ultimaJustificativaRejeicao = ultimaJustificativaRejeicao;
    }

    public Date getDataFimOriginal() {
        return dataFimOriginal;
    }

    public void setDataFimOriginal(Date dataFimOriginal) {
        this.dataFimOriginal = dataFimOriginal;
    }

    /**
     * Método que compara duas campanhas, segundo a regra de negócio de equivalências entre campanhas
     * @param campanha objeto da campanha a ser comparado com a instância atual do objeto
     * @return se os objetos são ou não iguais
     */
    public boolean comparar(Campanha campanha) {
        if (this == campanha) {
            return true;
        }
        if (campanha == null){
            return false;
        }

        Boolean datasSobrePostas = UtilitarioCalculoData.dataEmIntervalo(this.getDataInicio(), campanha.getDataInicio(), campanha.getDataFim())
                || UtilitarioCalculoData.dataEmIntervalo(this.getDataFim(), campanha.getDataInicio(), campanha.getDataFim())
                || UtilitarioCalculoData.dataEmIntervalo(campanha.getDataInicio(), this.getDataInicio(), this.getDataFim())
                || UtilitarioCalculoData.dataEmIntervalo(campanha.getDataFim(), this.getDataInicio(), this.getDataFim());

        Boolean combustiveisContidos = campanha.getCombustiveis().stream().allMatch(c -> this.getCombustiveis().stream().filter(c2 -> c2.getId() == c.getId()).count() > 0)
                && this.getCombustiveis().stream().allMatch(c -> campanha.getCombustiveis().stream().filter(c2 -> c2.getId() == c.getId()).count() > 0);

        Boolean quantidadeCondicoes = this.getCondicoes().size() == campanha.getCondicoes().size();

        Boolean condicoesIguais = this.condicoes.stream().allMatch(c -> campanha.getCondicoes().stream().filter(c2 -> c.comparar(c2)).count() > 0)
                || campanha.condicoes.stream().allMatch(c -> this.getCondicoes().stream().filter(c2 -> c.comparar(c2)).count() > 0);

        return datasSobrePostas && combustiveisContidos && quantidadeCondicoes && condicoesIguais;
    }

    /**
     * Copia por completo uma entidade de campanha
     * @return a cópia
     */
    public Campanha clone() {
        Campanha copia = new Campanha();

        List<TipoCombustivel> combustiveisCopia = new ArrayList<>();

        copia.setNome(this.getNome());
        copia.setDataInicio(this.getDataInicio());
        copia.setDataFim(this.getDataFim());
        copia.setStatus(this.getStatus());
        copia.setTipoDesconto(this.getTipoDesconto());
        copia.setUsuarioCriador(this.getUsuarioCriador());
        copia.setValorTotal(this.getValorTotal());
        if (this.getCondicoes() != null) {
            copia.setCondicoes(this.getCondicoes().stream().map(c -> c.clone()).collect(Collectors.toSet()));
            copia.getCondicoes().forEach(c -> c.setCampanha(copia));
        }
        for (TipoCombustivel combustivel : this.getCombustiveis()) {
            combustiveisCopia.add(combustivel);
        }
        copia.setCombustiveis(combustiveisCopia);
        return copia;
    }

    /**
     * Obtém o status calculado de uma campanha
     *
     * @param dataAmbiente A data corrente
     * @return O status correspondente da campanha
     */
    public StatusCampanha obterStatus(Date dataAmbiente) {

        if (dataAmbiente.after(this.getDataFim())) {
            return StatusCampanha.ENCERRADO;
        }

        StatusCampanha status;

        if (StatusCampanha.ATIVO.getValue().equals(this.getStatus())) {
            if (dataAmbiente.before(this.getDataInicio())) {
                status = StatusCampanha.AGENDADO;
            } else {
                status = StatusCampanha.ATIVO;
            }
        } else {
            status = StatusCampanha.obterPorValor(this.getStatus());
        }

        return status;
    }
}
