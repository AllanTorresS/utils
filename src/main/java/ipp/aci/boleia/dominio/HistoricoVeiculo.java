package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Historico do Veiculo
 *
 * @author allan.santos
 */
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "HISTORICO_VEICULO")
public class HistoricoVeiculo implements IPersistente , IPertenceFrota {

    private static final long serialVersionUID = -4123733181377658160L;

    @Id
    @Column(name = "CD_HISTORICO_VEICULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_VEICULO")
    @SequenceGenerator(name = "SEQ_HISTORICO_VEICULO", sequenceName = "SEQ_HISTORICO_VEICULO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_GRUPO")
    private GrupoOperacional grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @Column(name = "DS_PLACA")
    private String placa;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "ID_AGREGADO")
    private Integer agregado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_SUB_TIPO_VEICULO")
    private SubTipoVeiculo subtipoVeiculo;

    @Column(name = "NO_EIXOS")
    private Integer numeroEixos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MODELO")
    private ModeloVeiculo modeloVeiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MARCA")
    private MarcaVeiculo marcaVeiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MODELO_ANOS")
    private ModeloAnos modeloAnos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTOR")
    private MotorVeiculo motor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COMBUSTIVEL_MOTOR")
    private CombustivelMotor combustivelMotor;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "veiculo")
    private SaldoVeiculo saldoVeiculo;

    @Column(name = "QA_EST_LITRO")
    private BigDecimal consumoEstimadoLitro;

    @Column(name = "NO_CHASSI")
    private String numeroChassi;

    @Column(name = "CD_RENAVAM")
    private Long renavam;

    @Column(name = "DT_VENC_RENAVAM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimentoRenavam;

    @Column(name = "ID_POSSUI_SEGURO")
    private Boolean possuiSeguro;

    @Column(name = "DT_VENC_SEGURO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimentoSeguro;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "NO_HODOMETRO")
    private Long hodometro;

    @Column(name = "NO_HORIMETRO")
    private BigDecimal horimetro;

    @Column(name = "DT_ATUALIZ_HODOM_HORIM")
    private Date dataAtualizacaoHodometroHorimetro;

    @Column(name = "CD_RASTREADOR")
    private Integer rastreador;

    @Column(name = "VO_CAPACIDADE")
    private Integer capacidadeTanque;

    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "DS_PLACA_ANTERIOR")
    private String placaAnterior;

    @Column(name = "CD_IDENTIFICADOR_INTERNO")
    private String identificadorInterno;

    @Column(name = "ID_ABAST_DUAS_PLACAS")
    private Boolean habilitadoAbastecerDuasPlacas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public GrupoOperacional getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoOperacional grupo) {
        this.grupo = grupo;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public SubTipoVeiculo getSubtipoVeiculo() {
        return subtipoVeiculo;
    }

    public void setSubtipoVeiculo(SubTipoVeiculo subtipoVeiculo) {
        this.subtipoVeiculo = subtipoVeiculo;
    }

    public Integer getNumeroEixos() {
        return numeroEixos;
    }

    public void setNumeroEixos(Integer numeroEixos) {
        this.numeroEixos = numeroEixos;
    }

    public ModeloAnos getModeloAnos() {
        return modeloAnos;
    }

    public void setModeloAnos(ModeloAnos modeloAnos) {
        this.modeloAnos = modeloAnos;
    }

    public MotorVeiculo getMotor() {
        return motor;
    }

    public void setMotor(MotorVeiculo motor) {
        this.motor = motor;
    }

    public CombustivelMotor getCombustivelMotor() {
        return combustivelMotor;
    }

    public void setCombustivelMotor(CombustivelMotor combustivelMotor) {
        this.combustivelMotor = combustivelMotor;
    }

    public String getNumeroChassi() {
        return numeroChassi;
    }

    public void setNumeroChassi(String numeroChassi) {
        this.numeroChassi = numeroChassi;
    }

    public Long getRenavam() {
        return renavam;
    }

    public void setRenavam(Long renavam) {
        this.renavam = renavam;
    }

    public Date getDataVencimentoRenavam() {
        return dataVencimentoRenavam;
    }

    public void setDataVencimentoRenavam(Date dataVencimentoRenavam) {
        this.dataVencimentoRenavam = dataVencimentoRenavam;
    }

    public Boolean getPossuiSeguro() {
        return possuiSeguro;
    }

    public void setPossuiSeguro(Boolean possuiSeguro) {
        this.possuiSeguro = possuiSeguro;
    }

    public Date getDataVencimentoSeguro() {
        return dataVencimentoSeguro;
    }

    public void setDataVencimentoSeguro(Date dataVencimentoSeguro) {
        this.dataVencimentoSeguro = dataVencimentoSeguro;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public BigDecimal getConsumoEstimadoLitro() {
        return consumoEstimadoLitro;
    }

    public void setConsumoEstimadoLitro(BigDecimal consumoEstimadoLitro) {
        this.consumoEstimadoLitro = consumoEstimadoLitro;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public void setAgregado(Integer agregado) {
        this.agregado = agregado;
    }

    public ModeloVeiculo getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(ModeloVeiculo modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public MarcaVeiculo getMarcaVeiculo() {
        return marcaVeiculo;
    }

    public void setMarcaVeiculo(MarcaVeiculo marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
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

    public Integer getCapacidadeTanque() {
        return capacidadeTanque;
    }

    public void setCapacidadeTanque(Integer capacidadeTanque) {
        this.capacidadeTanque = capacidadeTanque;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacaoHodometroHorimetro() {
        return dataAtualizacaoHodometroHorimetro;
    }

    public void setDataAtualizacaoHodometroHorimetro(Date dataAtualizacaoHodometroHorimetro) {
        this.dataAtualizacaoHodometroHorimetro = dataAtualizacaoHodometroHorimetro;
    }

    public SaldoVeiculo getSaldoVeiculo() {
        return saldoVeiculo;
    }

    public void setSaldoVeiculo(SaldoVeiculo saldoVeiculo) {
        this.saldoVeiculo = saldoVeiculo;
    }

    public Integer getRastreador() {
        return rastreador;
    }

    public void setRastreador(Integer rastreador) {
        this.rastreador = rastreador;
    }

    public String getPlacaAnterior() {
        return placaAnterior;
    }

    public void setPlacaAnterior(String placaAnterior) {
        this.placaAnterior = placaAnterior;
    }

    public String getIdentificadorInterno() {
        return identificadorInterno;
    }

    public void setIdentificadorInterno(String identificadorInterno) {
        this.identificadorInterno = identificadorInterno;
    }

    public Boolean getHabilitadoAbastecerDuasPlacas() {
        return habilitadoAbastecerDuasPlacas;
    }

    public void setHabilitadoAbastecerDuasPlacas(Boolean habilitadoAbastecerDuasPlacas) {
        this.habilitadoAbastecerDuasPlacas = habilitadoAbastecerDuasPlacas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    /**
     * Verifica se o veículo é próprio.
     * @return true caso seja próprio, false caso contrário.
     */
    @Transient
    public boolean isProprio() {
        return ClassificacaoAgregado.PROPRIO.getValue().equals(this.agregado);
    }

    /**
     * Verifica se o veículo é agregado.
     * @return true caso seja agregado, false caso contrário.
     */
    @Transient
    public boolean isAgregado() {
        return !isProprio();
    }

    /**
     * Retorna o tipo de restrição do veículo.
     * @return o parâmetro de restrição do veículo.
     */
    @Transient
    public ParametroSistema getTipoRegraRestricaoSaldo() {
        return isProprio() ? ParametroSistema.COTA_VEICULO : ParametroSistema.CREDITO_VEICULO_AGREGADO;
    }

    /**
     * Verifica se o veículo utiliza hodômetro.
     * @return true caso utilize hodômetro, false caso contrário.
     */
    @Transient
    public boolean utilizaHorimetro(){
        return this.getSubtipoVeiculo().utilizaHorimetro();
    }

    /**
     * Monta o nome de apresentacao de um Veiculo,
     * @return O nome de apresentacao do Veiculo
     */
    @Transient
    public String getNomeApresentacao() {
        return getPlaca();
    }
}
