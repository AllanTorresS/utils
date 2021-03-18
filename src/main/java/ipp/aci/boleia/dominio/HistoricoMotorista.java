package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Historico de Motorista
 */
@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Table(name = "HISTORICO_MOTORISTA")
public class HistoricoMotorista implements IPersistente, IPertenceFrota, IPertenceMotorista {

    private static final long serialVersionUID = -5710886542823346375L;

    @Id
    @Column(name = "CD_HISTORICO_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_MOTORISTA")
    @SequenceGenerator(name = "SEQ_HISTORICO_MOTORISTA", sequenceName = "SEQ_HISTORICO_MOTORISTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA")
    private Motorista motorista;

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

    @Column(name = "CD_CPF")
    private Long cpf;

    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusAtivacao.DECODE_FORMULA)
    private String statusConvertido;

    @NotAudited
    @Formula(ClassificacaoAgregado.DECODE_FORMULA)
    private String agregadoConvertido;

    @NotNull
    @Column(name = "ID_AGREGADO")
    private Integer agregado;

    @Size(max = 250)
    @Column(name = "NM_MOTORISTA")
    private String nome;

    @Size(max = 150)
    @Column(name = "NM_APELIDO")
    private String apelido;

    @Size(max = 25)
    @Column(name = "CD_MATRICULA")
    private String matricula;

    @Column(name = "DT_NASC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;

    @Column(name = "CD_DDD_TEL_CEL")
    private Integer dddTelefoneCelular;

    @Column(name = "NO_TEL_CEL")
    private Integer telefoneCelular;

    @Column(name = "DS_EMAIL")
    private String email;

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

    @Column(name = "CD_CNH")
    private Long numeroCnh;

    @Column(name = "DT_VENC_CNH")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimentoCnh;

    @Column(name = "DS_SENHA")
    private String senhaCriptografada;

    @Column(name = "DS_SENHA_SALT")
    private String saltSenhaCriptografada;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_MOTORISTA")
    private UsuarioMotorista usuarioMotorista;

    @Column(name = "ID_USA_APP")
    private Boolean utilizaAppMotorista;

    @Column(name = "DS_SENHA_ABAST_PDV")
    private String senhaAbastecimentoPdv;

    @Column(name = "VA_NUMERO_TENTATIVAS")
    private Integer numeroTentativasSenha;

    @Column(name="ID_FRENTISTA_CTA")
    private Boolean frentistaCTA;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    /**
     * Construtor padrão da entidade.
     */
    public HistoricoMotorista() {
        // nada a fazer
    }

    /**
     * Construtor da classe
     *
     * @param cpf Cpf do motorista
     * @param nome Nome do motorista
     * @param frota Frota do motorista
     * @param ativo Status de ativação do motorista
     * @param excluido Informa se o motorista está excluído ou não
     * @param agregado Informa se o motorista é agregado ou próprio
     * @param usaApp Informa se o motorista usa o app do motorista
     * @param frentistaCTA Informa se é um frentista CTA
     */
    public HistoricoMotorista(Long cpf, String nome, Frota frota, StatusAtivacao ativo, Boolean excluido, Integer agregado, Boolean usaApp, Boolean frentistaCTA) {
        this.cpf = cpf;
        this.nome = nome;
        this.frota = frota;
        this.status = ativo.getValue();
        this.excluido = excluido;
        this.agregado = agregado;
        this.utilizaAppMotorista = usaApp;
        this.frentistaCTA = frentistaCTA;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) { this.id = id; }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
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

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAgregadoConvertido() {
        return agregadoConvertido;
    }

    public void setAgregadoConvertido(String agregadoConvertido) {
        this.agregadoConvertido = agregadoConvertido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getDddTelefoneCelular() {
        return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(Integer dddTelefoneCelular) {
        this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public Integer getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Integer telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getNumeroCnh() {
        return numeroCnh;
    }

    public void setNumeroCnh(Long numeroCnh) {
        this.numeroCnh = numeroCnh;
    }

    public Date getDataVencimentoCnh() {
        return dataVencimentoCnh;
    }

    public void setDataVencimentoCnh(Date dataVencimentoCnh) {
        this.dataVencimentoCnh = dataVencimentoCnh;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }

    public void setSenhaCriptografada(String senhaCriptografada) {
        this.senhaCriptografada = senhaCriptografada;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public void setAgregado(Integer agregado) {
        this.agregado = agregado;
    }

    public String getSaltSenhaCriptografada() {
        return saltSenhaCriptografada;
    }

    public void setSaltSenhaCriptografada(String saltSenhaCriptografada) {
        this.saltSenhaCriptografada = saltSenhaCriptografada;
    }

    /**
     * Metodo para apagar a senha de contingencia
     */
    public void apagarSenhaContingencia() {
        this.senhaCriptografada = null;
        this.saltSenhaCriptografada = null;
    }

    @Override
    @Transient
    public Motorista getMotorista() {
        return this.motorista;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
    
    public UsuarioMotorista getUsuarioMotorista() { return usuarioMotorista; }

    public void setUsuarioMotorista(UsuarioMotorista usuarioMotorista) { this.usuarioMotorista = usuarioMotorista; }

    public Boolean getUtilizaAppMotorista() {
        return utilizaAppMotorista;
    }

    public void setUtilizaAppMotorista(Boolean utilizaAppMotorista) {
        this.utilizaAppMotorista = utilizaAppMotorista;
    }

    public String getSenhaAbastecimentoPdv() {
        return senhaAbastecimentoPdv;
    }

    public void setSenhaAbastecimentoPdv(String senhaAbastecimentoPdv) {
        this.senhaAbastecimentoPdv = senhaAbastecimentoPdv;
    }

    public Integer getNumeroTentativasSenha() {
        return numeroTentativasSenha;
    }

    public void setNumeroTentativasSenha(Integer numeroTentativasSenha) {
        this.numeroTentativasSenha = numeroTentativasSenha;
    }

    public Boolean getFrentistaCTA() {
        return frentistaCTA;
    }

    public void setFrentistaCTA(Boolean frentistaCTA) {
        this.frentistaCTA = frentistaCTA;
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

    /**
     * Monta o nome de apresentacao do Motorista,
     * @return O nome de apresentacao
     */
    @Transient
    public String getNomeApresentacao() {
        String  cpfApresentacao = UtilitarioFormatacao.formatarCpfApresentacao(getCpf());
        String nome = getNome();
        cpfApresentacao += " - ";
        return cpfApresentacao + nome;
    }
}
