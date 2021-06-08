package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Dispositivo Motorista Pedido
 */
@Entity
@Audited
@Table(name = "DISP_MOTORISTA_PEDIDO")
public class DispositivoMotoristaPedido implements IPersistente, IPertenceFrota, IPertenceMotorista{

    private static final long serialVersionUID = -6070837063335951181L;

    @Id
    @Column(name = "CD_PEDIDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISP_MOTORISTA_PEDIDOO")
    @SequenceGenerator(name = "SEQ_DISP_MOTORISTA_PEDIDOO", sequenceName = "SEQ_DISP_MOTORISTA_PEDIDOO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NM_PEDIDO")
    private String numero;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Column(name = "VR_HODOMETRO")
    private Long valorHodometro;

    @Column(name = "VR_HORIMETRO")
    private BigDecimal valorHorimetro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_EXPIRACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracao;

    @NotNull
    @Column(name = "ID_HABILITADO")
    private Integer habilitado;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ARQUIVO_FOTO")
    private Arquivo fotoHodometroHorimetro;

    @Size(max=250)
    @Column(name = "DS_JUSTIF_FOTO")
    private String justificativaFoto;

    @DecimalMax("90.0")
    @DecimalMin("-90.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LATIT")
    private BigDecimal latitude;

    @DecimalMax("180.0")
    @DecimalMin("-180.0")
    @Digits(integer = 4, fraction = 12)
    @Column(name = "QT_GRAU_LONGIT")
    private BigDecimal longitude;

    @Size(max=250)
    @Column(name = "DS_JUSTIF_GPS")
    private String justificativaGPS;

    @Column(name = "ID_PRECISAO_GPS")
    private BigDecimal precisaoGPS;

    @DecimalMax("1000.00")
    @DecimalMin("0.01")
    @Column(name = "QT_LITRAGEM")
    private BigDecimal litragem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_DISPOSITIVO_MOTORISTA")
    private DispositivoMotorista dispositivoMotorista;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<TransacaoFrotaLeve> transacoesFrotasLeves;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    private List<AutorizacaoPagamento> abastecimentos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public Motorista getMotorista() { return motorista; }

    public void setMotorista(Motorista motorista) { this.motorista = motorista; }

    public Frota getFrota() { return frota;  }

    public void setFrota(Frota frota) {this.frota = frota; }

    public Long getValorHodometro() { return valorHodometro;  }

    public void setValorHodometro(Long valorHodometro) { this.valorHodometro = valorHodometro; }

    public BigDecimal getValorHorimetro() { return valorHorimetro;  }

    public void setValorHorimetro(BigDecimal valorHorimetro) { this.valorHorimetro = valorHorimetro;  }

    public Veiculo getVeiculo() { return veiculo; }

    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo;  }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public Date getDataCriacao() { return dataCriacao; }

    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public Date getDataExpiracao() { return dataExpiracao; }

    public void setDataExpiracao(Date dataExpiracao) { this.dataExpiracao = dataExpiracao; }

    public Integer getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Integer habilitado) {
        this.habilitado = habilitado;
    }

    public Arquivo getFotoHodometroHorimetro() {
        return fotoHodometroHorimetro;
    }

    public void setFotoHodometroHorimetro(Arquivo fotoHodometroHorimetro) {
        this.fotoHodometroHorimetro = fotoHodometroHorimetro;
    }

    public String getJustificativaFoto() {
        return justificativaFoto;
    }

    public void setJustificativaFoto(String justificativaFoto) {
        this.justificativaFoto = justificativaFoto;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
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

    public String getJustificativaGPS() {
        return justificativaGPS;
    }

    public void setJustificativaGPS(String justificativaGPS) {
        this.justificativaGPS = justificativaGPS;
    }

    public BigDecimal getPrecisaoGPS() {
        return precisaoGPS;
    }

    public void setPrecisaoGPS(BigDecimal precisaoGPS) {
        this.precisaoGPS = precisaoGPS;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public List<TransacaoFrotaLeve> getTransacoesFrotasLeves() {
        return transacoesFrotasLeves;
    }

    public void setTransacoesFrotasLeves(List<TransacaoFrotaLeve> transacoesFrotasLeves) {
        this.transacoesFrotasLeves = transacoesFrotasLeves;
    }

    public DispositivoMotorista getDispositivoMotorista() {
        return dispositivoMotorista;
    }

    public void setDispositivoMotorista(DispositivoMotorista dispositivoMotorista) {
        this.dispositivoMotorista = dispositivoMotorista;
    }

    public List<AutorizacaoPagamento> getAbastecimentos() {
        return abastecimentos;
    }

    public void setAbastecimentos(List<AutorizacaoPagamento> abastecimentos) {
        this.abastecimentos = abastecimentos;
    }
}
