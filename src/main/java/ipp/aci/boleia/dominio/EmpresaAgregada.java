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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
 * Representa a tabela de Empresa Agregada
 */
@Audited
@Entity
@Table(name = "EMPRESA_AGREGADA")
public class EmpresaAgregada implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final long serialVersionUID = -2073646290184511351L;

    @Id
    @Column(name = "CD_EMPR_AGREGADA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMPRESA_AGREGADA")
    @SequenceGenerator(name = "SEQ_EMPRESA_AGREGADA", sequenceName = "SEQ_EMPRESA_AGREGADA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaAgregada")
    private List<Motorista> motoristas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "empresaAgregada")
    private List<Veiculo> veiculos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMPRESA_AGREGADA_UNIDADE",
            joinColumns = @JoinColumn(name = "CD_EMPR_AGREGADA", referencedColumnName = "CD_EMPR_AGREGADA"),
            inverseJoinColumns = @JoinColumn(name = "CD_UNIDADE", referencedColumnName = "CD_UNIDADE"))
    private List<Unidade> unidades;

    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @Size(max = 250)
    @Column(name = "NM_RAZAO_SOCIAL")
    private String razaoSocial;

    @Size(max = 250)
    @Column(name = "NM_FANTASIA")
    private String fantasia;

    @Column(name = "NO_INSCR_ESTADUAL")
    private Long inscrEstadual;

    @Column(name = "NO_INSCR_MUNIC")
    private Long inscrMunic;

    @Size(max = 250)
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
    @Column(name = "CD_CEP")
    private Integer cep;

    @Size(max = 150)
    @Column(name = "DS_END")
    private String logradouroEndereco;

    @Column(name = "NO_END")
    private Integer numeroEndereco;

    @Size(max = 20)
    @Column(name = "DS_COMPL")
    private String complementoEndereco;

    @Size(max = 150)
    @Column(name = "NM_BAIRRO")
    private String bairro;

    @Size(max = 50)
    @Column(name = "NM_MUNIC")
    private String municipio;

    @Column(name = "SG_UF")
    private String uf;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @NotNull
    @Column(name = "ID_EXIGE_NOTA_FISCAL")
    private Boolean exigeNotaFiscal;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

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

    public List<Motorista> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(List<Motorista> motoristas) {
        this.motoristas = motoristas;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public List<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidade> unidades) {
        this.unidades = unidades;
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

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public Long getInscrEstadual() {
        return inscrEstadual;
    }

    public void setInscrEstadual(Long inscrEstadual) {
        this.inscrEstadual = inscrEstadual;
    }

    public Long getInscrMunic() {
        return inscrMunic;
    }

    public void setInscrMunic(Long inscrMunic) {
        this.inscrMunic = inscrMunic;
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

    public Boolean getExigeNotaFiscal() {
        return exigeNotaFiscal;
    }

    public void setExigeNotaFiscal(Boolean exigeNotaFiscal) {
        this.exigeNotaFiscal = exigeNotaFiscal;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    /**
     * Monta o nome de apresentacao de uma Empresa Agregada,
     * @return O nome de apresentacao da Empresa Agregada em questão
     */
    @Transient
    public String getNomeApresentacao() {
        String cnpjApresentacao = UtilitarioFormatacao.formatarCnpjApresentacao(getCnpj());
        String nome = getRazaoSocial();
        cnpjApresentacao += " - ";
        return cnpjApresentacao + nome;
    }
}
