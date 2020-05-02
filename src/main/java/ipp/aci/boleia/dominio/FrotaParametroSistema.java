package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.ParametroSistema;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Representa a tabela de Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "FROTA_PARAM_SIS")
public class FrotaParametroSistema implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 2244410419824752487L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS", sequenceName = "SEQ_FROTA_PARAM_SIS", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "ID_PARAMETRO")
    private Integer parametroSistema;

    @NotNull
    @Column(name = "ID_ATIVO")
    private Boolean ativo;

    @NotNull
    @Column(name = "ID_RESTRITIVO")
    private Boolean restritivo;

    @Column(name = "ID_INTRV_ABAST_TODOS_VEIC")
    private Boolean verificarIntervaloAbastecimentoTodosVeiculos;

    @Column(name = "VA_MINS_INTRV_ABAST_TODOS_VEIC")
    private Integer minutosIntervaloAbastecimentoTodosVeiculos;

    @Column(name = "ID_HODOM_HORIM_TODOS_VEICULOS")
    private Boolean verificarHodometroHorimetroTodosVeiculos;

    @Column(name = "ID_COTA_VEIC_VISIVEL_MOTORISTA")
    private Boolean cotaVeiculoVisivelMotorista;

    @Column(name = "ID_EM_LITROS")
    private Boolean emLitros;

    @Column(name = "ID_COTA_VEIC_POR_ABAS")
    private Boolean cotaVeiculoPorAbastecimento;

    @Column(name = "ID_COTA_VEIC_USAR_SUGESTAO")
    private Boolean cotaVeiculoUsarSugestao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_PRECO_MAX_DATA_ATUALIZACAO")
    private Date precoMaximoDataAtualizacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaProduto> produtos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaHorario> horarios;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaIntervaloAbastecimento> intervalosAbastecimento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaHodometroHorimetro> hodometrosHorimetros;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaPrecoMaximoProduto> precoMaximoProdutos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaPrecoMaximoAbastecimento> precoMaximoAbastecimentos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaPostoAutorizadoAbastecimento> postosAutorizadosAbastecimento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frotaParametroSistema")
    @Valid
    private List<FrotaParametroSistemaProdutoAbastecimento> combustiveisPermitidos;

    @OneToOne(mappedBy = "frotaParametroSistema")
    private FrotaParametroSistemaConsumo consumo;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;


    public FrotaParametroSistema() {
        // necessario para o JPA
    }

    /**
     * Construtor a partir do parametro sistema e da frota
     * @param param o parametro do sistema
     * @param frota a frota
     */
    public FrotaParametroSistema(ParametroSistema param, Frota frota) {
        this.parametroSistema = param.getCodigo();
        this.restritivo = param.isRestritivaPorDefault();
        this.ativo = false;
        this.frota = frota;
    }

	@Override
	public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
    }
    
    /**
     * Obtém valor do atributo Frota
     *
     * @return Valor de Frota
     */
    public Frota getFrota() {
        return frota;
    }
    
    /**
     * Define valor do atributo Frota
     *
     * @param frota Valor do attributo Frota
     */
    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    /**
     * Obtém o codigo do parametro de sistema referenciado
     *
     * @return O codigo do parametro de sistema referenciado
     */
    public Integer getParametroSistema() {
        return parametroSistema;
    }
    
    /**
     * Define o codigo do parametro de sistema referenciado
     *
     * @param parametroSistema O codigo do parametro de sistema referenciado
     */
    public void setParametroSistema(Integer parametroSistema) {
        this.parametroSistema = parametroSistema;
    }

    /**
     * Obtém valor do atributo Ativo
     *
     * @return Valor de Ativo
     */
    public Boolean getAtivo() {
        return ativo;
    }
    
    /**
     * Define valor do atributo Ativo
     *
     * @param ativo Valor do attributo Ativo
     */
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Obtém valor do atributo Restritivo
     *
     * @return Valor de Restritivo
     */
    public Boolean getRestritivo() {
        return restritivo;
    }

    /**
     * Define valor do atributo Restritivo
     *
     * @param restritivo Valor do attributo Restritivo
     */
    public void setRestritivo(Boolean restritivo) {
        this.restritivo = restritivo;
    }   

    public List<FrotaParametroSistemaProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<FrotaParametroSistemaProduto> produtos) {
        this.produtos = produtos;
    }

    public List<FrotaParametroSistemaPrecoMaximoProduto> getPrecoMaximoProdutos() {
        return precoMaximoProdutos;
    }

    public void setPrecoMaximoProdutos(List<FrotaParametroSistemaPrecoMaximoProduto> precoMaximoProdutos) {
        this.precoMaximoProdutos = precoMaximoProdutos;
    }

    public List<FrotaParametroSistemaPrecoMaximoAbastecimento> getPrecoMaximoAbastecimentos() {
        return precoMaximoAbastecimentos;
    }

    public void setPrecoMaximoAbastecimentos(List<FrotaParametroSistemaPrecoMaximoAbastecimento> precoMaximoAbastecimentos) {
        this.precoMaximoAbastecimentos = precoMaximoAbastecimentos;
    }

    public Boolean getCotaVeiculoUsarSugestao() {
        return cotaVeiculoUsarSugestao;
    }

    public void setCotaVeiculoUsarSugestao(Boolean cotaVeiculoUsarSugestao) {
        this.cotaVeiculoUsarSugestao = cotaVeiculoUsarSugestao;
    }

    public List<FrotaParametroSistemaHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<FrotaParametroSistemaHorario> horarios) {
        this.horarios = horarios;
    }

    @Transient
    public boolean isRestritivo() {
        return restritivo != null && restritivo;
    }

    @Transient
    public boolean isAtivo() {
        return ativo != null && ativo;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Date getPrecoMaximoDataAtualizacao() {
        return precoMaximoDataAtualizacao;
    }

    public void setPrecoMaximoDataAtualizacao(Date precoMaximoDataAtualizacao) {
        this.precoMaximoDataAtualizacao = precoMaximoDataAtualizacao;
    }


    public FrotaParametroSistemaConsumo getConsumo() {
        return consumo;
    }

    public void setConsumo(FrotaParametroSistemaConsumo consumo) {
        this.consumo = consumo;
    }

    public List<FrotaParametroSistemaHodometroHorimetro> getHodometrosHorimetros() {
        return hodometrosHorimetros;
    }

    public void setHodometrosHorimetros(List<FrotaParametroSistemaHodometroHorimetro> hodometrosHorimetros) {
        this.hodometrosHorimetros = hodometrosHorimetros;
    }

    public Boolean getVerificarHodometroHorimetroTodosVeiculos() {
        return verificarHodometroHorimetroTodosVeiculos;
    }

    public void setVerificarHodometroHorimetroTodosVeiculos(Boolean verificarHodometroHorimetroTodosVeiculos) {
        this.verificarHodometroHorimetroTodosVeiculos = verificarHodometroHorimetroTodosVeiculos;
    }

    public Boolean getVerificarIntervaloAbastecimentoTodosVeiculos() {
        return verificarIntervaloAbastecimentoTodosVeiculos;
    }

    public void setVerificarIntervaloAbastecimentoTodosVeiculos(Boolean verificarIntervaloAbastecimentoTodosVeiculos) {
        this.verificarIntervaloAbastecimentoTodosVeiculos = verificarIntervaloAbastecimentoTodosVeiculos;
    }

    public Integer getMinutosIntervaloAbastecimentoTodosVeiculos() {
        return minutosIntervaloAbastecimentoTodosVeiculos;
    }

    public void setMinutosIntervaloAbastecimentoTodosVeiculos(Integer minutosIntervaloAbastecimentoTodosVeiculos) {
        this.minutosIntervaloAbastecimentoTodosVeiculos = minutosIntervaloAbastecimentoTodosVeiculos;
    }

    public List<FrotaParametroSistemaIntervaloAbastecimento> getIntervalosAbastecimento() {
        return intervalosAbastecimento;
    }

    public void setIntervalosAbastecimento(List<FrotaParametroSistemaIntervaloAbastecimento> intervalosAbastecimento) {
        this.intervalosAbastecimento = intervalosAbastecimento;
    }

    @Transient
    public List<FrotaParametroSistemaProduto> getProdutosOrdenados() {
        List<FrotaParametroSistemaProduto> produtosOrdenados = getProdutos();
        produtosOrdenados.sort((p1, p2) -> {
            if (p1.getProduto().isOutros() == p2.getProduto().isOutros()) {
                return p1.getProduto().getNome().toLowerCase().compareTo(p2.getProduto().getNome().toLowerCase());
            }
            return p1.getProduto().isOutros() ? 1 : -1;
        });
        return produtosOrdenados;
    }

    public Boolean getCotaVeiculoVisivelMotorista() {
        return cotaVeiculoVisivelMotorista;
    }

    public void setCotaVeiculoVisivelMotorista(Boolean cotaVeiculoVisivelMotorista) {
        this.cotaVeiculoVisivelMotorista = cotaVeiculoVisivelMotorista;
    }

    public Boolean getEmLitros() {
        return emLitros;
    }

    public void setEmLitros(Boolean emLitros) {
        this.emLitros = emLitros;
    }

    public Boolean getCotaVeiculoPorAbastecimento() {
        return cotaVeiculoPorAbastecimento;
    }

    public void setCotaVeiculoPorAbastecimento(Boolean cotaVeiculoPorAbastecimento) {
        this.cotaVeiculoPorAbastecimento = cotaVeiculoPorAbastecimento;
    }

    public List<FrotaParametroSistemaPostoAutorizadoAbastecimento> getPostosAutorizadosAbastecimento() {
        return postosAutorizadosAbastecimento;
    }

    public void setPostosAutorizadosAbastecimento(List<FrotaParametroSistemaPostoAutorizadoAbastecimento> postosAutorizadosAbastecimento) {
        this.postosAutorizadosAbastecimento = postosAutorizadosAbastecimento;
    }

    public List<FrotaParametroSistemaProdutoAbastecimento> getCombustiveisPermitidos() {
        return combustiveisPermitidos;
    }

    public void setCombustiveisPermitidos(List<FrotaParametroSistemaProdutoAbastecimento> combustiveisPermitidos) {
        this.combustiveisPermitidos = combustiveisPermitidos;
    }

    @Transient
    public List<FrotaParametroSistemaPrecoMaximoProduto> getProdutosOrdenadosPrecoMaximo() {
        List<FrotaParametroSistemaPrecoMaximoProduto> produtosOrdenados = getPrecoMaximoProdutos();
        produtosOrdenados.sort((p1, p2) -> {
            if (p1.getProduto().isOutros() == p2.getProduto().isOutros()) {
                return p1.getProduto().getNome().toLowerCase().compareTo(p2.getProduto().getNome().toLowerCase());
            }
            return p1.getProduto().isOutros() ? 1 : -1;
        });
        return produtosOrdenados;
    }

    /**
     * Metodo que retorna a lista de preco maximo de abastecimento para os combustiveis definidos
     * no parametro de sistema da frota
     * @return a lista de precos dos combustiveis
     */
    @Transient
    public List<FrotaParametroSistemaPrecoMaximoAbastecimento> getCombustiveisOrdenadosPrecoMaximo() {
        List<FrotaParametroSistemaPrecoMaximoAbastecimento> combustiveisOrdenados = getPrecoMaximoAbastecimentos();
        combustiveisOrdenados.sort(Comparator.comparing(p -> p.getCombustivel().getDescricao().toLowerCase()));
        return combustiveisOrdenados;
    }

    /**
     * Metodo que retorna um posto autorizado para abastecimento de acordo com o parametro do sistema
     * @param idPontoVenda o id do ponto de venda a ser buscado
     * @return a entidade que representa o PV autorizado para abastecimento
     */
    @Transient
    public FrotaParametroSistemaPostoAutorizadoAbastecimento getAutorizacaoAbastecimentoPosto(Long idPontoVenda) {
        FrotaParametroSistemaPostoAutorizadoAbastecimento postoAutorizado = null;
        if(postosAutorizadosAbastecimento != null) {
            Optional<FrotaParametroSistemaPostoAutorizadoAbastecimento> optional = getPostosAutorizadosAbastecimento()
                    .stream()
                    .filter(p -> p.getPontoVenda().getId().equals(idPontoVenda))
                    .findFirst();
            postoAutorizado = optional.orElse(null);
        }
        return postoAutorizado;
    }
}