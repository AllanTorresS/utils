package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Edição de Autorização Pagamento
 */
@Entity
@Audited
@Table(name = "AUTORIZACAO_PGTO_EDICAO")
public class AutorizacaoPagamentoEdicao implements IPersistente {

    public static final String VALOR_MAXIMO_AUTORIZACAO = "99999.999";
    private static final long serialVersionUID = -6015113337076066392L;

    @Id
    @Column(name = "CD_AUTORIZACAO_PGTO_EDICAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTORIZACAO_PGTO_EDICAO")
    @SequenceGenerator(name = "SEQ_AUTORIZACAO_PGTO_EDICAO", sequenceName = "SEQ_AUTORIZACAO_PGTO_EDICAO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_EDICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEdicao;

    @NotNull
    @Column(name = "ID_STATUS_EDICAO")
    private Integer statusEdicao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_CHAMADO")
    private AutorizacaoChamado chamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Max(99999999999999L)
    @Column(name = "CD_CNPJ_FROTA")
    private Long cnpjFrota;

    @Size(max=250)
    @Column(name = "NM_RAZAO_SOCIAL_FROTA")
    private String razaoSocialFrota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Max(99999999L)
    @Column(name = "CD_PTOV_CORP")
    private Long codigoCorporativoPv;

    @Max(99999999L)
    @Column(name = "CD_PTOV_ABADI")
    private Long numeroAbadiPv;

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

    @Size(max=30)
    @Column(name = "DS_BANDEIRA")
    private String bandeiraAreaAbastecimento;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @Size(max=8)
    @Column(name = "DS_PLACA_VEICULO")
    private String placaVeiculo;

    @Column(name = "ID_AGREGADO_VEICULO")
    private Integer agregadoVeiculo;

    @Column(name = "CD_CNPJ_EMPRESA_VEICULO")
    private Long cnpjEmpresaVeiculo;

    @Size(max=250)
    @Column(name = "NM_RAZAO_EMPRESA_VEICULO")
    private String razaoSocialEmpresaVeiculo;

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

    @Size(max=250)
    @Column(name = "DS_SUB_TIPO_VEICULO")
    private String subTipoVeiculo;

    @Column(name="VA_COTA_VEICULO")
    private BigDecimal cotaVeiculo;

    @Column(name="VA_CONSUMO_VEICULO")
    private BigDecimal consumidoVeiculo;

    @Column(name = "NO_HODOMETRO")
    private Long hodometro;

    @Column(name = "NO_HORIMETRO")
    private BigDecimal horimetro;

    @Column(name = "NO_HODOMETRO_ANT")
    private Long hodometroAnterior;

    @Column(name = "NO_HORIMETRO_ANT")
    private BigDecimal horimetroAnterior;

    @Column(name="VA_HODOM_HORIM_ANTERIOR_EDICAO")
    private BigDecimal hodometroHorimetroAnteriorEdicao;

    @Column(name="NM_HODOM_HORIM_USUARIO_EDICAO")
    private String hodometroHorimetroUsuarioEdicao;

    @Column(name="DT_HODOM_HORIM_EDICAO")
    private Date hodometroHorimetroDataHoraEdicao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANSACAO_FROTA")
    private TransacaoFrota transacaoFrota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL")
    private TransacaoConsolidada transacaoConsolidada;

    @DecimalMax(VALOR_MAXIMO_AUTORIZACAO)
    @Column(name = "VA_DESCONTO_TOTAL")
    private BigDecimal valorDescontoTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CICLO_REPASSE")
    private CicloRepasse cicloRepasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @Size(max=250)
    @Column(name = "NM_UNIDADE")
    private String nomeUnidade;

    @Column(name="ID_UNIDADE_EXIGE_NF")
    private Boolean unidadeExigeNf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @Column(name="ID_EMPR_AGREG_EXIGE_NF")
    private Boolean empresaAgregadaExigeNf;

    @Column(name = "ID_RASTREADOR")
    @Min(value = -1)
    @Max(value = 1)
    private Integer statusRastreador;

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

    @Column(name = "NO_HODOMETRO_RASTREADOR")
    private Long hodometroRastreador;

    @Column(name = "NO_HORIMETRO_RASTREADOR")
    private BigDecimal horimetroRastreador;

    @Size(max=50)
    @Column(name = "CD_VIP")
    private String codigoVip;

    @Size(max=250)
    @Column(name = "DS_MOTIVO_ERRO_APROVACAO")
    private String motivoErroAprovacao;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @OneToMany(mappedBy = "autorizacaoPagamentoEdicao", fetch = FetchType.LAZY)
    private List<ItemAutorizacaoPagamentoEdicao> items;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_REVISOR")
    private Usuario usuarioRevisor;

    /**
     * Construtor de uma AutorizacaoPagamentoEdicao
     */
    public AutorizacaoPagamentoEdicao(){

    }

    /**
     * Construtor de uma AutorizacaoPagamentoEdicao
     * @param abastecimento Uma AutorizacaoPagamento associada
     * @param chamado O chamado associado
     * @param statusEdicao O valor do enumerado StatusEdicao que representa o status de edição
     */
    public AutorizacaoPagamentoEdicao(AutorizacaoPagamento abastecimento, AutorizacaoChamado chamado, Integer statusEdicao) {
        this.setAutorizacaoPagamento(abastecimento);
        this.setStatusEdicao(statusEdicao);
        this.setChamado(chamado);
        this.setFrota(abastecimento.getFrota());
        this.setCnpjFrota(abastecimento.getCnpjFrota());
        this.setRazaoSocialFrota(abastecimento.getRazaoSocialFrota());
        this.setPontoVenda(abastecimento.getPontoVenda());
        this.setCodigoCorporativoPv(abastecimento.getCodigoCorporativoPv());
        this.setNumeroAbadiPv(abastecimento.getNumeroAbadiPv());
        this.setCnpjAreaAbastecimento(abastecimento.getCnpjAreaAbastecimento());
        this.setNomePv(abastecimento.getNomePv());
        this.setEnderecoPv(abastecimento.getEnderecoPv());
        this.setMunicipioPV(abastecimento.getMunicipioPV());
        this.setEstadoPV(abastecimento.getEstadoPV());
        this.setLatitudePosto(abastecimento.getLatitudePosto());
        this.setLongitudePosto(abastecimento.getLongitudePosto());
        this.setBandeiraAreaAbastecimento(abastecimento.getBandeiraAreaAbastecimento());
        this.setTotalLitrosAbastecimento(abastecimento.getTotalLitrosAbastecimento());
        this.setNomeItemAbastecimento(abastecimento.getNomeItemAbastecimento());
        this.setValorUnitarioAbastecimento(abastecimento.getValorUnitarioAbastecimento());
        this.setQuantidadeItensAdicionais(abastecimento.getQuantidadeItensAdicionais());
        this.setValorTotal(abastecimento.getValorTotal());
        this.setMotorista(abastecimento.getMotorista());
        this.setCpfMotorista(abastecimento.getCpfMotorista());
        this.setNomeMotorista(abastecimento.getNomeMotorista());
        this.setAgregadoMotorista(abastecimento.getAgregadoMotorista());
        this.setCnpjUnidadeMotorista(abastecimento.getCnpjUnidadeMotorista());
        this.setNomeUnidadeMotorista(abastecimento.getNomeUnidadeMotorista());
        this.setCodigoGrupoMotorista(abastecimento.getCodigoGrupoMotorista());
        this.setNomeGrupoMotorista(abastecimento.getNomeGrupoMotorista());
        this.setCnpjEmpresaMotorista(abastecimento.getCnpjEmpresaMotorista());
        this.setRazaoSocialEmpresaMotorista(abastecimento.getRazaoSocialEmpresaMotorista());
        this.setVeiculo(abastecimento.getVeiculo());
        this.setPlacaVeiculo(abastecimento.getPlacaVeiculo());
        this.setAgregadoVeiculo(abastecimento.getAgregadoVeiculo());
        this.setCnpjEmpresaVeiculo(abastecimento.getCnpjEmpresaVeiculo());
        this.setRazaoSocialEmpresaVeiculo(abastecimento.getRazaoSocialEmpresaVeiculo());
        this.setCnpjUnidadeVeiculo(abastecimento.getCnpjUnidadeVeiculo());
        this.setNomeUnidadeVeiculo(abastecimento.getNomeUnidadeVeiculo());
        this.setCodigoGrupoVeiculo(abastecimento.getCodigoGrupoVeiculo());
        this.setNomeGrupoVeiculo(abastecimento.getNomeGrupoVeiculo());
        this.setSubTipoVeiculo(abastecimento.getSubTipoVeiculo());
        this.setCotaVeiculo(abastecimento.getCotaVeiculo());
        this.setConsumidoVeiculo(abastecimento.getConsumidoVeiculo());
        this.setHodometro(abastecimento.getHodometro());
        this.setHorimetro(abastecimento.getHorimetro());
        this.setHodometroAnterior(abastecimento.getHodometroAnterior());
        this.setHorimetroAnterior(abastecimento.getHorimetroAnterior());
        this.setHodometroHorimetroAnteriorEdicao(abastecimento.getHodometroHorimetroAnteriorEdicao());
        this.setHodometroHorimetroUsuarioEdicao(abastecimento.getHodometroHorimetroUsuarioEdicao());
        this.setHodometroHorimetroDataHoraEdicao(abastecimento.getHodometroHorimetroDataHoraEdicao());
        this.setTransacaoFrota(abastecimento.getTransacaoFrota());
        this.setTransacaoConsolidada(abastecimento.getTransacaoConsolidada());
        this.setValorDescontoTotal(abastecimento.getValorDescontoTotal());
        this.setCicloRepasse(abastecimento.getCicloRepasse());
        this.setUnidade(abastecimento.getUnidade());
        this.setNomeUnidade(abastecimento.getNomeUnidade());
        this.setUnidadeExigeNf(abastecimento.getUnidadeExigeNf());
        this.setEmpresaAgregada(abastecimento.getEmpresaAgregada());
        this.setEmpresaAgregadaExigeNf(abastecimento.getEmpresaAgregadaExigeNf());
        this.setStatusRastreador(abastecimento.getStatusRastreador());
        this.setLatitudeRastreador(abastecimento.getLatitudeRastreador());
        this.setLongitudeRastreador(abastecimento.getLongitudeRastreador());
        this.setHodometroRastreador(abastecimento.getHodometroRastreador());
        this.setHorimetroRastreador(abastecimento.getHorimetroRastreador());
        this.setCodigoVip(abastecimento.getCodigoVip());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(Date dataEdicao) {
        this.dataEdicao = dataEdicao;
    }

    public Integer getStatusEdicao() {
        return statusEdicao;
    }

    public void setStatusEdicao(Integer statusEdicao) {
        this.statusEdicao = statusEdicao;
    }

    public AutorizacaoChamado getChamado() {
        return chamado;
    }

    public void setChamado(AutorizacaoChamado chamado) {
        this.chamado = chamado;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
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

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Long getCodigoCorporativoPv() {
        return codigoCorporativoPv;
    }

    public void setCodigoCorporativoPv(Long codigoCorporativoPv) {
        this.codigoCorporativoPv = codigoCorporativoPv;
    }

    public Long getNumeroAbadiPv() {
        return numeroAbadiPv;
    }

    public void setNumeroAbadiPv(Long numeroAbadiPv) {
        this.numeroAbadiPv = numeroAbadiPv;
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

    public String getBandeiraAreaAbastecimento() {
        return bandeiraAreaAbastecimento;
    }

    public void setBandeiraAreaAbastecimento(String bandeiraAreaAbastecimento) {
        this.bandeiraAreaAbastecimento = bandeiraAreaAbastecimento;
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

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
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

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
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

    public String getSubTipoVeiculo() {
        return subTipoVeiculo;
    }

    public void setSubTipoVeiculo(String subTipoVeiculo) {
        this.subTipoVeiculo = subTipoVeiculo;
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

    public BigDecimal getHodometroHorimetroAnteriorEdicao() {
        return hodometroHorimetroAnteriorEdicao;
    }

    public void setHodometroHorimetroAnteriorEdicao(BigDecimal hodometroHorimetroAnteriorEdicao) {
        this.hodometroHorimetroAnteriorEdicao = hodometroHorimetroAnteriorEdicao;
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

    public TransacaoFrota getTransacaoFrota() {
        return transacaoFrota;
    }

    public void setTransacaoFrota(TransacaoFrota transacaoFrota) {
        this.transacaoFrota = transacaoFrota;
    }

    public TransacaoConsolidada getTransacaoConsolidada() {
        return transacaoConsolidada;
    }

    public void setTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        this.transacaoConsolidada = transacaoConsolidada;
    }

    public BigDecimal getValorDescontoTotal() {
        return valorDescontoTotal != null ? valorDescontoTotal : BigDecimal.ZERO;
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

    public Boolean getUnidadeExigeNf() {
        return unidadeExigeNf;
    }

    public void setUnidadeExigeNf(Boolean unidadeExigeNf) {
        this.unidadeExigeNf = unidadeExigeNf;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public Boolean getEmpresaAgregadaExigeNf() {
        return empresaAgregadaExigeNf;
    }

    public void setEmpresaAgregadaExigeNf(Boolean empresaAgregadaExigeNf) {
        this.empresaAgregadaExigeNf = empresaAgregadaExigeNf;
    }

    public Integer getStatusRastreador() {
        return statusRastreador;
    }

    public void setStatusRastreador(Integer statusRastreador) {
        this.statusRastreador = statusRastreador;
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

    public BigDecimal getHorimetroRastreador() {
        return horimetroRastreador;
    }

    public void setHorimetroRastreador(BigDecimal horimetroRastreador) {
        this.horimetroRastreador = horimetroRastreador;
    }

    public Long getHodometroRastreador() {
        return hodometroRastreador;
    }

    public void setHodometroRastreador(Long hodometroRastreador) {
        this.hodometroRastreador = hodometroRastreador;
    }

    public String getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(String codigoVip) {
        this.codigoVip = codigoVip;
    }

    public List<ItemAutorizacaoPagamentoEdicao> getItems() {
        return items;
    }

    public void setItems(List<ItemAutorizacaoPagamentoEdicao> items) {
        this.items = items;
    }

    public String getMotivoErroAprovacao() {
        return motivoErroAprovacao;
    }

    public void setMotivoErroAprovacao(String motivoErroAprovacao) {
        this.motivoErroAprovacao = motivoErroAprovacao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Usuario getUsuarioRevisor() { return usuarioRevisor; }

    public void setUsuarioRevisor(Usuario usuarioRevisor) { this.usuarioRevisor = usuarioRevisor; }

    /**
     * Obtém o valor do abastecimento considerando o desconto
     * @return o valor total do abastecimento com desconto
     */
    @Transient
    public BigDecimal getValorTotalComDesconto() {
        return getValorTotal().subtract(getValorDescontoTotal());
    }

}
