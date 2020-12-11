package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.util.UtilitarioFormatacao;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Unidade
 */
@Audited
@Entity
@Table(name = "UNIDADE")
public class Unidade implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final long serialVersionUID = 6245365791804756028L;

    @Id
    @Column(name = "CD_UNIDADE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_UNIDADE")
    @SequenceGenerator(name = "SEQ_UNIDADE", sequenceName = "SEQ_UNIDADE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
    private List<GrupoOperacional> grupo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
    private List<Motorista> motorista;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
    private List<Veiculo> veiculo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
    private List<Usuario> usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
    private List<ComandaDigital> comandaDigitais;

    @NotNull
    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @Size(max=250)
    @Column(name = "NM_UNIDADE")
    private String nome;

    @Column(name = "NO_INSCR_ESTADUAL")
    private Long inscrEstadual;

    @Column(name = "DS_EMAIL")
    private String email;

    @Min(10L)
    @Max(99L)
    @Column(name = "CD_DDD_TEL")
    private Integer dddTelefone;

    @Min(10000000L)
    @Max(999999999L)
    @Column(name = "NO_TEL")
    private Long telefone;

    @Min(0)
    @Max(99999999L)
    @Column(name = "NO_CEP")
    private Integer cep;

    @Column(name = "DS_END")
    private String logradouroEndereco;

    @Column(name = "NO_END")
    private Integer numeroEndereco;

    @Column(name = "DS_COMPL")
    private String complementoEndereco;

    @Column(name = "NM_BAIRRO")
    private String bairro;

    @Column(name = "NM_MUNIC")
    private String municipio;

    @Column(name = "SG_UF")
    private String uf;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_POSTO_INTERNO")
    private Boolean postoInterno;

    @Column(name = "ID_EXIGE_NF")
    private Boolean exigeNotaFiscal;

    @Column(name="DS_CONNECTCTA_TOKEN")
    private String connectCTAToken;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "unidade")
    private EmpresaUnidade empresaUnidade;

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

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getInscrEstadual() {
        return inscrEstadual;
    }

    public void setInscrEstadual(Long inscrEstadual) {
        this.inscrEstadual = inscrEstadual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getLogradouroEndereco() {
        return logradouroEndereco;
    }

    public void setLogradouroEndereco(String logradouroEndereco) {
        this.logradouroEndereco = logradouroEndereco;
    }

    public Integer getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(Integer numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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

    public Boolean getPostoInterno() { return postoInterno; }

    public void setPostoInterno(Boolean postoInterno) { this.postoInterno = postoInterno; }

    public List<GrupoOperacional> getGrupo() {
        return grupo;
    }

    public void setGrupo(List<GrupoOperacional> grupo) {
        this.grupo = grupo;
    }

    public List<Motorista> getMotorista() {
        return motorista;
    }

    public void setMotorista(List<Motorista> motorista) {
        this.motorista = motorista;
    }

    public List<Veiculo> getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(List<Veiculo> veiculo) {
        this.veiculo = veiculo;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public List<ComandaDigital> getComandaDigitais() {
        return comandaDigitais;
    }

    public void setComandaDigitais(List<ComandaDigital> comandaDigitais) {
        this.comandaDigitais = comandaDigitais;
    }

    public String getConnectCTAToken() {
        return connectCTAToken;
    }

    public void setConnectCTAToken(String connectCTAToken) {
        this.connectCTAToken = connectCTAToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EmpresaUnidade getEmpresaUnidade() {
        return empresaUnidade;
    }

    public void setEmpresaUnidade(EmpresaUnidade empresaUnidade) {
        this.empresaUnidade = empresaUnidade;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public Boolean getExigeNotaFiscal() { return exigeNotaFiscal; }

    public void setExigeNotaFiscal(Boolean exigeNotaFiscal) { this.exigeNotaFiscal = exigeNotaFiscal; }

    /**
     * Monta o nome de apresentacao de uma Unidade
     * @return O nome de apresentacao da Unidade em questão
     */
    @Transient
    public String getNomeApresentacao() {
        String cnpj = UtilitarioFormatacao.formatarCnpjApresentacao(getCnpj());
        String nome = getNome();
        cnpj += " - ";
        return cnpj + nome;
    }
}
