package ipp.aci.boleia.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.beneficios.ConfiguracaoDistribuicaoAutomatica;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import ipp.aci.boleia.dominio.enums.ClassificacaoStatusFrota;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusContrato;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusVigenciaAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.enums.TipoAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Representa a tabela de Frota
 */
@Audited
@Entity
@Table(name = "FROTA")
public class Frota implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final String NVL_NOME_RAZAO = "NVL(NM_FANTASIA, NM_RAZAO_SOCIAL)";
    private static final long serialVersionUID = -7992905602878762082L;

    @Id
    @Column(name = "CD_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA")
    @SequenceGenerator(name = "SEQ_FROTA", sequenceName = "SEQ_FROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @Max(99999999999999L)
    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(NVL_NOME_RAZAO)
    private String nomeRazaoFrota;

    @NotAudited
    @Formula(StatusFrota.DECODE_FORMULA)
    private String statusConvertido;

    @NotNull
    @Size(max=250)
    @Column(name = "NM_RAZAO_SOCIAL")
    private String razaoSocial;

    @Size(max=250)
    @Column(name = "NM_FANTASIA")
    private String nomeFantasia;

    @Max(999999999999999L)
    @Column(name = "NO_INSCR_ESTADUAL")
    private Long inscricaoEstadual;

    @Max(999999999999999L)
    @Column(name = "NO_INSCR_MUNICIPAL")
    private Long inscricaoMunicipal;

    @Max(99999999)
    @NotNull
    @Column(name = "NO_CEP")
    private Integer cep;

    @Size(max=150)
    @NotNull
    @Column(name = "DS_END")
    private String logradouro;

    @Max(99999999)
    @Column(name = "NO_END")
    private Integer numero;

    @Size(max=20)
    @Column(name = "DS_COMPL")
    private String complemento;

    @Size(max=150)
    @NotNull
    @Column(name = "NM_BAIRRO")
    private String bairro;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_MUNIC")
    private String municipio;

    @NotNull
    @Size(max=2)
    @Column(name = "SG_UF")
    private String unidadeFederativa;

    /**
     * @deprecated
     * Atributo legado, os assessores responsaveis agora estao dividido em tipos de consultores de negocio diferentes.
     * No entanto o atributo nao pode ser removido pois sera migrado aos poucos.
     */
    @Size(max=250)
    @Column(name = "NM_ASSESSOR_RESP")
    @Deprecated
    private String assessorResponsavel;

    /**
     * @deprecated
     * Atributo legado, os assessores responsaveis agora estao dividido em tipos de consultores de negocio diferentes.
     * No entanto o atributo nao pode ser removido pois sera migrado aos poucos.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_ASSESSOR_RESP")
    @JsonIgnoreProperties("frotasAssessoradas")
    @Deprecated
    private Usuario usuarioAssessorResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USU_CONSULTOR_HUNTER")
    @JsonIgnoreProperties("frotasAssessoradas")
    private Usuario usuarioConsultorHunter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USU_CONSULTOR_FARMER_PESADO")
    @JsonIgnoreProperties("frotasAssessoradas")
    private Usuario usuarioConsultorFarmerPesado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USU_CONSULTOR_FARMER_LEVE")
    @JsonIgnoreProperties("frotasAssessoradas")
    private Usuario usuarioConsultorFarmerLeve;

    @Max(99)
    @NotNull
    @Column(name = "CD_DDD_TEL")
    private Integer dddTelefone;

    @Min(10000000L)
    @Max(999999999L)
    @NotNull
    @Column(name = "NO_TEL")
    private Long telefone;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_EMAIL")
    private String email;

    @NotNull
    @Size(max=250)
    @Column(name = "NM_RESP")
    private String nomeResponsavelFrota;

    @NotNull
    @Max(99999999999L)
    @Column(name = "CD_CPF_RESP")
    private Long cpfResponsavelFrota;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_CARGO_RESP")
    private String cargoResponsavelFrota;

    @Max(99)
    @NotNull
    @Column(name = "CD_DDD_TEL_RESP")
    private Integer dddTelefoneResponsavelFrota;

    @Min(10000000L)
    @Max(999999999L)
    @NotNull
    @Column(name = "NO_TEL_RESP")
    private Long telefoneResponsavelFrota;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_EMAIL_RESP")
    private String emailResponsavelFrota;


    @Size(max=250)
    @Column(name = "NM_DONO")
    private String nomeDonoFrota;

    @Max(99999999999L)
    @Column(name = "CD_CPF_DONO")
    private Long cpfDonoFrota;

    @Size(max=250)
    @Column(name = "DS_CARGO_DONO")
    private String cargoDonoFrota;

    @Max(99)
    @Column(name = "CD_DDD_TEL_DONO")
    private Integer dddTelefoneDonoFrota;

    @Min(10000000L)
    @Max(999999999L)
    @Column(name = "NO_TEL_DONO")
    private Long telefoneDonoFrota;

    @Size(max=250)
    @Column(name = "DS_EMAIL_DONO")
    private String emailDonoFrota;

    @Column(name="CD_FX_QTD_VEIC_PESADOS")
    private Integer faixaQtdVeicPesados;

    @Column(name="CD_FX_QTD_VEIC_LEVES")
    private Integer faixaQtdVeicLeves;

    @Max(999999999999999L)
    @Column(name="VO_ESTIM_DIESEL")
    private Long volumeEstimadoDiesel;

    @Max(999999999999999L)
    @Column(name="VO_ESTIM_CICLO_OTTO")
    private Long volumeEstimadoCicloOtto;

    @NotNull
    @Column(name="CD_MOD_PGTO")
    private Integer modoPagamento;

    @NotAudited
    @Formula(ModalidadePagamento.DECODE_FORMULA)
    private String modoPagamentoConvertido;

    @Column(name="CD_PORTE")
    private Integer porte;

    @Column(name="CD_SEGTO_ATUACAO")
    private Integer segmentoAtuacao;

    @Column(name="CD_STATUS_CNTR")
    private Integer statusContrato;

    @NotAudited
    @Formula(StatusContrato.DECODE_FORMULA)
    private String statusContratoConvertido;

    @Column(name="DT_INI_CNTR")
    private Date inicioContrato;

    @Column(name="QT_PRAZO_CNTR")
    private Integer prazoContrato;

    @Column(name="DT_HABIL")
    private Date dataHabilitacao;

    @Column(name="DT_SALDO_ZERADO_DESDE")
    private Date dataSaldoZerado;

    @Column(name = "ID_ACORDO_ESPECIAL")
    private Boolean permiteAcordoEspecial;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "CD_MUNIC_IBGE")
    private String codigoIBGE;

    @Column(name = "CD_BEN_FISCAL")
    @Size(max = 3)
    private String codCatBeneficioFiscal;

    @Column(name = "CD_FROTA_JDE_INT")
    private Integer numeroJdeInterno;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<GrupoOperacional> gruposOperacionais;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Veiculo> veiculos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Motorista> motoristas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Unidade> unidades;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<FrotaPontoVenda> negociacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PARAM_CICLO")
    private ParametroCiclo parametroCiclo;

    @OneToOne(mappedBy = "frota")
    private SaldoFrota saldo;

    @OneToMany(mappedBy = "frota")
    private List<ApiToken> apiTokens;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_POSTO_INTERNO")
    private Boolean postoInterno;

    @Column(name = "NO_SEQ_JDE")
    private Long numeroSequencialJde;

    @OneToMany(mappedBy = "frota")
    private List<FrotaParametroSistema> parametrosSistema;

    @Column(name="ID_SEM_NOTA_FISCAL")
    private Boolean semNotaFiscal;

    @Column(name="ID_GERENCIA_NF_AGENDADA")
    private Boolean gerenciaNfAgendada;

    @Column(name="ID_GERENCIA_NF")
    private Boolean gerenciaNf;

    @Column(name = "DT_ACEITE_TERMOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceiteTermos;

    @Column(name="ID_PRIMEIRA_COMPRA")
    private Boolean primeiraCompra;

    @Column(name="ID_FROTA_LEVE")
    private Boolean frotaLeve;

    @Column(name="ID_ROTEIRIZADOR_INTELIGENTE")
    private Boolean roteirizadorInteligente;

    @Column(name="ID_EXIBIR_DESCONTO_TOTAL")
    private Boolean exibirDesconto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    private List<EmpresaAgregada> empresasAgregadas;

    @AuditJoinTable(name = "FROTA_PERMISSAO_AUD")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "FROTA_PERMISSAO",
            joinColumns = @JoinColumn(name = "CD_FROTA"),
            inverseJoinColumns = @JoinColumn(name = "CD_PERMISSAO"))
    private List<Permissao> permissoes;

    @Column(name="DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name="DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Column(name="DS_CONNECTCTA_TOKEN")
    private String connectCTAToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_NOVO_PARAM_CICLO")
    private ParametroCiclo novoParametroCiclo;

    @Column(name = "DT_ALTERACAO_CICLO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracaoCiclo;

    @OneToOne(mappedBy = "frota")
    private CondicoesComerciais condicoesComerciais;

    @OneToOne(mappedBy = "frota")
    private SituacaoConectCar situacaoConectCar;

    @OneToOne(mappedBy = "frota")
    private Lead lead;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    private List<PostoInternoTipoCombustivelPreco> postoInternoTipoCombustivelPreco;

    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitude;

    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitude;

    @OneToOne(mappedBy = "frota")
    private ParametroNotaFiscal parametroNotaFiscal;

    @Column(name = "ID_LEMBRAR_PARAMETRIZACAO_NF")
    private Boolean lembrarParametrizacaoNf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    private List<MotivoAlteracaoStatusFrota> motivosAlteracaoStatus;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "frota")
    private ConfiguracaoDistribuicaoAutomatica configuracaoDistribuicaoAutomatica;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frota")
    private List<Beneficiario> beneficiarios;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "frota")
    private ContaBeneficiosFrota contaBeneficiosFrota;

    @NotAudited
    @Formula("(SELECT NVL(COUNT(0), 0) FROM BOLEIA_SCHEMA.TAG_CONECTCAR T WHERE T.CD_FROTA = CD_FROTA)")
    private Integer totalTags;

    @NotAudited
    @Formula("(SELECT NVL(COUNT(0), 0) FROM BOLEIA_SCHEMA.TAG_CONECTCAR T WHERE T.CD_FROTA = CD_FROTA AND T.DT_ATIVACAO IS NOT NULL)")
    private Integer totalTagsAtivas;

    /**
     * Construtor default
     */
    public Frota() {
        // construtor default
    }

    /**
     * Construtor a partir do id da frota
     * @param idFrota id da frota
     */
    public Frota(Long idFrota) {
        this.id = idFrota;
    }

    /**
     * Construtor da classe.
     *
     * @param id Identificador da frota
     * @param cnpj CNPJ da frota
     * @param status Identificador do status da frota
     * @param nomeRazaoFrota Nome da frota
     * @param statusConvertido Label do status da frota
     * @param razaoSocial Razão social da frota
     * @param nomeFantasia Nome fantasia da frota
     * @param inscricaoEstadual Inscrição estadual da frota
     * @param inscricaoMunicipal Inscrição municipal da frota
     * @param cep CEP do endereço da frota
     * @param logradouro Logradouro do endereço
     * @param numero Número do endereço
     * @param complemento Complemento do endereço
     * @param bairro Bairro da frota
     * @param municipio Município
     * @param unidadeFederativa Unidade federativa da frota
     * @param assessorResponsavel Assessor responsável pela frota
     * @param usuarioAssessorResponsavel Usuario assessor responsavel pela frota
     * @param dddTelefone DDD do telefone da frota
     * @param telefone Telefone da frota
     * @param email Email da frota
     * @param nomeResponsavelFrota Nome do responsável pela frota
     * @param cpfResponsavelFrota CPF do responsável pela frota
     * @param cargoResponsavelFrota Cargo do responsável pela frota
     * @param dddTelefoneResponsavelFrota DDD do telefone do responsável pela frota
     * @param telefoneResponsavelFrota Telefone do responsável pela frota
     * @param emailResponsavelFrota Email do responsável pela frota
     * @param faixaQtdVeicPesados Faixa da quantidade de veículos pesados que a frota possui
     * @param faixaQtdVeicLeves Faixa da quantidade de veículos leves que a frota possui
     * @param volumeEstimadoDiesel Volume estimado de diesel da frota
     * @param volumeEstimadoCicloOtto Volume estimado de ciclo otto da frota
     * @param modoPagamento Modo de pagamento da frota
     * @param porte Porte da frota
     * @param segmentoAtuacao Segmento de atuação da frota
     * @param statusContrato Status do contrato da frota
     * @param statusContratoConvertido Label do status do contrato
     * @param inicioContrato Data de inicio do contrato
     * @param prazoContrato Prazo do contrato
     * @param dataHabilitacao Data de habilitação da frota
     * @param dataSaldoZerado Data em que o saldo da frota é zerado
     * @param permiteAcordoEspecial Informa se é permitido algum acordo especial
     * @param excluido Informa se a frota esta excluida
     * @param codigoIBGE Código IBGE da frota
     * @param codCatBeneficioFiscal Código de beneficio fiscal da frota
     * @param numeroJdeInterno Identificador da frota no JDE
     * @param gruposOperacionais Grupos operacionais da frota
     * @param veiculos Veículos da frota
     * @param motoristas Lista de motoristas da frota
     * @param unidades Lista de unidades da frota
     * @param negociacoes Lista de negociações que a frota possui
     * @param parametroCiclo Parametros do ciclo da frota
     * @param saldo Saldo da frota
     * @param apiTokens Lista de tokens de API da frota
     * @param versao Versao do registro no banco
     * @param postoInterno Informa se é posto interno
     * @param numeroSequencialJde Número sequencial do JDE
     * @param parametrosSistema Parametros do sistema atrelados a frota
     * @param semNotaFiscal Informa se a frota não possui nota fiscal
     * @param dataAceiteTermos Data de aceite dos termos
     * @param primeiraCompra Informa se a frota fez a primeira compra
     * @param empresasAgregadas Empresas agregadas à frota
     * @param permissoes Lista de permissões da frota
     * @param dataCriacao Data de criação da frota
     * @param dataAtualizacao Data de atualização da frota
     * @param connectCTAToken Token do connect
     * @param condicoesComerciais Condições comerciais do contrato da Frota com o Pró-Frotas
     * @param situacaoConectCar Objeto com a situação conectcar da frota
     * @param tagsAtivas Lista com as tags ativas da frota
     * @param tagsInativas Lista com as tags inativas da frota
     */
    public Frota(Long id, Long cnpj, Integer status, String nomeRazaoFrota, String statusConvertido, String razaoSocial, String nomeFantasia, Long inscricaoEstadual, Long inscricaoMunicipal, Integer cep, String logradouro, Integer numero, String complemento, String bairro, String municipio, String unidadeFederativa, String assessorResponsavel, Usuario usuarioAssessorResponsavel, Integer dddTelefone, Long telefone, String email, String nomeResponsavelFrota, Long cpfResponsavelFrota, String cargoResponsavelFrota, Integer dddTelefoneResponsavelFrota, Long telefoneResponsavelFrota, String emailResponsavelFrota, Integer faixaQtdVeicPesados, Integer faixaQtdVeicLeves, Long volumeEstimadoDiesel, Long volumeEstimadoCicloOtto, Integer modoPagamento, Integer porte, Integer segmentoAtuacao, Integer statusContrato, String statusContratoConvertido, Date inicioContrato, Integer prazoContrato, Date dataHabilitacao, Date dataSaldoZerado, Boolean permiteAcordoEspecial, Boolean excluido, String codigoIBGE, String codCatBeneficioFiscal, Integer numeroJdeInterno, List<GrupoOperacional> gruposOperacionais, List<Veiculo> veiculos, List<Motorista> motoristas, List<Unidade> unidades, List<FrotaPontoVenda> negociacoes, ParametroCiclo parametroCiclo, SaldoFrota saldo, List<ApiToken> apiTokens, Long versao, Boolean postoInterno, Long numeroSequencialJde, List<FrotaParametroSistema> parametrosSistema, Boolean semNotaFiscal, Date dataAceiteTermos, Boolean primeiraCompra, List<EmpresaAgregada> empresasAgregadas, List<Permissao> permissoes, Date dataCriacao, Date dataAtualizacao, String connectCTAToken, CondicoesComerciais condicoesComerciais, SituacaoConectCar situacaoConectCar, List<TagConectcar> tagsAtivas, List<TagConectcar> tagsInativas) {
        this.id = id;
        this.cnpj = cnpj;
        this.status = status;
        this.nomeRazaoFrota = nomeRazaoFrota;
        this.statusConvertido = statusConvertido;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.municipio = municipio;
        this.unidadeFederativa = unidadeFederativa;
        this.assessorResponsavel = assessorResponsavel;
        this.usuarioAssessorResponsavel = usuarioAssessorResponsavel;
        this.dddTelefone = dddTelefone;
        this.telefone = telefone;
        this.email = email;
        this.nomeResponsavelFrota = nomeResponsavelFrota;
        this.cpfResponsavelFrota = cpfResponsavelFrota;
        this.cargoResponsavelFrota = cargoResponsavelFrota;
        this.dddTelefoneResponsavelFrota = dddTelefoneResponsavelFrota;
        this.telefoneResponsavelFrota = telefoneResponsavelFrota;
        this.emailResponsavelFrota = emailResponsavelFrota;
        this.faixaQtdVeicPesados = faixaQtdVeicPesados;
        this.faixaQtdVeicLeves = faixaQtdVeicLeves;
        this.volumeEstimadoDiesel = volumeEstimadoDiesel;
        this.volumeEstimadoCicloOtto = volumeEstimadoCicloOtto;
        this.modoPagamento = modoPagamento;
        this.porte = porte;
        this.segmentoAtuacao = segmentoAtuacao;
        this.statusContrato = statusContrato;
        this.statusContratoConvertido = statusContratoConvertido;
        this.inicioContrato = inicioContrato;
        this.prazoContrato = prazoContrato;
        this.dataHabilitacao = dataHabilitacao;
        this.dataSaldoZerado = dataSaldoZerado;
        this.permiteAcordoEspecial = permiteAcordoEspecial;
        this.excluido = excluido;
        this.codigoIBGE = codigoIBGE;
        this.codCatBeneficioFiscal = codCatBeneficioFiscal;
        this.numeroJdeInterno = numeroJdeInterno;
        this.gruposOperacionais = gruposOperacionais;
        this.veiculos = veiculos;
        this.motoristas = motoristas;
        this.unidades = unidades;
        this.negociacoes = negociacoes;
        this.parametroCiclo = parametroCiclo;
        this.saldo = saldo;
        this.apiTokens = apiTokens;
        this.versao = versao;
        this.postoInterno = postoInterno;
        this.numeroSequencialJde = numeroSequencialJde;
        this.parametrosSistema = parametrosSistema;
        this.semNotaFiscal = semNotaFiscal;
        this.dataAceiteTermos = dataAceiteTermos;
        this.primeiraCompra = primeiraCompra;
        this.empresasAgregadas = empresasAgregadas;
        this.permissoes = permissoes;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.connectCTAToken = connectCTAToken;
        this.condicoesComerciais = condicoesComerciais;
        this.situacaoConectCar = situacaoConectCar;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public Long getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(Long inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public Long getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(Long inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUnidadeFederativa() {
        return unidadeFederativa;
    }

    public void setUnidadeFederativa(String uf) {
        this.unidadeFederativa = uf;
    }

    public String getAssessorResponsavel() {
        return this.usuarioAssessorResponsavel == null ? assessorResponsavel : this.usuarioAssessorResponsavel.getNome();
    }

    public void setAssessorResponsavel(String assessorResponsavel) {
        this.assessorResponsavel = assessorResponsavel;
    }

    public Usuario getUsuarioAssessorResponsavel() {
        return usuarioAssessorResponsavel;
    }

    public void setUsuarioAssessorResponsavel(Usuario usuarioAssessorResponsavel) {
        this.usuarioAssessorResponsavel = usuarioAssessorResponsavel;
    }

    public Usuario getUsuarioConsultorHunter() {
        return usuarioConsultorHunter;
    }

    public void setUsuarioConsultorHunter(Usuario usuarioConsultorHunter) {
        this.usuarioConsultorHunter = usuarioConsultorHunter;
    }

    public Usuario getUsuarioConsultorFarmerPesado() {
        return usuarioConsultorFarmerPesado;
    }

    public void setUsuarioConsultorFarmerPesado(Usuario usuarioConsultorFarmerPesado) {
        this.usuarioConsultorFarmerPesado = usuarioConsultorFarmerPesado;
    }

    public Usuario getUsuarioConsultorFarmerLeve() {
        return usuarioConsultorFarmerLeve;
    }

    public void setUsuarioConsultorFarmerLeve(Usuario usuarioConsultorFarmerLeve) {
        this.usuarioConsultorFarmerLeve = usuarioConsultorFarmerLeve;
    }

    public Integer getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(Integer dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeResponsavelFrota() {
        return nomeResponsavelFrota;
    }

    public void setNomeResponsavelFrota(String nomeResponsavelFrota) {
        this.nomeResponsavelFrota = nomeResponsavelFrota;
    }

    public Long getCpfResponsavelFrota() {
        return cpfResponsavelFrota;
    }

    public void setCpfResponsavelFrota(Long cpfResponsavelFrota) {
        this.cpfResponsavelFrota = cpfResponsavelFrota;
    }

    public String getCargoResponsavelFrota() {
        return cargoResponsavelFrota;
    }

    public void setCargoResponsavelFrota(String cargoResponsavelFrota) {
        this.cargoResponsavelFrota = cargoResponsavelFrota;
    }

    public Integer getDddTelefoneResponsavelFrota() {
        return dddTelefoneResponsavelFrota;
    }

    public void setDddTelefoneResponsavelFrota(Integer dddTelefoneResponsavelFrota) {
        this.dddTelefoneResponsavelFrota = dddTelefoneResponsavelFrota;
    }

    public Long getTelefoneResponsavelFrota() {
        return telefoneResponsavelFrota;
    }

    public void setTelefoneResponsavelFrota(Long telefoneResponsavelFrota) {
        this.telefoneResponsavelFrota = telefoneResponsavelFrota;
    }

    public String getEmailResponsavelFrota() {
        return emailResponsavelFrota;
    }

    public void setEmailResponsavelFrota(String emailResponsavelFrota) {
        this.emailResponsavelFrota = emailResponsavelFrota;
    }

    public String getNomeDonoFrota() {
        return nomeDonoFrota;
    }

    public void setNomeDonoFrota(String nomeDonoFrota) {
        this.nomeDonoFrota = nomeDonoFrota;
    }

    public Long getCpfDonoFrota() {
        return cpfDonoFrota;
    }

    public void setCpfDonoFrota(Long cpfDonoFrota) {
        this.cpfDonoFrota = cpfDonoFrota;
    }

    public String getCargoDonoFrota() {
        return cargoDonoFrota;
    }

    public void setCargoDonoFrota(String cargoDonoFrota) {
        this.cargoDonoFrota = cargoDonoFrota;
    }

    public Integer getDddTelefoneDonoFrota() {
        return dddTelefoneDonoFrota;
    }

    public void setDddTelefoneDonoFrota(Integer dddTelefoneDonoFrota) {
        this.dddTelefoneDonoFrota = dddTelefoneDonoFrota;
    }

    public Long getTelefoneDonoFrota() {
        return telefoneDonoFrota;
    }

    public void setTelefoneDonoFrota(Long telefoneDonoFrota) {
        this.telefoneDonoFrota = telefoneDonoFrota;
    }

    public String getEmailDonoFrota() {
        return emailDonoFrota;
    }

    public void setEmailDonoFrota(String emailDonoFrota) {
        this.emailDonoFrota = emailDonoFrota;
    }

    public Integer getFaixaQtdVeicPesados() {
        return faixaQtdVeicPesados;
    }

    public void setFaixaQtdVeicPesados(Integer faixaQtdVeicPesados) {
        this.faixaQtdVeicPesados = faixaQtdVeicPesados;
    }

    public Integer getFaixaQtdVeicLeves() {
        return faixaQtdVeicLeves;
    }

    public void setFaixaQtdVeicLeves(Integer faixaQtdVeicLeves) {
        this.faixaQtdVeicLeves = faixaQtdVeicLeves;
    }

    public Long getVolumeEstimadoDiesel() {
        return volumeEstimadoDiesel;
    }

    public void setVolumeEstimadoDiesel(Long volumeEstimadoDiesel) {
        this.volumeEstimadoDiesel = volumeEstimadoDiesel;
    }

    public Long getVolumeEstimadoCicloOtto() {
        return volumeEstimadoCicloOtto;
    }

    public void setVolumeEstimadoCicloOtto(Long volumeEstimadoCicloOtto) {
        this.volumeEstimadoCicloOtto = volumeEstimadoCicloOtto;
    }

    public Integer getModoPagamento() {
        return modoPagamento;
    }

    public void setModoPagamento(Integer modoPagamento) {
        this.modoPagamento = modoPagamento;
    }

    public String getModoPagamentoConvertido() {
        return modoPagamentoConvertido;
    }

    public void setModoPagamentoConvertido(String modoPagamentoConvertido) {
        this.modoPagamentoConvertido = modoPagamentoConvertido;
    }

    public Integer getPorte() {
        return porte;
    }

    public void setPorte(Integer porte) {
        this.porte = porte;
    }

    public Integer getSegmentoAtuacao() {
        return segmentoAtuacao;
    }

    public void setSegmentoAtuacao(Integer segmentoAtuacao) {
        this.segmentoAtuacao = segmentoAtuacao;
    }

    public Integer getStatusContrato() {
        return statusContrato;
    }

    public void setStatusContrato(Integer statusContrato) {
        this.statusContrato = statusContrato;
    }

    public Date getInicioContrato() {
        return inicioContrato;
    }

    public void setInicioContrato(Date inicioContrato) {
        this.inicioContrato = inicioContrato;
    }

    public Integer getPrazoContrato() {
        return prazoContrato;
    }

    public void setPrazoContrato(Integer prazoContrato) {
        this.prazoContrato = prazoContrato;
    }

    public Date getDataHabilitacao() {
        return dataHabilitacao;
    }

    public void setDataHabilitacao(Date dataHabilitacao) {
        this.dataHabilitacao = dataHabilitacao;
    }

    public List<GrupoOperacional> getGruposOperacionais() {
        return gruposOperacionais;
    }

    public void setGruposOperacionais(List<GrupoOperacional> gruposOperacionais) {
        this.gruposOperacionais = gruposOperacionais;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Motorista> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(List<Motorista> motoristas) {
        this.motoristas = motoristas;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
    }

    public List<FrotaPontoVenda> getNegociacoes() {
        return negociacoes;
    }

    public void setNegociacoes(List<FrotaPontoVenda> negociacoes) {
        this.negociacoes = negociacoes;
    }

    public ParametroCiclo getParametroCiclo() {
        return parametroCiclo;
    }

    public void setParametroCiclo(ParametroCiclo parametroCiclo) {
        this.parametroCiclo = parametroCiclo;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Boolean getPostoInterno() { return postoInterno; }

    public void setPostoInterno(Boolean postoInterno) { this.postoInterno = postoInterno; }

    public String getStatusContratoConvertido() {
        return statusContratoConvertido;
    }

    public void setStatusContratoConvertido(String statusContratoConvertido) {
        this.statusContratoConvertido = statusContratoConvertido;
    }

    @JsonIgnore
    public String getFrotaParaAutocomplete() {
        return UtilitarioFormatacao.formatarCnpjApresentacao(getCnpj()) + " - " + getRazaoSocial();
    }

    public Boolean getPermiteAcordoEspecial() {
        return permiteAcordoEspecial;
    }

    public void setPermiteAcordoEspecial(Boolean permiteAcordoEspecial) {
        this.permiteAcordoEspecial = permiteAcordoEspecial;
    }

    public SaldoFrota getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoFrota saldo) {
        this.saldo = saldo;
    }

    public String getNomeRazaoFrota() {
        return nomeRazaoFrota;
    }

    public void setNomeRazaoFrota(String nomeRazaoFrota) {
        this.nomeRazaoFrota = nomeRazaoFrota;
    }

    public String getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(String codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getCodCatBeneficioFiscal() {
        return codCatBeneficioFiscal;
    }

    public void setCodCatBeneficioFiscal(String codCatBeneficioFiscal) {
        this.codCatBeneficioFiscal = codCatBeneficioFiscal;
    }

    public Integer getNumeroJdeInterno() {
        return numeroJdeInterno;
    }

    public void setNumeroJdeInterno(Integer numeroJdeInterno) {
        this.numeroJdeInterno = numeroJdeInterno;
    }

    public Long getNumeroSequencialJde() {
        return numeroSequencialJde;
    }

    public void setNumeroSequencialJde(Long numeroSequencialJde) {
        this.numeroSequencialJde = numeroSequencialJde;
    }

    public String getConnectCTAToken() {
        return connectCTAToken;
    }

    public void setConnectCTAToken(String connectCTAToken) {
        this.connectCTAToken = connectCTAToken;
    }

    public ApiToken getApiToken() {
        return apiTokens != null ?
                apiTokens
                        .stream()
                        .filter(a -> !a.isContingencia())
                        .findFirst()
                        .orElse(null)
                : null;
    }

    public ApiToken getApiTokenContigencia() {
        return apiTokens != null ?
                apiTokens
                        .stream()
                        .filter(ApiToken::isContingencia)
                        .findFirst()
                        .orElse(null)
                : null;
    }

    @Transient
    public String getLogradouroENumero() {
        return this.logradouro + (this.numero  != null ? ", " + this.numero : "");
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(this);
    }

    public List<FrotaParametroSistema> getParametrosSistema() {
        return parametrosSistema;
    }

    public void setParametrosSistema(List<FrotaParametroSistema> parametrosSistema) {
        this.parametrosSistema = parametrosSistema;
    }

    public Boolean isSemNotaFiscal() {
        return semNotaFiscal;
    }

    public void setSemNotaFiscal(Boolean semNotaFiscal) {
        this.semNotaFiscal = semNotaFiscal;
    }

    /**
     * @return the apiTokens
     */
    public List<ApiToken> getApiTokens() {
        return apiTokens;
    }

    /**
     * @param apiTokens the apiTokens to set
     */
    public void setApiTokens(List<ApiToken> apiTokens) {
        this.apiTokens = apiTokens;
    }

    public Date getDataAceiteTermos() {
        return dataAceiteTermos;
    }

    public void setDataAceiteTermos(Date dataAceiteTermos) {
        this.dataAceiteTermos = dataAceiteTermos;
    }

    public Boolean getPrimeiraCompra() {
        return primeiraCompra;
    }

    public void setPrimeiraCompra(Boolean primeiraCompra) {
        this.primeiraCompra = primeiraCompra;
    }

    public Boolean getFrotaLeve() {
        return frotaLeve != null ? frotaLeve : false;
    }

    public void setFrotaLeve(Boolean frotaLeve) {
        this.frotaLeve = frotaLeve;
    }

    public Boolean getRoteirizadorInteligente() { return roteirizadorInteligente != null ? roteirizadorInteligente : false; }

    public void setRoteirizadorInteligente(Boolean roteirizadorInteligente) { this.roteirizadorInteligente = roteirizadorInteligente; }

    public Date getDataSaldoZerado() {
        return dataSaldoZerado;
    }

    public void setDataSaldoZerado(Date dataSaldoZerado) {
        this.dataSaldoZerado = dataSaldoZerado;
    }

    public List<EmpresaAgregada> getEmpresasAgregadas() {
        return empresasAgregadas;
    }

    public void setEmpresasAgregadas(List<EmpresaAgregada> empresasAgregadas) {
        this.empresasAgregadas = empresasAgregadas;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public ParametroCiclo getNovoParametroCiclo() {
        return novoParametroCiclo;
    }

    public void setNovoParametroCiclo(ParametroCiclo novoParametroCiclo) {
        this.novoParametroCiclo = novoParametroCiclo;
    }

    public Date getDataAlteracaoCiclo() {
        return dataAlteracaoCiclo;
    }

    public void setDataAlteracaoCiclo(Date dataAlteracaoCiclo) {
        this.dataAlteracaoCiclo = dataAlteracaoCiclo;
    }

    /**
     * Metodo que verifica se a frota precisa comprar credito antes de utilizar o sistema
     * @return true, caso a frota seja pre paga e nao tenha realizado uma primeira compra, false caso contrario
     */
    public Boolean exigeCompraCredito() {
        return this.modoPagamento.equals(ModalidadePagamento.PRE_PAGO.getValue()) && (this.primeiraCompra == null || !this.primeiraCompra);
    }

    /**
     * Metodo que verifica se a frota precisa aceitar o termo de compromisso antes de utilizar o sistema
     * @return true, caso a frota seja pre paga e nao tenha aceitado o termo de compromisso, false caso contrario
     */
    public Boolean exigirAceiteTermos() {
        return this.modoPagamento.equals(ModalidadePagamento.PRE_PAGO.getValue()) && this.dataAceiteTermos == null;
    }

    /**
     * Verifica se a frota é pre-paga
     * @return se é pre paga ou não
     */
    @Transient
    public boolean isPrePaga() {
        return this.modoPagamento != null && ModalidadePagamento.obterPorValor(this.modoPagamento).equals(ModalidadePagamento.PRE_PAGO);
    }

    public Boolean getExibirDesconto() {
        return exibirDesconto != null ? exibirDesconto : false;
    }

    public void setExibirDesconto(Boolean exibirDesconto) {
        this.exibirDesconto = exibirDesconto;
    }

    /**
     * Monta o nome de apresentacao da Frota,
     * @return O nome de apresentacao da Frota
     */
    @Transient
    public String getNomeApresentacao() {
        String  cnpjApresentacao = UtilitarioFormatacao.formatarCnpjApresentacao(getCnpj());
        String nomeFrota = getRazaoSocial();
        cnpjApresentacao += " - ";
        return cnpjApresentacao + nomeFrota;
    }

    /**
     * Obtém o nome do muncípio no formato "nome - uf"
     *
     * @return O nome do munícipio no formato desejado
     */
    @Transient
    public String getNomeMunicipioApresentacao() {
        StringJoiner joiner = new StringJoiner(" - ");
        joiner.add(this.municipio);
        joiner.add(this.unidadeFederativa);

        return joiner.toString();
    }

    public void setCondicoesComerciais(CondicoesComerciais condicoesComerciais) {
        this.condicoesComerciais = condicoesComerciais;
    }
    public CondicoesComerciais getCondicoesComerciais() {
        return condicoesComerciais;
    }
    /**
     * Informa se a Matriz da frota exige nota fiscal.
     *
     * @return true, caso exija.
     */
    @Transient
    public boolean exigeNotaFiscal() {
        return semNotaFiscal == null || !semNotaFiscal;
    }

    /**
     * Existe uma frota ou unidade que exige nota fiscal?
     * @return true se positivo
     */
    @Transient
    public boolean isFrotaOuUmaDasUnidadesExigemNotaFiscal(){
        return  this.exigeNotaFiscal()
                || this.getUnidades() != null
                && this.getUnidades()
                .stream()
                .anyMatch(u -> u.getExigeNotaFiscal() != null && u.getExigeNotaFiscal());
    }

    /**
     * Verifica se frota tem parametro de ciclo para atualizar
     * @return true caso tenha novo parâmetro de ciclo
     */
    @Transient
    public boolean temParametroDeCicloNovoParaAtualizar() {
        return this.getNovoParametroCiclo() != null;
    }

    /**
     * Atualiza parametro ciclo com novo parametro ciclo agendado e remove o agendamento
     */
    @Transient
    public void atualizaParametroDeCicloConformeNovoCiclo() {
        if (this.temParametroDeCicloNovoParaAtualizar()) {
            this.setParametroCiclo(this.getNovoParametroCiclo());
            this.setNovoParametroCiclo(null);
            this.setDataAlteracaoCiclo(null);
        }
    }

    @Transient
    public String getLongitudeString() {
        return UtilitarioFormatacao.formatarDecimal(this.longitude);
    }

    @Transient
    public String getLatitudeString() {
        return UtilitarioFormatacao.formatarDecimal(this.latitude);
    }

    public SituacaoConectCar getSituacaoConectCar() {
        return situacaoConectCar;
    }

    public void setSituacaoConectCar(SituacaoConectCar situacaoConectCar) {
        this.situacaoConectCar = situacaoConectCar;
    }

    public ParametroNotaFiscal getParametroNotaFiscal() {
        return parametroNotaFiscal;
    }

    public void setParametroNotaFiscal(ParametroNotaFiscal parametroNotaFiscal) {
        this.parametroNotaFiscal = parametroNotaFiscal;
    }

    public Integer getTotalTags() {
        return totalTags;
    }

    public void setTotalTags(Integer totalTags) {
        this.totalTags = totalTags;
    }

    public Integer getTotalTagsAtivas() {
        return totalTagsAtivas;
    }

    public void setTotalTagsAtivas(Integer totalTagsAtivas) {
        this.totalTagsAtivas = totalTagsAtivas;
    }

    public List<PostoInternoTipoCombustivelPreco> getPostoInternoTipoCombustivelPreco() {
        return postoInternoTipoCombustivelPreco;
    }

    public void setPostoInternoTipoCombustivelPreco(List<PostoInternoTipoCombustivelPreco> postoInternoTipoCombustivelPreco) {
        this.postoInternoTipoCombustivelPreco = postoInternoTipoCombustivelPreco;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public Boolean getGerenciaNfAgendada() {
        return gerenciaNfAgendada;
    }

    public void setGerenciaNfAgendada(Boolean gerenciaNfAgendada) {
        this.gerenciaNfAgendada = gerenciaNfAgendada;
    }

    public Boolean isGerenciaNf() {
        return gerenciaNf;
    }

    public void setGerenciaNf(Boolean gerenciaNf) {
        this.gerenciaNf = gerenciaNf;
    }

    public Boolean getLembrarParametrizacaoNf() {
        return lembrarParametrizacaoNf;
    }

    public void setLembrarParametrizacaoNf(Boolean lembrarParametrizacaoNf) {
        this.lembrarParametrizacaoNf = lembrarParametrizacaoNf;
    }

    /**
     * Obtem o endereço completo
     * @return endereço completo
     */
    @Transient
    public String obterEnderecoCompleto(){
        StringBuffer buffer = new StringBuffer();
        if(this.logradouro != null){
            buffer.append(this.logradouro).append(", ");
        }
        if(this.numero != null){
            buffer.append(this.numero).append(", ");
        }
        if(this.complemento != null){
            buffer .append(this.complemento).append(", ");
        }
        if(this.bairro != null){
            buffer.append(this.bairro).append(", ");
        }
        if(this.unidadeFederativa != null){
            buffer.append(this.unidadeFederativa).append(", ");
        }
        if(this.cep != null){
            buffer.append("( ").append(UtilitarioFormatacao.formatarCepApresentacao(this.cep)).append(" )");
        }
        return buffer.toString();
    }

    public List<MotivoAlteracaoStatusFrota> getMotivosAlteracaoStatus() {
       return motivosAlteracaoStatus;
    }

    public void setMotivosAlteracaoStatus(List<MotivoAlteracaoStatusFrota> motivosAlteracaoStatus) {
        this.motivosAlteracaoStatus = motivosAlteracaoStatus;
    }

    public ConfiguracaoDistribuicaoAutomatica getConfiguracaoDistribuicaoAutomatica() {
        return configuracaoDistribuicaoAutomatica;
    }

    public void setConfiguracaoDistribuicaoAutomatica(ConfiguracaoDistribuicaoAutomatica configuracaoDistribuicaoAutomatica) {
        this.configuracaoDistribuicaoAutomatica = configuracaoDistribuicaoAutomatica;
    }

    public List<Beneficiario> getBeneficiarios() {
        return beneficiarios;
    }

    public void setBeneficiarios(List<Beneficiario> beneficiarios) {
        this.beneficiarios = beneficiarios;
    }

    public ContaBeneficiosFrota getContaBeneficiosFrota() {
        return contaBeneficiosFrota;
    }

    public void setContaBeneficiosFrota(ContaBeneficiosFrota contaBeneficiosFrota) {
        this.contaBeneficiosFrota = contaBeneficiosFrota;
    }

    /**
     * Verifica se os debitos da frota em questao encontram-se pagos
     * @return True caso os debitos da frota em questao encontrem-se pagos.
     */
    @Transient
    public boolean isSemDebitosVencidos() {
        return StatusFrota.ATIVO.getValue().equals(status)
                && !motivosAlteracaoStatus.stream().anyMatch(
                        motivo ->
                                ClassificacaoStatusFrota.DEBITO_VENCIDO.getValue().equals(motivo.getTipoMotivo())
                                && StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                );
    }

    /**
     * Verifica se a frota possui alguma ativação temporária em andamento
     *
     * @return True caso a frota possua ativação temporária em andamento, false caso contrário
     */
    @Transient
    public boolean possuiAtivacaoTemporariaAtiva() {
        return motivosAlteracaoStatus.stream().anyMatch(
                motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                        && TipoAlteracaoStatusFrota.ATIVACAO.getValue().equals(motivo.getTipoAlteracaoStatus())
                        && motivo.getDataInicio() != null
                        && motivo.getDataFim() != null
        );
    }

    /**
     * Verifica se a frota possui alguma inativação temporária em andamento
     *
     * @return True caso a frota possua inativação temporária em andamento, false caso contrário
     */
    @Transient
    public boolean possuiInativacaoTemporariaAtiva() {
        return motivosAlteracaoStatus
                .stream()
                .anyMatch(
                    motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                            && TipoAlteracaoStatusFrota.INATIVACAO.getValue().equals(motivo.getTipoAlteracaoStatus())
                            && motivo.getDataInicio() != null
                            && motivo.getDataFim() != null
                );
    }

    /**
     * Obtém o último motivo de ativação da frota
     * @return O último motivo de ativação encontrado
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoAtivacao() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                        motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                                && TipoAlteracaoStatusFrota.ATIVACAO.getValue().equals(motivo.getTipoAlteracaoStatus())
                )
                .max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém o último motivo de inativação da frota
     * @return O último motivo de inativação encontrado
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoInativacao() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                    motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                            && TipoAlteracaoStatusFrota.INATIVACAO.getValue().equals(motivo.getTipoAlteracaoStatus())
                )
                .max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém o último motivo de alteração de status da frota
     * @return O último motivo de alteração de status encontrado
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoAlteracaoStatus() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                        motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                )
                .max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém o último motivo vigente de débito vencido
     * @return O motivo
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoDebitoVencidoVigente() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                        motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                                && ClassificacaoStatusFrota.DEBITO_VENCIDO.getValue().equals(motivo.getTipoMotivo())
                )
                .max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém o último motivo vigente de saldo zerado
     * @return O motivo
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoSaldoZeradoVigente() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                        motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                                && ClassificacaoStatusFrota.SALDO_ZERADO.getValue().equals(motivo.getTipoMotivo())
                                && StatusFrota.INATIVO.getValue().equals(status)
                                && dataSaldoZerado != null
                ).max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém o último motivo de alteração de status da frota
     * @return O último motivo de alteração de status encontrado
     */
    @Transient
    public MotivoAlteracaoStatusFrota getUltimoMotivoDefinitivo() {
        return motivosAlteracaoStatus
                .stream()
                .filter(
                        motivo -> StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue().equals(motivo.getStatusVigenciaAlteracao())
                        && motivo.getDataInicio() == null
                        && motivo.getDataFim() == null
                )
                .max(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .orElse(null);
    }

    /**
     * Obtém a lista de motivos da frota ordenados por data de criação
     * @return A lista ordenada
     */
    @Transient
    public List<MotivoAlteracaoStatusFrota> getMotivosAlteracaoStatusFrotaOrdenados() {
        return motivosAlteracaoStatus
                .stream()
                .sorted(Comparator.comparing(MotivoAlteracaoStatusFrota::getDataCriacao))
                .collect(Collectors.toList());
    }
}
