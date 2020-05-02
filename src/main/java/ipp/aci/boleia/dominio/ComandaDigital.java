package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Comanda Digital
 */
@Entity
@Audited
@Table(name = "COMANDA_DIGITAL")
public class ComandaDigital implements IPersistente, IPertenceRevendedor, IPertenceFrota, IExclusaoLogica, IPossuiFingerprint {

    private static final long serialVersionUID = 8664388019459656303L;

    @Id
    @Column(name = "CD_COMANDA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMANDA_DIGITAL")
    @SequenceGenerator(name = "SEQ_COMANDA_DIGITAL", sequenceName = "SEQ_COMANDA_DIGITAL", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Column(name = "ID_HABILITADO")
    private Integer habilitado;

    @NotAudited
    @Formula(StatusHabilitacao.DECODE_FORMULA)
    private String habilitadoConvertido;

    @Column(name = "ID_BLOQUEADO")
    private Integer bloqueado;

    @NotAudited
    @Formula(StatusBloqueio.DECODE_FORMULA)
    private String bloqueadoConvertido;

    @Size(max=250)
    @Column(name = "NM_COMANDA")
    private String nome;

    @Size(max=150)
    @Column(name = "UDID_DISP")
    private String udidDispositivo;

    @Size(max=150)
    @Column(name = "NO_NUMERO_SERIE_DISP")
    private String numeroSerie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Size(max=150)
    @Column(name = "NM_MARCA_DISP")
    private String marcaDispositivo;

    @Size(max=150)
    @Column(name = "NM_MODELO_DISP")
    private String modeloDispositivo;

    @Size(max = 50)
    @Column(name = "NM_SO_DISP")
    private String soDispositivo;

    @Size(max=6)
    @Column(name = "CD_TOKEN")
    private String token;

    @Column(name = "CD_TOKEN_AUTENTICACAO")
    private String tokenAutenticacao;

    @Column(name = "DT_EXP_TOKEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracaoToken;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Integer getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Integer habilitado) {
        this.habilitado = habilitado;
    }

    public String getHabilitadoConvertido() {
        return habilitadoConvertido;
    }

    public void setHabilitadoConvertido(String habilitadoConvertido) {
        this.habilitadoConvertido = habilitadoConvertido;
    }

    public Integer getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Integer bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getBloqueadoConvertido() {
        return bloqueadoConvertido;
    }

    public void setBloqueadoConvertido(String bloqueadoConvertido) {
        this.bloqueadoConvertido = bloqueadoConvertido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUdidDispositivo() {
        return udidDispositivo;
    }

    public void setUdidDispositivo(String udidDispositivo) {
        this.udidDispositivo = udidDispositivo;
    }

    public String getMarcaDispositivo() {
        return marcaDispositivo;
    }

    public void setMarcaDispositivo(String marcaDispositivo) {
        this.marcaDispositivo = marcaDispositivo;
    }

    public String getModeloDispositivo() {
        return modeloDispositivo;
    }

    public void setModeloDispositivo(String modeloDispositivo) {
        this.modeloDispositivo = modeloDispositivo;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getSoDispositivo() {
        return soDispositivo;
    }

    public void setSoDispositivo(String soDispositivo) {
        this.soDispositivo = soDispositivo;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;

    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoVenda);
    }

    @Transient
    @Override
    public String[] getCamposFingerprint() {
        return new String[]{udidDispositivo, numeroSerie};
    }

    public String getTokenAutenticacao() {
        return tokenAutenticacao;
    }

    public void setTokenAutenticacao(String tokenAutenticacao) {
        this.tokenAutenticacao = tokenAutenticacao;
    }
}
