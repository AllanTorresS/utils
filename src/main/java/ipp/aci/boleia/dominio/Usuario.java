package ipp.aci.boleia.dominio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoDashboard;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Representa a tabela de Usuario
 */
@Entity
@Audited
@Table(name = "USUARIO")
public class Usuario implements IPersistente, IExclusaoLogica, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = -956571306715313393L;

    @Id
    @Column(name = "CD_USUARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO")
    @SequenceGenerator(name = "SEQ_USUARIO", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_GRUPO_OPERACIONAL")
    private GrupoOperacional grupoOperacional;

    @Column(name = "CD_CPF")
    private Long cpf;

    @Min(10L)
    @Max(99L)
    @Column(name = "CD_DDD_TEL_CEL")
    private Integer dddTelefoneCelular;

    @Min(10000000L)
    @Max(999999999L)
    @Column(name = "NO_TEL_CEL")
    private Long telefoneCelular;

    @Size(max = 50)
    @Column(name = "DS_TOKEN")
    private String token;

    @Column(name = "DT_EXP_TOKEN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracaoToken;

    @Column(name = "NO_ERRO_LOGIN")
    private Integer numeroErrosLogin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REDE")
    private Rede rede;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_TIPO_PERFIL")
    private TipoPerfil tipoPerfil;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_USUARIO")
    private String nome;

    @Size(max = 50)
    @Column(name = "NM_LOGIN")
    private String login;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "USUARIO_PERFIL",
            joinColumns = @JoinColumn(name = "CD_USUARIO", referencedColumnName = "CD_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "CD_PERFIL", referencedColumnName = "CD_PERFIL"))
    private List<Perfil> perfis;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name="USUARIO_PONTO_VENDA", joinColumns={@JoinColumn(name="CD_USUARIO")}, inverseJoinColumns={@JoinColumn(name="CD_PTOV")})
    private List<PontoDeVenda> pontosDeVenda;

    @Size(max = 250)
    @Column(name = "DS_EMAIL")
    private String email;

    @Size(max = 256)
    @Column(name = "DS_SENHA")
    private String senhaHash;

    @Size(max = 50)
    @Column(name = "DS_SENHA_SALT")
    private String senhaSalt;

    @Column(name = "DT_ULTIMO_LOGIN")
    @Temporal(TemporalType.DATE)
    private Date dataUltimoLogin;

    @Column(name = "DT_BLOQUEIO_TEMPORARIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bloqueioTemporario;

    @Column(name = "DT_ULTIMO_EMAIL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimoEmail;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusAtivacao.DECODE_FORMULA)
    private String statusConvertido;

    @Column(name = "ID_GESTOR")
    private Boolean gestor;

    @Column(name = "ID_TIPO_DASHBOARD")
    private Integer tipoDashboard;

    @NotAudited
    @Formula(TipoDashboard.DECODE_FORMULA)
    private String tipoDashboardConvertido;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
    private CodigoValidacaoTokenJwt codigoValidacaoTokenJwt;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuario")
    private ContaBeneficioUsuario contaBeneficioUsuario;

    /**
     * Coordenadoria que um assessor esta associado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COORDENADORIA")
    private Coordenadoria coordenadoria;

    /**
     * Coordenadorias que um coordenador esta associado.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coordenador")
    @JsonIgnoreProperties("coordenador")
    private List<Coordenadoria> coordenadoriasCoordenador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioAssessorResponsavel")
    @JsonIgnoreProperties("usuarioAssessorResponsavel")
    private List<Frota> frotasAssessoradas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioConsultorHunter")
    @JsonIgnoreProperties("usuarioConsultorHunter")
    private List<Frota> frotasAssessoradasConsultorHunter;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioConsultorFarmerPesado")
    @JsonIgnoreProperties("usuarioConsultorFarmerPesado")
    private List<Frota> frotasAssessoradasConsultorFarmerPesado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioConsultorFarmerLeve")
    @JsonIgnoreProperties("usuarioConsultorFarmerLeve")
    private List<Frota> frotasAssessoradasConsultorFarmerLeve;

    @Transient
    private Set<Permissao> permissoes;

    @Transient
    private Motorista motorista;

    @Transient
    private Boolean primeiroLogin;

    @Transient
    private Set<Long> aceitesPendentesAdiados;

    @Transient
    private Set<Long> aceitesRealizados;

    @Transient
    private String tokenJWT;

    @Transient
    private TipoTokenJwt tipoTokenJwt;

    @Column( name = "DT_CRIACAO", insertable = false, updatable = false)
    private Date dataCriacao;

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

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public GrupoOperacional getGrupoOperacional() {
        return grupoOperacional;
    }

    public void setGrupoOperacional(GrupoOperacional grupoOperacional) {
        this.grupoOperacional = grupoOperacional;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Integer getDddTelefoneCelular() {
        return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(Integer dddTelefoneCelular) {
        this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public Long getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Long telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
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

    public Integer getNumeroErrosLogin() {
        return numeroErrosLogin;
    }

    public void setNumeroErrosLogin(Integer numeroErrosLogin) {
        this.numeroErrosLogin = numeroErrosLogin;
    }

    public void setTipoPerfil(TipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public TipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontoDeVenda> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }

    public Date getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(Date dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getGestor() {
        return gestor;
    }

    public void setGestor(Boolean gestor) {
        this.gestor = gestor;
    }

    public Integer getTipoDashboard() {
        return tipoDashboard;
    }

    public void setTipoDashboard(Integer tipoDashboard) {
        this.tipoDashboard = tipoDashboard;
    }

    public String getTipoDashboardConvertido() {
        return tipoDashboardConvertido;
    }

    public void setTipoDashboardConvertido(String tipoDashboardConvertido) {
        this.tipoDashboardConvertido = tipoDashboardConvertido;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getSenhaSalt() {
        return senhaSalt;
    }

    public void setSenhaSalt(String senhaSalt) {
        this.senhaSalt = senhaSalt;
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

    public CodigoValidacaoTokenJwt getCodigoValidacaoTokenJwt() {
        return codigoValidacaoTokenJwt;
    }

    public void setCodigoValidacaoTokenJwt(CodigoValidacaoTokenJwt codigoValidacaoTokenJwt) {
        this.codigoValidacaoTokenJwt = codigoValidacaoTokenJwt;
    }

    public Coordenadoria getCoordenadoria() {
        return coordenadoria;
    }

    public void setCoordenadoria(Coordenadoria coordenadoria) {
        this.coordenadoria = coordenadoria;
    }

    public List<Coordenadoria> getCoordenadoriasCoordenador() {
        return coordenadoriasCoordenador;
    }

    public void setCoordenadoriasCoordenador(List<Coordenadoria> coordenadoriasCoordenador) {
        this.coordenadoriasCoordenador = coordenadoriasCoordenador;
    }

    public List<Frota> getFrotasAssessoradas() {
        return frotasAssessoradas;
    }

    public void setFrotasAssessoradas(List<Frota> frotasAssessoradas) {
        this.frotasAssessoradas = frotasAssessoradas;
    }

    public List<Frota> getFrotasAssessoradasConsultorHunter() {
        return frotasAssessoradasConsultorHunter;
    }

    public void setFrotasAssessoradasConsultorHunter(List<Frota> frotasAssessoradasConsultorHunter) {
        this.frotasAssessoradasConsultorHunter = frotasAssessoradasConsultorHunter;
    }

    public List<Frota> getFrotasAssessoradasConsultorFarmerPesado() {
        return frotasAssessoradasConsultorFarmerPesado;
    }

    public void setFrotasAssessoradasConsultorFarmerPesado(List<Frota> frotasAssessoradasConsultorFarmerPesado) {
        this.frotasAssessoradasConsultorFarmerPesado = frotasAssessoradasConsultorFarmerPesado;
    }

    public List<Frota> getFrotasAssessoradasConsultorFarmerLeve() {
        return frotasAssessoradasConsultorFarmerLeve;
    }

    public void setFrotasAssessoradasConsultorFarmerLeve(List<Frota> frotasAssessoradasConsultorFarmerLeve) {
        this.frotasAssessoradasConsultorFarmerLeve = frotasAssessoradasConsultorFarmerLeve;
    }

    public Date getBloqueioTemporario() {
        return bloqueioTemporario;
    }

    public void setBloqueioTemporario(Date bloqueioTemporario) {
        this.bloqueioTemporario = bloqueioTemporario;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Date getDataUltimoEmail() {
        return dataUltimoEmail;
    }

    public void setDataUltimoEmail(Date dataUltimoEmail) {
        this.dataUltimoEmail = dataUltimoEmail;
    }

    @Transient
    public Motorista getMotorista() {
        return motorista;
    }

    @Transient
    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    @Transient
    public String getTokenJWT() {
        return tokenJWT;
    }

    @Transient
    public void setTokenJWT(String tokenJWT) {
        this.tokenJWT = tokenJWT;
    }

    /**
     * Busca as permissoes do usuario a partir dos perfis associados a ele
     *
     * @return um conjunto de permissoes
     */
    @Transient
    public Set<Permissao> getPermissoes() {
        if (permissoes == null) {
            permissoes = new HashSet<>();
            if (this.perfis != null) {
                for (Perfil perfil : this.perfis) {
                    List<Permissao> permissoesDoPerfil = perfil.getPermissoes();
                    if (!CollectionUtils.isEmpty(permissoesDoPerfil)) {
                        this.permissoes.addAll(permissoesDoPerfil);
                    }
                }
            }
        }
        return permissoes;
    }

    /**
     * Atribui as permissoes do usuario de maneira volatil
     *
     * @param permissoes o conjunto de permissoes
     */
    @Transient
    public void setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }


    @Transient
    public boolean isInterno() {
        return tipoPerfil != null && tipoPerfil.isInterno();
    }

    @Transient
    public boolean isFrotista() {
        return tipoPerfil != null && tipoPerfil.isFrotista();
    }

    @Transient
    public boolean isRevendedor() {
        return tipoPerfil != null && tipoPerfil.isRevendedor();
    }

    @Transient
    public boolean isPrecos() {
        return tipoPerfil != null && tipoPerfil.isPrecos();
    }

    @Transient
    public boolean isMotorista() {
        return tipoPerfil != null && tipoPerfil.isMotorista();
    }

    @Transient
    public boolean isGestor() {
        return gestor != null && gestor;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public ContaBeneficioUsuario getContaBeneficioUsuario() {
        return contaBeneficioUsuario;
    }

    public void setContaBeneficioUsuario(ContaBeneficioUsuario contaBeneficioUsuario) {
        this.contaBeneficioUsuario = contaBeneficioUsuario;
    }

    /**
     * Obtem o identificador para tabela de auditoria de modificações das entidades
     * @return Login para usuario Interno, Email para outros
     */
    public String obterIdentificadorAuditoria() {
        if(tipoPerfil!= null && tipoPerfil.getId().equals(TipoPerfilUsuario.INTERNO.getValue())) {
            return login;
        }
        return email;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    @Transient
    public Boolean getPrimeiroLogin() {
        return primeiroLogin;
    }

    @Transient
    public void setPrimeiroLogin(Boolean primeiroLogin) {
        this.primeiroLogin = primeiroLogin;
    }

    @Transient
    public Set<Long> getAceitesPendentesAdiados() {
        return aceitesPendentesAdiados;
    }

    @Transient
    public void setAceitesPendentesAdiados(Set<Long> aceitesPendentesAdiados) {
        this.aceitesPendentesAdiados = aceitesPendentesAdiados;
    }

    @Transient
    public Set<Long> getAceitesRealizados() {
        return aceitesRealizados;
    }

    @Transient
    public void setAceitesRealizados(Set<Long> aceitesRealizados) {
        this.aceitesRealizados = aceitesRealizados;
    }

    @Transient
    public TipoTokenJwt getTipoTokenJwt() {
        return tipoTokenJwt;
    }

    @Transient
    public void setTipoTokenJwt(TipoTokenJwt tipoTokenJwt) {
        this.tipoTokenJwt = tipoTokenJwt;
    }

    @Transient
    public TipoPerfilUsuario getTipoPerfilUsuario(){ return tipoPerfil.getTipoPerfilUsuario(); }

    @Transient
    public String getUsernameParaDispositivoFrotista() {
        return email + "_" + tipoTokenJwt.name();
    }

    /**
     * Informa se um usuário pode visualizar as informações de desconto.
     *
     * @return True, caso o usuário possa visualizar.
     */
    @Transient
    public Boolean podeVisualizarDescontos(){
        return this.isInterno() ||
                (this.isFrotista() &&
                        this.getFrota() != null);
    }

    /**
     * Remove a visao comercial do usuario, desassociando de sua coordenadoria.
     */
    @JsonIgnore
    public void removerVisaoComercial() {
        coordenadoria = null;
        tipoDashboard = TipoDashboard.INTERNO_SIMPLES.getValue();
    }

    /**
     * Lista os ids das frotas das coordenadorias que o usuario eh coordenador e as frotas que o usuario assessora.
     *
     * @return lista com os ids das frotas das coordenadorias que o usuario eh coordenador e as frotas que o usuario assessora.
     */
    @JsonIgnore
    public List<Long> listarIdsFrotasAssociadas() {
        return Stream.concat(listarIdsFrotasCoordenadas().stream(), listarIdsFrotasAssessoradas().stream()).collect(Collectors.toList());
    }

    /**
     * Valida se o usuario possui frotas associadas, seja como coordenador ou assessor.
     * @return true se o possuir frotas associadas.
     */
    @JsonIgnore
    public Boolean possuiFrotasAssociadas() {
        return isAssessor() || isCoordenador();
    }

    /**
     * Lista os ids das frotas das coordenadorias pelo usuario.
     *
     * @return lista com os ids das frotas assessoradas pelo usuario.
     */
    @JsonIgnore
    public List<Long> listarIdsFrotasCoordenadas() {
        if (isCoordenador()) {
            return this.coordenadoriasCoordenador.stream().map(Coordenadoria::listarFrotas).flatMap(List::stream).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Valida se o usuario eh coordenador de alguma coordenadoria.
     * @return true se o usuario for coordenador de alguma coordenadoria.
     */
    @JsonIgnore
    public Boolean isCoordenador() {
        return !CollectionUtils.isEmpty(this.coordenadoriasCoordenador);
    }

    /**
     * Lista os ids das frotas assessoradas pelo usuario.
     *
     * @return lista com os ids das frotas assessoradas pelo usuario.
     */
    @JsonIgnore
    public List<Long> listarIdsFrotasAssessoradas() {
        if (isAssessor()) {
            return Stream.of(this.frotasAssessoradas,
                            this.frotasAssessoradasConsultorHunter,
                            this.frotasAssessoradasConsultorFarmerPesado,
                            this.frotasAssessoradasConsultorFarmerLeve)
                    .flatMap(Collection::stream)
                    .map(Frota::getId)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Transforma um usuario em assessor, se ele ja nao tiver esse papel.
     */
    @JsonIgnore
    public void transformarEmAssessor() {
        if (!isAssessor()) {
            this.tipoDashboard = null;
        }
    }

    /**
     * Valida se o usuario eh assessor ou consultor de alguma frota.
     * @return true se o usuario for assessor ou consultor de alguma frota.
     */
    @JsonIgnore
    public Boolean isAssessor() {
        return !CollectionUtils.isEmpty(this.frotasAssessoradas)
                || !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorHunter)
                || !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorFarmerPesado)
                || !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorFarmerLeve);
    }

    /**
     * Valida se o usuario eh consultor de negocios hunter de alguma frota.
     * @return true se o usuario for assessor de alguma frota.
     */
    public boolean isConsultorHunter() {
        return !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorHunter);
    }

    /**
     * Valida se o usuario eh consultor de negocios farmer pesado de alguma frota.
     * @return true se o usuario for farmer pesado de alguma frota.
     */
    public boolean isConsultorFarmerPesado() {
        return !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorFarmerPesado);
    }

    /**
     * Valida se o usuario eh consultor de negocios farmer leve de alguma frota.
     * @return true se o usuario for farmer leve de alguma frota.
     */
    public boolean isConsultorFarmerLeve() {
        return !CollectionUtils.isEmpty(this.frotasAssessoradasConsultorFarmerLeve);
    }
}
