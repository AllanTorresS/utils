package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
import ipp.aci.boleia.dominio.interfaces.IPossuiFingerprint;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Dispositivo Motorista
 */
@Audited
@Entity
@Table(name = "DISPOSITIVO_MOTORISTA")
public class DispositivoMotorista implements IPersistente, IPertenceFrota, IPertenceMotorista, IPossuiFingerprint {

    private static final long serialVersionUID = 7175312649901195659L;

    @Id
    @Column(name = "CD_DISPOSITIVO_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DISPOSITIVO_MOTORISTA")
    @SequenceGenerator(name = "SEQ_DISPOSITIVO_MOTORISTA", sequenceName = "SEQ_DISPOSITIVO_MOTORISTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

    @NotAudited
    @Formula(StatusHabilitacao.DECODE_FORMULA)
    private String statusHabilitacaoConvertido;

    @NotAudited
    @Formula(StatusBloqueio.DECODE_FORMULA)
    private String statusBloqueioConvertido;

    @NotNull
    @Column(name = "ID_BLOQUEADO")
    private Integer statusBloqueio;

    @NotNull
    @Column(name = "ID_HABILITADO")
    private Integer statusHabilitacao;

    @Size(max = 150)
    @Column(name = "DS_UDID")
    private String udid;

    @Size(max=150)
    @Column(name = "NO_NUMERO_SERIE")
    private String numeroSerie;

    @Size(max = 150)
    @Column(name = "NM_MARCA")
    private String marca;

    @Size(max = 150)
    @Column(name = "NM_MODELO")
    private String modelo;

    @Size(max = 50)
    @Column(name = "NM_SO")
    private String sistemaOperacional;

    @Size(max = 6)
    @Column(name = "CD_TOKEN")
    private String token;

    @Column(name = "DT_EXP_TOKEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracaoToken;

    @Column(name = "CD_TOKEN_ALLOWME")
    private String tokenAllowMe;

    @Column(name = "CD_ID_ALLOWME")
    private String idAllowMe;

    @Column(name = "DT_REENVIO_TOKEN")
    private Date dataReenvioToken;

    @Column(name = "CD_TOKEN_AUTENTICACAO")
    private String tokenAutenticacao;

    @Column(name = "CD_TOKEN_PUSH")
    private String tokenPush;

    @Column(name = "NO_VERSAO_APP")
    private String versaoDoApp;

    @Column(name = "NO_VERSAO")
    private String versao;

    @Column(name = "NTP_ATIVO")
    private Boolean ntpAtivo;

    @Column(name = "TIMEZONE")
    private String timeZone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dispositivoMotorista")
    private List<DispositivoMotoristaPedido> pedidos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public String getStatusHabilitacaoConvertido() {
        return statusHabilitacaoConvertido;
    }

    public void setStatusHabilitacaoConvertido(String statusHabilitacaoConvertido) {
        this.statusHabilitacaoConvertido = statusHabilitacaoConvertido;
    }

    public String getStatusBloqueioConvertido() {
        return statusBloqueioConvertido;
    }

    public void setStatusBloqueioConvertido(String statusBloqueioConvertido) {
        this.statusBloqueioConvertido = statusBloqueioConvertido;
    }

    public Integer getStatusBloqueio() {
        return statusBloqueio;
    }

    public void setStatusBloqueio(Integer statusBloqueio) {
        this.statusBloqueio = statusBloqueio;
    }

    public Integer getStatusHabilitacao() {
        return statusHabilitacao;
    }

    public void setStatusHabilitacao(Integer statusHabilitacao) {
        this.statusHabilitacao = statusHabilitacao;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDataExpiracaoToken() {
        return dataExpiracaoToken;
    }

    public void setDataExpiracaoToken(Date dataExpiracaoToken) {
        this.dataExpiracaoToken = dataExpiracaoToken;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public String getTokenAllowMe() {
        return tokenAllowMe;
    }

    public void setTokenAllowMe(String tokenAllowMe) {
        this.tokenAllowMe = tokenAllowMe;
    }

    public String getIdAllowMe() {
        return idAllowMe;
    }

    public void setIdAllowMe(String idAllowMe) {
        this.idAllowMe = idAllowMe;
    }

    public Date getDataReenvioToken() {
        return dataReenvioToken;
    }

    public void setDataReenvioToken(Date dataReenvioToken) {
        this.dataReenvioToken = dataReenvioToken;
    }

    public String getTokenAutenticacao() {
        return tokenAutenticacao;
    }

    public void setTokenAutenticacao(String tokenAutenticacao) {
        this.tokenAutenticacao = tokenAutenticacao;
    }

    public String getTokenPush() {
        return tokenPush;
    }

    public void setTokenPush(String tokenPush) {
        this.tokenPush = tokenPush;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getVersaoDoApp() {
        return versaoDoApp;
    }

    public void setVersaoDoApp(String versaoDoApp) {
        this.versaoDoApp = versaoDoApp;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    @Transient
    @Override
    public String[] getCamposFingerprint() {
        return new String[]{udid, numeroSerie};
    }

    public Boolean getNtpAtivo() {
        return ntpAtivo;
    }

    public void setNtpAtivo(Boolean ntpAtivo) {
        this.ntpAtivo = ntpAtivo;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<DispositivoMotoristaPedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<DispositivoMotoristaPedido> pedidos) {
        this.pedidos = pedidos;
    }
}
