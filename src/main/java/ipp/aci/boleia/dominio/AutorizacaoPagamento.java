package ipp.aci.boleia.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.TipoErroAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TipoPreenchimentoLitragem;
import ipp.aci.boleia.dominio.enums.TipoRealizacaoPedido;
import ipp.aci.boleia.dominio.enums.TipoSenhaAutorizacao;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa a tabela de Autorizacao Pagamento
 */
@Entity
@Audited
@Table(name = "AUTORIZACAO_PAGAMENTO")
public class AutorizacaoPagamento implements IPersistente, IPertenceFrota, IPertenceRevendedor, IPertenceMotorista {

    public static final String VALOR_MAXIMO_AUTORIZACAO = "99999.999";
    private static final long serialVersionUID = 5442724833587865453L;

    /**
     * <i>@Formula</i> necessária para que seja possível realizar a ordenação paginada
     * dos estornos por preço total de abastecimeto sem grandes mudanças na estrutura de pesquisa.
     */
    private static final String PRECOCOMBUSTIVEL_FORMULA = "QT_TOTAL_LIT_ABAS * VA_UNITARIO_ABAS";

    /**
     * Formula necessária para possibilitar a ordenação padrão dos abastecimentos exibidos
     * na tela de detalhamento de NF.
     */
    private static final String CHAVE_ORDENACAO_FINANCEIRO_FORMULA =
                    "CASE WHEN AP.CD_TRANS_CONSOL_POSTERGADA IS NOT NULL AND AP.ID_STATUS = 1 THEN 0 " +
                    "     WHEN AP.CD_TRANS_CONSOL_POSTERGADA IS NOT NULL AND AP.ID_STATUS <> 1 THEN 1 " +
                    "     WHEN AP.CD_TRANS_CONSOL_POSTERGADA IS NULL AND (AP.ID_STATUS_EDICAO = 1 OR AP.ID_STATUS = -1) THEN 2 " +
                    "     ELSE 3 " +
                    "END";

    @Id
    @Column(name = "CD_AUTORIZACAO_PAGAMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTORIZACAO_PAGAMENTO")
    @SequenceGenerator(name = "SEQ_AUTORIZACAO_PAGAMENTO", sequenceName = "SEQ_AUTORIZACAO_PAGAMENTO", allocationSize = 1)
    private Long id;

    @Size(max=50)
    @Column(name = "CD_UUID_AUTORIZACAO_PAGAMENTO")
    private String uuid;

    @Size(max=50)
    @Column(name = "CD_PAGAMENTO")
    private String codigoPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COMANDA")
    private ComandaDigital comandaDigital;

    @Size(max=250)
    @Column(name = "NM_COMANDA")
    private String nomeComanda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @Size(max=250)
    @Column(name = "NM_UNIDADE")
    private String nomeUnidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusAutorizacao.DECODE_FORMULA)
    private String statusConvertido;

    @Size(max=450)
    @Column(name = "DS_MOTIVO_RECUSA")
    private String motivoRecusa;

    @Size(max=50)
    @Column(name = "CD_VIP")
    private String codigoVip;

    @NotNull
    @Size(max=15)
    @Column(name = "CD_IP_ORIGEM")
    private String ipOrigem;

    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitudeOrigem;

    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitudeOrigem;

    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT_RASTREADOR")
    private BigDecimal latitudeRastreador;

    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT_RASTREADOR")
    private BigDecimal longitudeRastreador;

    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT_POSTO")
    private BigDecimal latitudePosto;

    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT_POSTO")
    private BigDecimal longitudePosto;

    @Column(name = "ID_RASTREADOR")
    @Min(value = -1)
    @Max(value = 1)
    private Integer statusRastreador;

    @NotNull
    @Column(name = "DT_REQUISICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRequisicao;

    @Column(name = "DT_PROCESSAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataProcessamento;

    @NotNull
    @Max(99999999999999L)
    @Column(name = "CD_CNPJ_FROTA")
    private Long cnpjFrota;

    @Size(max=250)
    @Column(name = "NM_RAZAO_SOCIAL_FROTA")
    private String razaoSocialFrota;

    @Column(name = "CD_CPF_MOTORISTA")
    private Long cpfMotorista;

    @Size(max = 250)
    @Column(name = "NM_MOTORISTA")
    private String nomeMotorista;

    @Column(name = "ID_AGREGADO_MOTORISTA")
    private Integer agregadoMotorista;

    @Column(name = "CD_CNPJ_UNIDADE_MOTORISTA")
    private Long cnpjUnidadeMotorista;

    @Size(max=250)
    @Column(name = "NM_UNIDADE_MOTORISTA")
    private String nomeUnidadeMotorista;

    @Column(name = "CD_GRUPO_MOTORISTA")
    private String codigoGrupoMotorista;

    @Size(max=250)
    @Column(name = "NM_GRUPO_MOTORISTA")
    private String nomeGrupoMotorista;

    @Column(name = "CD_CNPJ_EMPRESA_MOTORISTA")
    private Long cnpjEmpresaMotorista;

    @Size(max=250)
    @Column(name = "NM_RAZAO_EMPRESA_MOTORISTA")
    private String razaoSocialEmpresaMotorista;

    @NotNull
    @Size(max=8)
    @Column(name = "DS_PLACA_VEICULO")
    private String placaVeiculo;

    @Column(name = "ID_AGREGADO_VEICULO")
    private Integer agregadoVeiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @Column(name = "CD_CNPJ_UNIDADE_VEICULO")
    private Long cnpjUnidadeVeiculo;

    @Size(max=250)
    @Column(name = "NM_UNIDADE_VEICULO")
    private String nomeUnidadeVeiculo;

    @Column(name = "CD_GRUPO_VEICULO")
    private String codigoGrupoVeiculo;

    @Size(max=250)
    @Column(name = "NM_GRUPO_VEICULO")
    private String nomeGrupoVeiculo;

    @Column(name = "CD_CNPJ_EMPRESA_VEICULO")
    private Long cnpjEmpresaVeiculo;

    @Size(max=250)
    @Column(name = "NM_RAZAO_EMPRESA_VEICULO")
    private String razaoSocialEmpresaVeiculo;

    @Column(name = "NO_HODOMETRO")
    private Long hodometro;

    @Column(name = "NO_HORIMETRO")
    private BigDecimal horimetro;

    @Column(name = "NO_HODOMETRO_ANT")
    private Long hodometroAnterior;

    @Column(name = "NO_HORIMETRO_ANT")
    private BigDecimal horimetroAnterior;

    @Column(name = "NO_HODOMETRO_RASTREADOR")
    private Long hodometroRastreador;

    @Column(name = "NO_HORIMETRO_RASTREADOR")
    private BigDecimal horimetroRastreador;

    @Max(99999999L)
    @Column(name = "CD_PTOV_CORP")
    private Long codigoCorporativoPv;

    @Column(name = "CD_CNPJ_AREA_ABASTECIMENTO")
    private Long cnpjAreaAbastecimento;

    @Size(max=40)
    @Column(name = "NM_PTOV")
    private String nomePv;

    @Size(max=450)
    @Column(name = "DS_ENDERECO_PTOV")
    private String enderecoPv;

    @Size(max=50)
    @Column(name = "NM_MUNIC")
    private String municipioPV;

    @Size(max=2)
    @Column(name = "SG_UF")
    private String estadoPV;

    @Size(max=30)
    @Column(name = "DS_BANDEIRA")
    private String bandeiraAreaAbastecimento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANSACAO_FROTA")
    private TransacaoFrota transacaoFrota;

    @DecimalMax(VALOR_MAXIMO_AUTORIZACAO)
    @Column(name = "QT_TOTAL_LIT_ABAS")
    private BigDecimal totalLitrosAbastecimento;

    @Size(max=250)
    @Column(name = "NM_ITEM_ABAS")
    private String nomeItemAbastecimento;

    @DecimalMax("999.999")
    @Column(name = "VA_UNITARIO_ABAS")
    private BigDecimal valorUnitarioAbastecimento;

    @Max(999)
    @Column(name = "QT_ITENS_ADICIONAIS")
    private Integer quantidadeItensAdicionais;

    @DecimalMax(VALOR_MAXIMO_AUTORIZACAO)
    @Column(name = "VA_TOTAL")
    private BigDecimal valorTotal;

    @Size(max=250)
    @Column(name = "DS_SUB_TIPO_VEICULO")
    private String subTipoVeiculo;

    @Max(999)
    @Column(name = "ID_TIPO_AUTO_PAG")
    private Integer tipoAutorizacaoPagamento;

    @Max(999)
    @Column(name = "ID_TIPO_SENHA_AUTO")
    private Integer tipoSenhaAutorizacao;

    @Column(name = "ID_MOTIVO_SEM_SENHA_OU_CODIGO")
    private Integer codigoMotivoSemSenhaOuCodigo;

    @NotAudited
    @Formula(StatusAutorizacao.DECODE_FORMULA)
    private String statusAutorizacaoConvertido;

    @OneToMany(mappedBy = "autorizacaoPagamento", fetch = FetchType.LAZY)
    private List<ItemAutorizacaoPagamento> items;

    @Max(999)
    @Column(name = "ID_MOTIVO_ESTORNO")
    private Integer motivoEstorno;

    @Size(max=250)
    @Column(name = "DS_JUSTIF_ESTORNO")
    private String justificativaEstorno;

    @Column(name = "CD_AUTORIZACAO_ESTORNO")
    private Long idAutorizacaoEstorno;

    @Size(max=250)
    @Column(name = "DS_JUSTIF_INCL")
    private String justificativaInclusao;

    @Max(99)
    @Column(name = "ID_MOTIVO_INCL")
    private Integer motivoInclusao;

    @Column(name="VA_COTA_VEICULO")
    private BigDecimal cotaVeiculo;

    @Column(name="VA_CONSUMO_VEICULO")
    private BigDecimal consumidoVeiculo;

    @Column(name="NM_HODOM_HORIM_USUARIO_EDICAO")
    private String hodometroHorimetroUsuarioEdicao;

    @Column(name="DT_HODOM_HORIM_EDICAO")
    private Date hodometroHorimetroDataHoraEdicao;

    @Column(name="VA_HODOM_HORIM_ANTERIOR_EDICAO")
    private BigDecimal hodometroHorimetroAnteriorEdicao;

    @Column(name="ID_STATUS_NF")
    private Integer statusNotaFiscal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PEDIDO")
    private DispositivoMotoristaPedido pedido;

    @Size(max=44)
    @Column(name = "CD_UUID_ABASTECIMENTO")
    private String uuidAbastecimento;

    @Column(name="ID_EMPR_AGREG_EXIGE_NF")
    private Boolean empresaAgregadaExigeNf;

    @Column(name="ID_UNIDADE_EXIGE_NF")
    private Boolean unidadeExigeNf;

    @Max(99999999L)
    @Column(name = "CD_PTOV_ABADI")
    private Long numeroAbadiPv;

    @Transient
    private Long codigoItemAbastecimento;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @AuditJoinTable(name = "AUTORIZACAO_NOTA_AUD")
    @JoinTable(name="AUTORIZACAO_NOTA", joinColumns={@JoinColumn(name="CD_AUTORIZACAO_PAGAMENTO")}, inverseJoinColumns={@JoinColumn(name="CD_NFE")})
    private List<NotaFiscal> notasFiscais;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "nota")
    private AvaliacaoAbastecimento avaliacaoAbastecimento;

    @Column(name = "CD_FRENTISTA")
    private String codigoFrentista;

    @Column(name = "NM_FRENTISTA")
    private String nomeFrentista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL")
    private TransacaoConsolidada transacaoConsolidada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL_POSTERGADA")
    private TransacaoConsolidada transacaoConsolidadaPostergada;

    @Column(name = "DT_POSTERGACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPostergacao;

    @Column(name = "CD_ABASTECIMENTO_CTA")
    private Long codigoAbastecimentoCTA;

    @Column(name="CD_RFID_FRENTISTA")
    private String codigoRFIDFrentista;

    @DecimalMax(VALOR_MAXIMO_AUTORIZACAO)
    @Column(name = "VA_DESCONTO_TOTAL")
    private BigDecimal valorDescontoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CICLO_REPASSE")
    private CicloRepasse cicloRepasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CICLO_REPASSE_ORIGINAL")
    private CicloRepasse cicloRepasseOriginal;

    @Column(name = "DT_POSTERGACAO_REPASSE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPostergacaoRepasse;

    @Column(name = "ID_PROCESSADO_CAMPANHA")
    private Boolean foiProcessadoPeloGeradorDeCampanhas;

    @Column(name = "ID_STATUS_EDICAO")
    private Integer statusEdicao;

    @NotAudited
    @Formula(PRECOCOMBUSTIVEL_FORMULA)
    private BigDecimal precoCombustivelTotal;

    @NotAudited
    @Formula(CHAVE_ORDENACAO_FINANCEIRO_FORMULA)
    private Integer chaveOrdenacaoFinanceiro;

    @Transient
    private TipoErroAutorizacaoPagamento tipoErroAutorizacaoPagamento;

    /**
     * Construtor da classe.
     */
    public AutorizacaoPagamento () {
        this.statusEdicao = StatusEdicao.NAO_EDITADO.getValue();
    }

    public String getCodigoRFIDFrentista() {
        return codigoRFIDFrentista;
    }

    public void setCodigoRFIDFrentista(String codigoRFIDFrentista) {
        this.codigoRFIDFrentista = codigoRFIDFrentista;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Frota getFrota() {
        return this.frota;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public String getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(String codigoVip) {
        this.codigoVip = codigoVip;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }

    public void setIpOrigem(String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    public BigDecimal getLatitudeOrigem() {
        return latitudeOrigem;
    }

    public void setLatitudeOrigem(BigDecimal latitudeOrigem) {
        this.latitudeOrigem = latitudeOrigem;
    }

    public BigDecimal getLongitudeOrigem() {
        return longitudeOrigem;
    }

    public void setLongitudeOrigem(BigDecimal longitudeOrigem) {
        this.longitudeOrigem = longitudeOrigem;
    }

    public BigDecimal getLatitudeRastreador() {
        return latitudeRastreador;
    }

    public void setLatitudeRastreador(BigDecimal latitudeRastreador) {
        this.latitudeRastreador = latitudeRastreador;
    }

    public BigDecimal getLongitudeRastreador() {
        return longitudeRastreador;
    }

    public void setLongitudeRastreador(BigDecimal longitudeRastreador) {
        this.longitudeRastreador = longitudeRastreador;
    }

    public BigDecimal getLatitudePosto() {
        return latitudePosto;
    }

    public void setLatitudePosto(BigDecimal latitudePosto) {
        this.latitudePosto = latitudePosto;
    }

    public BigDecimal getLongitudePosto() {
        return longitudePosto;
    }

    public void setLongitudePosto(BigDecimal longitudePosto) {
        this.longitudePosto = longitudePosto;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public Long getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(Long cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public String getRazaoSocialFrota() {
        return razaoSocialFrota;
    }

    public void setRazaoSocialFrota(String razaoSocialFrota) {
        this.razaoSocialFrota = razaoSocialFrota;
    }

    public Long getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(Long cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public Integer getAgregadoMotorista() {
        return agregadoMotorista;
    }

    public void setAgregadoMotorista(Integer agregadoMotorista) {
        this.agregadoMotorista = agregadoMotorista;
    }

    public Long getCnpjUnidadeMotorista() {
        return cnpjUnidadeMotorista;
    }

    public void setCnpjUnidadeMotorista(Long cnpjUnidadeMotorista) {
        this.cnpjUnidadeMotorista = cnpjUnidadeMotorista;
    }

    public String getNomeUnidadeMotorista() {
        return nomeUnidadeMotorista;
    }

    public void setNomeUnidadeMotorista(String nomeUnidadeMotorista) {
        this.nomeUnidadeMotorista = nomeUnidadeMotorista;
    }

    public String getCodigoGrupoMotorista() {
        return codigoGrupoMotorista;
    }

    public void setCodigoGrupoMotorista(String codigoGrupoMotorista) {
        this.codigoGrupoMotorista = codigoGrupoMotorista;
    }

    public String getNomeGrupoMotorista() {
        return nomeGrupoMotorista;
    }

    public void setNomeGrupoMotorista(String nomeGrupoMotorista) {
        this.nomeGrupoMotorista = nomeGrupoMotorista;
    }

    public Long getCnpjEmpresaMotorista() {
        return cnpjEmpresaMotorista;
    }

    public void setCnpjEmpresaMotorista(Long cnpjEmpresaMotorista) {
        this.cnpjEmpresaMotorista = cnpjEmpresaMotorista;
    }

    public String getRazaoSocialEmpresaMotorista() {
        return razaoSocialEmpresaMotorista;
    }

    public void setRazaoSocialEmpresaMotorista(String razaoSocialEmpresaMotorista) {
        this.razaoSocialEmpresaMotorista = razaoSocialEmpresaMotorista;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Integer getAgregadoVeiculo() {
        return agregadoVeiculo;
    }

    public void setAgregadoVeiculo(Integer agregadoVeiculo) {
        this.agregadoVeiculo = agregadoVeiculo;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public Long getCnpjUnidadeVeiculo() {
        return cnpjUnidadeVeiculo;
    }

    public void setCnpjUnidadeVeiculo(Long cnpjUnidadeVeiculo) {
        this.cnpjUnidadeVeiculo = cnpjUnidadeVeiculo;
    }

    public String getNomeUnidadeVeiculo() {
        return nomeUnidadeVeiculo;
    }

    public void setNomeUnidadeVeiculo(String nomeUnidadeVeiculo) {
        this.nomeUnidadeVeiculo = nomeUnidadeVeiculo;
    }

    public String getCodigoGrupoVeiculo() {
        return codigoGrupoVeiculo;
    }

    public void setCodigoGrupoVeiculo(String codigoGrupoVeiculo) {
        this.codigoGrupoVeiculo = codigoGrupoVeiculo;
    }

    public String getNomeGrupoVeiculo() {
        return nomeGrupoVeiculo;
    }

    public void setNomeGrupoVeiculo(String nomeGrupoVeiculo) {
        this.nomeGrupoVeiculo = nomeGrupoVeiculo;
    }

    public Long getCnpjEmpresaVeiculo() {
        return cnpjEmpresaVeiculo;
    }

    public void setCnpjEmpresaVeiculo(Long cnpjEmpresaVeiculo) {
        this.cnpjEmpresaVeiculo = cnpjEmpresaVeiculo;
    }

    public String getRazaoSocialEmpresaVeiculo() {
        return razaoSocialEmpresaVeiculo;
    }

    public void setRazaoSocialEmpresaVeiculo(String razaoSocialEmpresaVeiculo) {
        this.razaoSocialEmpresaVeiculo = razaoSocialEmpresaVeiculo;
    }

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }

    public BigDecimal getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(BigDecimal horimetro) {
        this.horimetro = horimetro;
    }

    public Long getHodometroAnterior() {
        return hodometroAnterior;
    }

    public void setHodometroAnterior(Long hodometroAnterior) {
        this.hodometroAnterior = hodometroAnterior;
    }

    public BigDecimal getHorimetroAnterior() {
        return horimetroAnterior;
    }

    public void setHorimetroAnterior(BigDecimal horimetroAnterior) {
        this.horimetroAnterior = horimetroAnterior;
    }

    public Long getCodigoCorporativoPv() {
        return codigoCorporativoPv;
    }

    public void setCodigoCorporativoPv(Long codigoCorporativoPv) {
        this.codigoCorporativoPv = codigoCorporativoPv;
    }

    public Long getCnpjAreaAbastecimento() {
        return cnpjAreaAbastecimento;
    }

    public void setCnpjAreaAbastecimento(Long cnpjAreaAbastecimento) {
        this.cnpjAreaAbastecimento = cnpjAreaAbastecimento;
    }

    public String getNomePv() {
        return nomePv;
    }

    public void setNomePv(String nomePv) {
        this.nomePv = nomePv;
    }

    public String getEnderecoPv() {
        return enderecoPv;
    }

    public void setEnderecoPv(String enderecoPv) {
        this.enderecoPv = enderecoPv;
    }

    public String getBandeiraAreaAbastecimento() {
        return bandeiraAreaAbastecimento;
    }

    public void setBandeiraAreaAbastecimento(String bandeiraAreaAbastecimento) {
        this.bandeiraAreaAbastecimento = bandeiraAreaAbastecimento;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }

    public TransacaoFrota getTransacaoFrota() {
        return transacaoFrota;
    }

    public void setTransacaoFrota(TransacaoFrota transacaoFrota) {
        this.transacaoFrota = transacaoFrota;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public BigDecimal getTotalLitrosAbastecimento() {
        return totalLitrosAbastecimento;
    }

    public void setTotalLitrosAbastecimento(BigDecimal totalLitrosAbastecimento) {
        this.totalLitrosAbastecimento = totalLitrosAbastecimento;
    }

    public String getNomeItemAbastecimento() {
        return nomeItemAbastecimento;
    }

    public void setNomeItemAbastecimento(String nomeItemAbastecimento) {
        this.nomeItemAbastecimento = nomeItemAbastecimento;
    }

    public BigDecimal getValorUnitarioAbastecimento() {
        return valorUnitarioAbastecimento;
    }

    public void setValorUnitarioAbastecimento(BigDecimal valorUnitarioAbastecimento) {
        this.valorUnitarioAbastecimento = valorUnitarioAbastecimento;
    }

    public Integer getQuantidadeItensAdicionais() {
        return quantidadeItensAdicionais;
    }

    public void setQuantidadeItensAdicionais(Integer quantidadeItensAdicionais) {
        this.quantidadeItensAdicionais = quantidadeItensAdicionais;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getSubTipoVeiculo() {
        return subTipoVeiculo;
    }

    public void setSubTipoVeiculo(String subTipoVeiculo) {
        this.subTipoVeiculo = subTipoVeiculo;
    }

    public Integer getTipoAutorizacaoPagamento() {
        return tipoAutorizacaoPagamento;
    }

    public void setTipoAutorizacaoPagamento(Integer tipoAutorizacaoPagamento) {
        this.tipoAutorizacaoPagamento = tipoAutorizacaoPagamento;
    }

    public String getStatusAutorizacaoConvertido() {
        return statusAutorizacaoConvertido;
    }

    public void setStatusAutorizacaoConvertido(String statusAutorizacaoConvertido) {
        this.statusAutorizacaoConvertido = statusAutorizacaoConvertido;
    }

    public Integer getTipoSenhaAutorizacao() {
        return tipoSenhaAutorizacao;
    }

    public void setTipoSenhaAutorizacao(Integer tipoSenhaAutorizacao) {
        this.tipoSenhaAutorizacao = tipoSenhaAutorizacao;
    }

    public Integer getCodigoMotivoSemSenhaOuCodigo() {
        return codigoMotivoSemSenhaOuCodigo;
    }

    public void setCodigoMotivoSemSenhaOuCodigo(Integer codigoMotivoSemSenhaOuCodigo) {
        this.codigoMotivoSemSenhaOuCodigo = codigoMotivoSemSenhaOuCodigo;
    }

    public ComandaDigital getComandaDigital() {
        return comandaDigital;
    }

    public void setComandaDigital(ComandaDigital comandaDigital) {
        this.comandaDigital = comandaDigital;
    }

    public String getNomeComanda() {
        return nomeComanda;
    }

    public void setNomeComanda(String nomeComanda) {
        this.nomeComanda = nomeComanda;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public Integer getMotivoEstorno() {
        return motivoEstorno;
    }

    public void setMotivoEstorno(Integer motivoEstorno) {
        this.motivoEstorno = motivoEstorno;
    }

    public String getJustificativaEstorno() {
        return justificativaEstorno;
    }

    public void setJustificativaEstorno(String justificativaEstorno) {
        this.justificativaEstorno = justificativaEstorno;
    }

    public Long getIdAutorizacaoEstorno() {
        return idAutorizacaoEstorno;
    }

    public void setIdAutorizacaoEstorno(Long idAutorizacaoEstorno) {
        this.idAutorizacaoEstorno = idAutorizacaoEstorno;
    }

    public List<ItemAutorizacaoPagamento> getItems() {
        return items;
    }

    public void setItems(List<ItemAutorizacaoPagamento> items) {
        this.items = items;
    }

    public String getJustificativaInclusao() {
        return justificativaInclusao;
    }

    public void setJustificativaInclusao(String justificativaInclusao) {
        this.justificativaInclusao = justificativaInclusao;
    }

    public Integer getMotivoInclusao() {
        return motivoInclusao;
    }

    public void setMotivoInclusao(Integer motivoInclusao) {
        this.motivoInclusao = motivoInclusao;
    }

    public BigDecimal getCotaVeiculo() {
        return cotaVeiculo;
    }

    public void setCotaVeiculo(BigDecimal cotaVeiculo) {
        this.cotaVeiculo = cotaVeiculo;
    }

    public BigDecimal getConsumidoVeiculo() {
        return consumidoVeiculo;
    }

    public void setConsumidoVeiculo(BigDecimal consumidoVeiculo) {
        this.consumidoVeiculo = consumidoVeiculo;
    }

    public String getHodometroHorimetroUsuarioEdicao() {
        return hodometroHorimetroUsuarioEdicao;
    }

    public void setHodometroHorimetroUsuarioEdicao(String hodometroHorimetroUsuarioEdicao) {
        this.hodometroHorimetroUsuarioEdicao = hodometroHorimetroUsuarioEdicao;
    }

    public Date getHodometroHorimetroDataHoraEdicao() {
        return hodometroHorimetroDataHoraEdicao;
    }

    public void setHodometroHorimetroDataHoraEdicao(Date hodometroHorimetroDataHoraEdicao) {
        this.hodometroHorimetroDataHoraEdicao = hodometroHorimetroDataHoraEdicao;
    }

    public Long getHodometroRastreador() {
        return hodometroRastreador;
    }

    public void setHodometroRastreador(Long hodometroRastreador) {
        this.hodometroRastreador = hodometroRastreador;
    }

    public BigDecimal getHorimetroRastreador() {
        return horimetroRastreador;
    }

    public void setHorimetroRastreador(BigDecimal horimetroRastreador) {
        this.horimetroRastreador = horimetroRastreador;
    }

    public BigDecimal getHodometroHorimetroAnteriorEdicao() {
        return hodometroHorimetroAnteriorEdicao;
    }

    public void setHodometroHorimetroAnteriorEdicao(BigDecimal hodometroHorimetroAnteriorEdicao) {
        this.hodometroHorimetroAnteriorEdicao = hodometroHorimetroAnteriorEdicao;
    }

    public List<NotaFiscal> getNotasFiscais() {
        return notasFiscais;
    }

    public void setNotasFiscais(List<NotaFiscal> notasFiscais) {
        this.notasFiscais = notasFiscais;
    }

    public Integer getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(Integer statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }

    public DispositivoMotoristaPedido getPedido() {
        return pedido;
    }

    public void setPedido(DispositivoMotoristaPedido pedido) {
        this.pedido = pedido;
    }

    public AvaliacaoAbastecimento getAvaliacaoAbastecimento() {
        return avaliacaoAbastecimento;
    }

    public void setAvaliacaoAbastecimento(AvaliacaoAbastecimento avaliacaoAbastecimento) {
        this.avaliacaoAbastecimento = avaliacaoAbastecimento;
    }

    public String getCodigoFrentista() {
        return codigoFrentista;
    }

    public void setCodigoFrentista(String codigoFrentista) {
        this.codigoFrentista = codigoFrentista;
    }

    public String getNomeFrentista() {
        return nomeFrentista;
    }

    public void setNomeFrentista(String nomeFrentista) {
        this.nomeFrentista = nomeFrentista;
    }

    public TransacaoConsolidada getTransacaoConsolidada() {
        return transacaoConsolidada;
    }

    public void setTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        this.transacaoConsolidada = transacaoConsolidada;
    }

    public TransacaoConsolidada getTransacaoConsolidadaPostergada() {
        return transacaoConsolidadaPostergada;
    }

    public void setTransacaoConsolidadaPostergada(TransacaoConsolidada transacaoConsolidadaPostergada) {
        this.transacaoConsolidadaPostergada = transacaoConsolidadaPostergada;
    }

    public Date getDataPostergacao() {
        return dataPostergacao;
    }

    public void setDataPostergacao(Date dataPostergacao) {
        this.dataPostergacao = dataPostergacao;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoVenda);
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return frota != null ? Collections.singletonList(frota) : Collections.emptyList();
    }

    @Transient
    public String getNomePosto() {
        if(pontoVenda != null) {
            return pontoVenda.getNome();
        }
        return nomeUnidade != null ? nomeUnidade : frota.getNomeFantasia();
    }

    @Transient
    public String getNomePessoaComponente() {
        if(pontoVenda != null) {
            return pontoVenda.getComponenteAreaAbastecimento().getNomePessoa();
        }
        return null;
    }

    @Transient
    public boolean isPostoInterno() {
        return pontoVenda == null;
    }

    @Transient
    public Long getCodigoItemAbastecimento() {
        return codigoItemAbastecimento;
    }

    @Transient
    public void setCodigoItemAbastecimento(Long codigoItemAbastecimento) {
        this.codigoItemAbastecimento = codigoItemAbastecimento;
    }

    @Transient
    public BigDecimal getValorEmitido() {
        return getNotasFiscais() != null ? getNotasFiscais()
                .stream()
                .map(NotaFiscal::getValorTotal)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)) : BigDecimal.ZERO;
    }

    @Transient
    public Integer getQuantidadeNotasFiscais() {
        return getNotasFiscais() != null ? getNotasFiscaisSemJustificativa().size() : 0;
    }

    public String getUuidAbastecimento() {
        return uuidAbastecimento;
    }

    public void setUuidAbastecimento(String uuidAbastecimento) {
        this.uuidAbastecimento = uuidAbastecimento;
    }

    @Transient
    public BigDecimal getValorTotalAbastecimento() {
        if(valorUnitarioAbastecimento != null && totalLitrosAbastecimento != null) {
            return valorUnitarioAbastecimento.multiply(totalLitrosAbastecimento);
        }
        return null;
    }

    public String getMunicipioPV() {
        return municipioPV;
    }

    public void setMunicipioPV(String municipioPV) {
        this.municipioPV = municipioPV;
    }

    public String getEstadoPV() {
        return estadoPV;
    }

    public void setEstadoPV(String estadoPV) {
        this.estadoPV = estadoPV;
    }

    public Integer getStatusRastreador() {
        return statusRastreador;
    }

    public void setStatusRastreador(Integer statusRastreador) {
        this.statusRastreador = statusRastreador;
    }

    public Boolean getEmpresaAgregadaExigeNf() {
        return empresaAgregadaExigeNf;
    }

    public void setEmpresaAgregadaExigeNf(Boolean empresaAgregadaExigeNf) {
        this.empresaAgregadaExigeNf = empresaAgregadaExigeNf;
    }

    public Boolean getUnidadeExigeNf() {
        return unidadeExigeNf;
    }

    public void setUnidadeExigeNf(Boolean unidadeExigeNf) {
        this.unidadeExigeNf = unidadeExigeNf;
    }

    public Long getNumeroAbadiPv() {
        return numeroAbadiPv;
    }

    public void setNumeroAbadiPv(Long numeroAbadiPv) {
        this.numeroAbadiPv = numeroAbadiPv;
    }

    public Integer getStatusEdicao() {
        return statusEdicao;
    }

    public void setStatusEdicao(Integer statusEdicao) {
        this.statusEdicao = statusEdicao;
    }

    public BigDecimal getPrecoCombustivelTotal() {
        return precoCombustivelTotal;
    }

    public void setPrecoCombustivelTotal(BigDecimal precoCombustivelTotal) {
        this.precoCombustivelTotal = precoCombustivelTotal;
    }

    public Integer getChaveOrdenacaoFinanceiro() {
        return chaveOrdenacaoFinanceiro;
    }

    public void setChaveOrdenacaoFinanceiro(Integer chaveOrdenacaoFinanceiro) {
        this.chaveOrdenacaoFinanceiro = chaveOrdenacaoFinanceiro;
    }

    /**
     * Verifica a utilizacao de creditos pre pagos
     * @return true caso consumiu creditos pre pagos, false caso contrario
     */
    @Transient
    public boolean utilizouCreditosPrePagos() {
        if(getTransacaoFrota() != null) {
            return transacaoFrota.getConsumiuCreditoPrePago();
        }
        return false;
    }

    /**
     * Obtem o tipo do abastecimento (manual ou automatico)
     * @return tipo de abastecimento
     */
    @Transient
    public String getTipoPreenchimentoLitragem() {
        if(uuidAbastecimento != null) {
            return TipoPreenchimentoLitragem.AUTO.getLabel();
        }
        return TipoPreenchimentoLitragem.MANUAL.getLabel();
    }

    /**
     * Obtem modo de realizacao do pedido
     * @return modo de realizacao do pedido
     */
    @Transient
    public String getModoRealizacaoPedido() {
        return (getPedido() != null && TipoSenhaAutorizacao.CODIGO_ABASTECIMENTO.getValue().equals(getTipoSenhaAutorizacao()))
                ? TipoRealizacaoPedido.ONLINE.getLabel()
                : TipoRealizacaoPedido.OFFLINE.getLabel();
    }

    /**
     * Obtem a classificacao do veiculo
     * @return classificacao do veiculo
     */
    @Transient
    public String getClassificacaoAgregadoVeiculo() {
        return agregadoVeiculo !=  null && agregadoVeiculo > 0 ? ClassificacaoAgregado.AGREGADO.getLabel() : ClassificacaoAgregado.PROPRIO.getLabel();
    }

    /**
     * Indica que a nota fiscal está pendente de avaliação
     *
     * @param dataHoraCorrente Data e hora correntes
     * @return Se está pendente de avaliação
     */
    @Transient
    public boolean isAvalicaoPendente(Date dataHoraCorrente) {
        boolean geradaAte5DiasAtras = getDataProcessamento().compareTo(UtilitarioCalculoData.adicionarDiasData(dataHoraCorrente, -5)) >= 0;
        return getAvaliacaoAbastecimento() == null && geradaAte5DiasAtras;
    }

    public TipoErroAutorizacaoPagamento getTipoErroAutorizacaoPagamento() { return tipoErroAutorizacaoPagamento; }

    public void setTipoErroAutorizacaoPagamento(TipoErroAutorizacaoPagamento tipoErroAutorizacaoPagamento) { this.tipoErroAutorizacaoPagamento = tipoErroAutorizacaoPagamento; }

    /**
     * Retorna lista de registros que representam as justificativas para não-emissão de NF
     *
     * @return lista de registros que representam as justificativas para não-emissão de NF
     */
    @Transient
    public List<NotaFiscal> getNotasFiscaisComJustificativa() {
        return notasFiscais.stream().filter(NotaFiscal::getIsJustificativa).collect(Collectors.toList());
    }

    /**
     * Retorna lista de registros que representam as notas fiscais emitidas
     *
     * @return lista de registros que representam as notas fiscais emitidas
     */
    @Transient
    public List<NotaFiscal> getNotasFiscaisSemJustificativa() {
        return notasFiscais.stream().filter(nota -> !nota.getIsJustificativa()).collect(Collectors.toList());
    }

    /**
     * Informa se o objeto possui a informação de localização da origem do pedido de abastecimento.
     *
     * @return True, caso possua.
     */
    public boolean possuiLocalizacaoOrigem() {
        return latitudeOrigem != null && longitudeOrigem != null;
    }

    /**
     * Informa se o abastecimento está pendente de autorização.
     *
     * @return true, caso esteja pendente.
     */
    @Transient
    public boolean isPendenteAutorizacao() {
        return StatusAutorizacao.PENDENTE_AUTORIZACAO.getValue().equals(status);
    }

    @Transient
    public EmpresaAgregada getEmpresaAgregadaParaNF() {
        return getEmpresaAgregadaExigeNf() != null && getEmpresaAgregadaExigeNf() ? getEmpresaAgregada() : null;
    }

    @Transient
    public Unidade getUnidadeParaNF() {
        return getUnidadeExigeNf() != null && getUnidadeExigeNf() ? getUnidade() : null;
    }

    /**
     * Verifica se a autorização pagamento está autorizada ou cancelada
     * @return True, caso esteja autorizado ou cancelado.
     */
    @Transient
    public boolean estaAutorizadaOuCancelada() {
        if(getStatus() != null) {
            return estaAutorizado() || estaCancelado();
        }
        return false;
    }

    /**
     * Verifica se a autorização de pagamento está autorizada.
     *
     * @return True, caso esteja autorizado.
     */
    @Transient
    public boolean estaAutorizado() {
        if (getStatus() != null) {
            return getStatus().equals(StatusAutorizacao.AUTORIZADO.getValue());
        }
        return false;
    }

    /**
     * Verifica se a autorização de pagamento está cancelada.
     *
     * @return True, caso esteja cancelada.
     */
    @Transient
    public boolean estaCancelado() {
        if (getStatus() != null) {
            return getStatus().equals(StatusAutorizacao.CANCELADO.getValue());
        }
        return false;
    }

    /**
     * Verifica o status da nota fiscal da autorizacao
     *
     * @param statusNF Status a ser verificado
     * @return True, caso o status seja igual ao verificado
     */
    @Transient
    public boolean statusNotaFiscalEsta(StatusNotaFiscalAbastecimento statusNF) {
        return getStatusNotaFiscal().equals(statusNF.getValue());
    }

    /**
     * Verifica se a nota fiscal esta emitida
     *
     * @return True, caso a nota fiscal esteja com status igual a EMITIDA
     */
    @Transient
    public boolean notaFiscalEstaEmitido() {
        return statusNotaFiscalEsta(StatusNotaFiscalAbastecimento.EMITIDA);
    }

    /**
     * Informa se a autorização de pagamento possui pendência de emissão de nota fiscal
     * levando em consideração a exigência de emissão e status de autorização.
     * @param considerarCancelado indica se a pendência de NF deve ser avaliada também para abastecimentos
     * cancelados
     *
     * @return true, caso possua pendencia.
     */
    @Transient
    public boolean isPendenteEmissaoNF(boolean considerarCancelado) {
        boolean statusAutorizacao = considerarCancelado ? estaAutorizadaOuCancelada() : estaAutorizado();
        return statusAutorizacao &&
                valorTotal.compareTo(BigDecimal.ZERO) > 0 &&
                exigeEmissaoNF() &&
                (statusNotaFiscalEsta(StatusNotaFiscalAbastecimento.PENDENTE));
    }

    /**
     * verifica se uma transacao foi emitida em um ciclo com status consolidacao FECHADO (ou seja, se seu valor foi considerado em algum reembolso gerado)
     * @return true, caso a transacao nao tenha pendencia de emissao e seu ciclo mais atual (de origem ou postergacao) esteja FECHADO
     */
    public boolean emitidaEmCicloFechado(){
        return this.getTransacaoConsolidadaVigente().getStatusConsolidacao().equals(StatusTransacaoConsolidada.FECHADA.getValue())
                            && !isPendenteEmissaoNF(true);
    }

    /**
     * Obtem o valor do abastecimento considerando o desconto
     * @return o valor total do abastecimento com desconto
     */
    @Transient
    public BigDecimal getValorTotalComDesconto() {
        return getValorTotal().subtract(getValorDescontoTotal());
    }

    /**
     * Calcula da data de debito de um abastecimento a partir dos dados da frota
     *
     * @return A data calculada
     */
    public Date obterDataDebito(){

        int numDiasDoCiclo = getFrota().getParametroCiclo().getPrazoCiclo().intValue();
        int prazoPagamento= getFrota().getParametroCiclo().getPrazoPagamento().intValue();
        int diaAbast= UtilitarioCalculoData.obterCampoData(getDataProcessamento(), Calendar.DAY_OF_MONTH);
        int mesAbast=UtilitarioCalculoData.obterCampoData(getDataProcessamento(), Calendar.MONTH);
        int anoAbast=UtilitarioCalculoData.obterCampoData(getDataProcessamento(), Calendar.YEAR);
        int cicloDoAbast = (diaAbast/numDiasDoCiclo) + ((diaAbast%numDiasDoCiclo > 0) ? 1 : 0);
        int ultimoDiaMesDoMes = UtilitarioCalculoData.obterUltimoValorCampo(getDataProcessamento(), Calendar.DAY_OF_MONTH);
        int diasEmRelacaoAcicloAtual = (cicloDoAbast * numDiasDoCiclo);
        int diaDebito = Math.min(diasEmRelacaoAcicloAtual, ultimoDiaMesDoMes);
        Date dataDeDebito = UtilitarioCalculoData.obterData((diaDebito + prazoPagamento), mesAbast, anoAbast);

        return UtilitarioCalculoData.obterProximoDiaUtilSemFeriado(dataDeDebito);
    }

    /**
     * Obtém o item de combustível de uma autorização de pagamento
     *
     * @return O item da autorização que representa o abastecimento
     */
    public ItemAutorizacaoPagamento obterItemAbastecimento() {
        return this.getItems().stream().filter(ItemAutorizacaoPagamento::isAbastecimento).findFirst().get();
    }

    /**
     * Obtém uma lista de produtos e serviços de uma autorização pagamento
     *
     * @return A lista de itens da autorização que representa os produtos e serviços
     */
    public List<ItemAutorizacaoPagamento> obterItensServico(){
        return this.getItems().stream().filter(i -> !i.isAbastecimento()).collect(Collectors.toList());
    }

    public Long getCodigoAbastecimentoCTA() {
        return codigoAbastecimentoCTA;
    }

    public void setCodigoAbastecimentoCTA(Long codigoAbastecimentoCTA) {
        this.codigoAbastecimentoCTA = codigoAbastecimentoCTA;
    }

    public BigDecimal getValorDescontoTotal() {
        return valorDescontoTotal!=null? valorDescontoTotal : BigDecimal.ZERO;
    }

    public void setValorDescontoTotal(BigDecimal valorDescontoTotal) {
        this.valorDescontoTotal = valorDescontoTotal;
    }

    public CicloRepasse getCicloRepasse() {
        return cicloRepasse;
    }

    public void setCicloRepasse(CicloRepasse cicloRepasse) {
        this.cicloRepasse = cicloRepasse;
    }

    public CicloRepasse getCicloRepasseOriginal() {
        return cicloRepasseOriginal;
    }

    public void setCicloRepasseOriginal(CicloRepasse cicloRepasseOriginal) {
        this.cicloRepasseOriginal = cicloRepasseOriginal;
    }

    public Date getDataPostergacaoRepasse() {
        return dataPostergacaoRepasse;
    }

    public void setDataPostergacaoRepasse(Date dataPostergacaoRepasse) {
        this.dataPostergacaoRepasse = dataPostergacaoRepasse;
    }

    public Boolean getFoiProcessadoPeloGeradorDeCampanhas() {
        return foiProcessadoPeloGeradorDeCampanhas != null ? foiProcessadoPeloGeradorDeCampanhas : false;
    }

    public void setFoiProcessadoPeloGeradorDeCampanhas(Boolean foiProcessadoPeloGeradorDeCampanhas) {
        this.foiProcessadoPeloGeradorDeCampanhas = foiProcessadoPeloGeradorDeCampanhas;
    }

    /**
     * Verifica se a {@link AutorizacaoPagamento} deve ir para a fila de repasse
     * @return true se a autorizacao deve ir para a fila de repasse
     */
    public Boolean deveGerarRepasse(){
        return BigDecimal.ZERO.compareTo(this.getValorDescontoTotal()) != 0;
    }

    /**
     * Informa se a autorização de pagamento possui alguma nota fiscal com justificativa.
     * @return true, caso possua.
     */
    @Transient
    public boolean possuiNotasFiscaisComJustificativa() {
        return notasFiscais != null && !getNotasFiscaisComJustificativa().isEmpty();
    }

    /**
     * Calcula o consumo relativo a autorizacao de pagamento.
     * @return consumo relativo a autorizacao de pagamento.
     */
    @JsonIgnore
    public BigDecimal obterConsumo() {
        final BigDecimal diferencaHodometroHorimetro = obterDiferencaHodometroHorimetro();
        return diferencaHodometroHorimetro == null || totalLitrosAbastecimento == null ? null :
            diferencaHodometroHorimetro.divide(totalLitrosAbastecimento, 3, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Obtem a diferenca de hodometro ou horimetro da autorizacao de pagamento.
     * @return a diferenca de hodometro ou horimetro da autorizacao de pagamento.
     */
    @JsonIgnore
    private BigDecimal obterDiferencaHodometroHorimetro() {
        if (hodometro != null) {
            if (hodometroAnterior != null && hodometroAnterior != BigDecimal.ZERO.longValue()) {
                return new BigDecimal(hodometro - hodometroAnterior);
            }
        }
        if (horimetro != null) {
            if (horimetroAnterior != null && !horimetroAnterior.equals(BigDecimal.ZERO)) {
                return horimetro.subtract(horimetroAnterior);
            }
        }
        return null;
    }

    /**
     * Informa a autorização de pagamento possui exigencia de NF para a Unidade.
     *
     * @return true, caso possua
     */
    @Transient
    public boolean unidadePossuiExigenciaNF() {
        return unidadeExigeNf != null && unidadeExigeNf;
    }

    /**
     * Informa a autorização de pagamento possui exigencia de NF para a Empresa Agregada.
     *
     * @return true, caso possua
     */
    @Transient
    public boolean empresaAgregadaPossuiExigenciaNF() {
        return empresaAgregadaExigeNf != null && empresaAgregadaExigeNf;
    }

    /**
     * Informa se a autorização de pagamento exige emissão de nota.
     *
     * @return true, caso exija emissão de nf
     */
    @Transient
    public boolean exigeEmissaoNF() {
        return frota.exigeNotaFiscal() || unidadePossuiExigenciaNF() || empresaAgregadaPossuiExigenciaNF();
    }

    /**
     * Retorna qual a transação consolidada vigente do abastecimento.
     *
     * @return caso tenha sido postergado, retorna a transação consolidada da postergação,
     * caso contrário retorna a transação consolidada original.
     */
    @Transient
    public TransacaoConsolidada getTransacaoConsolidadaVigente() {
        if(transacaoConsolidadaPostergada != null) {
            return transacaoConsolidadaPostergada;
        }
        return transacaoConsolidada;
    }

    /**
     * Informa qual a modalidade de pagamento utilizada no abastecimento.
     *
     * @return a modalidade de pagamento
     */
    @Transient
    public ModalidadePagamento getModalidadePagamento() {
        Boolean prePago = getTransacaoFrota().getConsumiuCreditoPrePago();
        return prePago != null && prePago ? ModalidadePagamento.PRE_PAGO : ModalidadePagamento.POS_PAGO;
    }

    /**
     * Retorna um boolean primitivo informando se a empresa agregada do abastecimento exige emissão de NF.
     *
     * @return True, caso possua empresa agregada vinculada e exija emissão.
     */
    @Transient
    public boolean empresaAgregadaExigeNf() {
        return getEmpresaAgregadaExigeNf() != null && getEmpresaAgregadaExigeNf();
    }

    /**
     * Retorna um boolean primitivo informando se a unidade do abastecimento exige emissão de NF.
     *
     * @return True, caso possua unidade vinculada e exija emissão.
     */
    @Transient
    public boolean unidadeExigeNf() {
        return getUnidadeExigeNf() != null && getUnidadeExigeNf();
    }
}