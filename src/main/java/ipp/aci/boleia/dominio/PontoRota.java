package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Ponto Rota, representando os pontos de venda na rota
 */
@Entity
@Audited
@Table(name = "PONTO_ROTA")
public class PontoRota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 1067830032185838658L;

    @Id
    @Column(name = "CD_PONTO_ROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PONTO_ROTA")
    @SequenceGenerator(name = "SEQ_PONTO_ROTA", sequenceName = "SEQ_PONTO_ROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ROTA")
    private Rota rota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @DecimalMin("000000.000")
    @DecimalMax("999999.999")
    @Column(name = "KM_PROXIMO_PONTO")
    private BigDecimal distanciaProximoPonto;

    @NotNull
    @Column(name = "ID_TIPO")
    private Integer tipo;

    @NotNull
    @Column(name = "ID_ORDEM")
    private Integer ordem;

    @Size(max=250)
    @Column(name = "NM_PONTO_ROTA")
    private String nome;

    @Size(max=150)
    @Column(name = "DS_COMENTARIO")
    private String comentario;

    @NotNull
    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitude;

    @NotNull
    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitude;

    @Column(name = "VA_ABAST_LITROS")
    private BigDecimal abastecerLitros;

    @Column(name = "VA_ABAST_VALOR")
    private BigDecimal abastecerValor;

    @Column(name = "DT_ATUALIZACAO_ABAST_VALOR")
    private Date dataAtualizacaoAbastecerValor;

    @Column(name = "ID_PRECO_NEGOCIADO")
    private Boolean precoNegociado;

    @NotNull
    @Column(name = "ID_API")
    private Integer api;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota postoInternoFrota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade postoInternoUnidade;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public List<Frota> getFrotas() {
        return rota.getFrotas();
    }

    public BigDecimal getDistanciaProximoPonto() {
        return distanciaProximoPonto;
    }

    public void setDistanciaProximoPonto(BigDecimal distanciaProximoPonto) {
        this.distanciaProximoPonto = distanciaProximoPonto;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getApi() { return api; }

    public void setApi(Integer api) { this.api = api; }

    public BigDecimal getAbastecerLitros() {
        return abastecerLitros;
    }

    public void setAbastecerLitros(BigDecimal abastecerLitros) {
        this.abastecerLitros = abastecerLitros;
    }

    public BigDecimal getAbastecerValor() {
        return abastecerValor;
    }

    public void setAbastecerValor(BigDecimal abastecerValor) {
        this.abastecerValor = abastecerValor;
    }

    public Date getDataAtualizacaoAbastecerValor() {
        return dataAtualizacaoAbastecerValor;
    }

    public void setDataAtualizacaoAbastecerValor(Date dataAtualizacaoAbastecerValor) {
        this.dataAtualizacaoAbastecerValor = dataAtualizacaoAbastecerValor;
    }

    public Boolean isPrecoNegociado() {
        return precoNegociado;
    }

    public void setPrecoNegociado(Boolean precoNegociado) {
        this.precoNegociado = precoNegociado;
    }

    public Frota getPostoInternoFrota() {
        return postoInternoFrota;
    }

    public void setPostoInternoFrota(Frota frota) {
        this.postoInternoFrota = frota;
    }

    public Unidade getPostoInternoUnidade() {
        return postoInternoUnidade;
    }

    public void setPostoInternoUnidade(Unidade unidade) {
        this.postoInternoUnidade = unidade;
    }
}
