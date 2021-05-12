package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Rota
 */
@Entity
@Audited
@Table(name = "ROTA")
public class Rota implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final long serialVersionUID = -2992575318587803234L;

    @Id
    @Column(name = "CD_ROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROTA")
    @SequenceGenerator(name = "SEQ_ROTA", sequenceName = "SEQ_ROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Size(max=250)
    @Column(name = "NM_ROTA")
    private String nome;

    @NotNull
    @DecimalMin("000000.000")
    @DecimalMax("999999.999")
    @Column(name = "KM_DISTANCIA")
    private BigDecimal distancia;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @OneToMany(mappedBy = "rota", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PontoRota> pontos;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @NotNull
    @Column(name = "ID_TIPO")
    private Integer tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PLANO")
    private PlanoViagem planoViagem;

    @NotNull
    @Column(name = "TEMPO_SEGUNDOS")
    private BigDecimal tempo;

    @Column(name = "ID_PRINCIPAL")
    private Boolean principal;

    @OneToOne
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @Transient
    private Long quantidadePostos;

    @OneToMany(mappedBy = "rota", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotaParametroFrota> rotaParametrosFrotas;

    @Column(name = "ID_POSTO_URBANO")
    private Boolean postoUrbano;

    @Column(name = "ID_IDA_VOLTA")
    private Boolean idaVolta;

    @OneToMany(mappedBy = "rota", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RotaPostoDesconsiderado> rotaPostoDesconsiderados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @Column(name = "VA_ATUAL_TANQUE")
    private BigDecimal estadoTanqueInicio;

    @Column(name = "VA_MEDIA_CONSUMO")
    private BigDecimal mediaConsumo;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public List<PontoRota> getPontos() {
        return pontos;
    }

    public void setPontos(List<PontoRota> pontos) {
        this.pontos = pontos;
    }

    public Integer getTipo() { return tipo; }

    public void setTipo(Integer tipo) { this.tipo = tipo; }

    public PlanoViagem getPlanoViagem() { return planoViagem; }

    public void setPlanoViagem(PlanoViagem planoViagem) { this.planoViagem = planoViagem; }

    public BigDecimal getTempo() { return tempo; }

    public void setTempo(BigDecimal tempo) { this.tempo = tempo; }

    public Boolean getPrincipal() { return principal; }

    public void setPrincipal(Boolean principal) { this.principal = principal; }

    @Transient
    public Long getQuantidadePostos() {
        return quantidadePostos;
    }

    @Transient
    public void setQuantidadePostos(Long quantidadePostos) {
        this.quantidadePostos = quantidadePostos;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public List<RotaParametroFrota> getRotaParametrosFrotas() {
        return rotaParametrosFrotas;
    }

    public void setRotaParametrosFrotas(List<RotaParametroFrota> rotaParametrosFrotas) {
        this.rotaParametrosFrotas = rotaParametrosFrotas;
    }

    public Boolean getPostoUrbano() {
        return postoUrbano;
    }

    public void setPostoUrbano(Boolean postoUrbano) {
        this.postoUrbano = postoUrbano;
    }

    public Boolean getIdaVolta() {
        return idaVolta;
    }

    public void setIdaVolta(Boolean idaVolta) {
        this.idaVolta = idaVolta;
    }

    public List<RotaPostoDesconsiderado> getRotaPostoDesconsiderados() {
        return rotaPostoDesconsiderados;
    }

    public void setRotaPostoDesconsiderados(List<RotaPostoDesconsiderado> rotaPostoDesconsiderados) {
        this.rotaPostoDesconsiderados = rotaPostoDesconsiderados;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public BigDecimal getEstadoTanqueInicio() {
        return estadoTanqueInicio;
    }

    public void setEstadoTanqueInicio(BigDecimal estadoTanqueInicio) {
        this.estadoTanqueInicio = estadoTanqueInicio;
    }

    public BigDecimal getMediaConsumo() {
        return mediaConsumo;
    }

    public void setMediaConsumo(BigDecimal mediaConsumo) {
        this.mediaConsumo = mediaConsumo;
    }
}
