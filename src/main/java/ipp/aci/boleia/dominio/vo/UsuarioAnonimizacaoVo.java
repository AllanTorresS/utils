package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de exibição que representa um UsuarioAnonimizacaoVo
 */
public class UsuarioAnonimizacaoVo {

    private Long id;
    private Long frotaId;
    private Long unidadeId;
    private Long grupoOperacionalId;
    private Long cpf;
    private Integer dddTelefoneCelular;
    private Long telefoneCelular;
    private String token;
    private Date dataExpiracaoToken;
    private Integer numeroErrosLogin;
    private Long redeId;
    private long tipoPerfilId;
    private String nome;
    private String login;
    private List<Long> idPerfis;
    private List<Long> idPermissoes;
    private List<Long> idPontosDeVenda;
    private String email;
    private String senhaHash;
    private String senhaSalt;
    private Date dataUltimoLogin;
    private Date bloqueioTemporario;
    private Date dataUltimoEmail;
    private String statusConvertido;
    private Boolean gestor;
    private Integer tipoDashboard;
    private String tipoDashboardConvertido;
    private Boolean excluido;
    private Long versao;
    private Long codigoValidacaoTokenJwtId;
    private Long coordenadoriaId;
    private List<Long> idCoordenadoriasCoordenador;
    private List<Long> idFrotasAssessoradas;
    private List<Long> idFrotasAssessoradasConsultorHunter;
    private List<Long> idFrotasAssessoradasConsultorFarmerPesado;
    private List<Long> idFrotasAssessoradasConsultorFarmerLeve;
    private Date dataCriacao;
    private Integer status;

    /**
     * Construtor básico para serialização JSON
     */
    public UsuarioAnonimizacaoVo() {

    }

    /**
     * Constroi a representacao do usuario
     * @param usuario O usuario
     */
    public UsuarioAnonimizacaoVo(Usuario usuario) {
        if (usuario == null) {
            return;
        }

        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.tipoPerfilId = usuario.getTipoPerfil() != null ? usuario.getTipoPerfil().getId() : null;
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.dataUltimoLogin = usuario.getDataUltimoLogin();
        this.status = usuario.getStatus();
        this.gestor = usuario.getGestor();
        this.excluido = usuario.getExcluido();
        this.versao = usuario.getVersao();
        this.token = usuario.getToken();
        this.dataExpiracaoToken = usuario.getDataExpiracaoToken();
        this.numeroErrosLogin = usuario.getNumeroErrosLogin();
        this.login = usuario.getLogin();
        this.senhaHash = usuario.getSenhaHash();
        this.senhaSalt = usuario.getSenhaSalt();
        this.bloqueioTemporario = usuario.getBloqueioTemporario();
        this.dataUltimoEmail = usuario.getDataUltimoEmail();
        this.statusConvertido = usuario.getStatusConvertido();
        this.codigoValidacaoTokenJwtId = usuario.getCodigoValidacaoTokenJwt() != null ? usuario.getCodigoValidacaoTokenJwt().getId() : null;
        this.redeId = usuario.getRede() != null ? usuario.getRede().getId() : null;
        this.grupoOperacionalId = usuario.getGrupoOperacional() != null ? usuario.getGrupoOperacional().getId() : null;
        this.frotaId = usuario.getFrota() != null ? usuario.getFrota().getId() : null;
        this.unidadeId = usuario.getUnidade() != null ? usuario.getUnidade().getId() : null;
        this.idPerfis = usuario.getPerfis() != null ? usuario.getPerfis().stream().map(p -> p.getId()).collect(Collectors.toList()) : null;
        this.idPermissoes = usuario.getPermissoes() != null ? usuario.getPermissoes().stream().map(p -> p.getId()).collect(Collectors.toList()) : null;
        this.idPontosDeVenda = usuario.getPontosDeVenda() != null ? usuario.getPontosDeVenda().stream().map(p -> p.getId()).collect(Collectors.toList()) : null;
        this.telefoneCelular = usuario.getTelefoneCelular();
        this.dddTelefoneCelular = usuario.getDddTelefoneCelular();
        this.dataCriacao = usuario.getDataCriacao();
        this.tipoDashboard = usuario.getTipoDashboard();
        this.tipoDashboardConvertido = usuario.getTipoDashboardConvertido();
        this.coordenadoriaId = usuario.getCoordenadoria() != null ? usuario.getCoordenadoria().getId() : null;
        this.idCoordenadoriasCoordenador = usuario.getCoordenadoriasCoordenador() != null ? usuario.getCoordenadoriasCoordenador().stream().map(c -> c.getId()).collect(Collectors.toList()) : null;
        this.idFrotasAssessoradas = usuario.getFrotasAssessoradas() != null ? usuario.getFrotasAssessoradas().stream().map(f -> f.getId()).collect(Collectors.toList()) : null;
        this.idFrotasAssessoradasConsultorHunter = usuario.getFrotasAssessoradasConsultorHunter() != null ? usuario.getFrotasAssessoradasConsultorHunter().stream().map(f -> f.getId()).collect(Collectors.toList()) : null;
        this.idFrotasAssessoradasConsultorFarmerPesado = usuario.getFrotasAssessoradasConsultorFarmerPesado() != null ? usuario.getFrotasAssessoradasConsultorFarmerPesado().stream().map(f -> f.getId()).collect(Collectors.toList()) : null;
        this.idFrotasAssessoradasConsultorFarmerLeve = usuario.getFrotasAssessoradasConsultorFarmerLeve() != null ? usuario.getFrotasAssessoradasConsultorFarmerLeve().stream().map(f -> f.getId()).collect(Collectors.toList()) : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrotaId() {
        return frotaId;
    }

    public void setFrotaId(Long frotaId) {
        this.frotaId = frotaId;
    }

    public Long getUnidadeId() {
        return unidadeId;
    }

    public void setUnidadeId(Long unidadeId) {
        this.unidadeId = unidadeId;
    }

    public Long getGrupoOperacionalId() {
        return grupoOperacionalId;
    }

    public void setGrupoOperacionalId(Long grupoOperacionalId) {
        this.grupoOperacionalId = grupoOperacionalId;
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

    public Long getRedeId() {
        return redeId;
    }

    public void setRedeId(Long redeId) {
        this.redeId = redeId;
    }

    public long getTipoPerfilId() {
        return tipoPerfilId;
    }

    public void setTipoPerfilId(long tipoPerfilId) {
        this.tipoPerfilId = tipoPerfilId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Long> getIdPerfis() {
        return idPerfis;
    }

    public void setIdPerfis(List<Long> idPerfis) {
        this.idPerfis = idPerfis;
    }

    public List<Long> getIdPermissoes() {
        return idPermissoes;
    }

    public void setIdPermissoes(List<Long> idPermissoes) {
        this.idPermissoes = idPermissoes;
    }

    public List<Long> getIdPontosDeVenda() {
        return idPontosDeVenda;
    }

    public void setIdPontosDeVenda(List<Long> idPontosDeVenda) {
        this.idPontosDeVenda = idPontosDeVenda;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(Date dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public Date getBloqueioTemporario() {
        return bloqueioTemporario;
    }

    public void setBloqueioTemporario(Date bloqueioTemporario) {
        this.bloqueioTemporario = bloqueioTemporario;
    }

    public Date getDataUltimoEmail() {
        return dataUltimoEmail;
    }

    public void setDataUltimoEmail(Date dataUltimoEmail) {
        this.dataUltimoEmail = dataUltimoEmail;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
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

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Long getCodigoValidacaoTokenJwtId() {
        return codigoValidacaoTokenJwtId;
    }

    public void setCodigoValidacaoTokenJwtId(Long codigoValidacaoTokenJwtId) {
        this.codigoValidacaoTokenJwtId = codigoValidacaoTokenJwtId;
    }

    public Long getCoordenadoriaId() {
        return coordenadoriaId;
    }

    public void setCoordenadoriaId(Long coordenadoriaId) {
        this.coordenadoriaId = coordenadoriaId;
    }

    public List<Long> getIdCoordenadoriasCoordenador() {
        return idCoordenadoriasCoordenador;
    }

    public void setIdCoordenadoriasCoordenador(List<Long> idCoordenadoriasCoordenador) {
        this.idCoordenadoriasCoordenador = idCoordenadoriasCoordenador;
    }

    public List<Long> getIdFrotasAssessoradas() {
        return idFrotasAssessoradas;
    }

    public void setIdFrotasAssessoradas(List<Long> idFrotasAssessoradas) {
        this.idFrotasAssessoradas = idFrotasAssessoradas;
    }

    public List<Long> getIdFrotasAssessoradasConsultorHunter() {
        return idFrotasAssessoradasConsultorHunter;
    }

    public void setIdFrotasAssessoradasConsultorHunter(List<Long> idFrotasAssessoradasConsultorHunter) {
        this.idFrotasAssessoradasConsultorHunter = idFrotasAssessoradasConsultorHunter;
    }

    public List<Long> getIdFrotasAssessoradasConsultorFarmerPesado() {
        return idFrotasAssessoradasConsultorFarmerPesado;
    }

    public void setIdFrotasAssessoradasConsultorFarmerPesado(List<Long> idFrotasAssessoradasConsultorFarmerPesado) {
        this.idFrotasAssessoradasConsultorFarmerPesado = idFrotasAssessoradasConsultorFarmerPesado;
    }

    public List<Long> getIdFrotasAssessoradasConsultorFarmerLeve() {
        return idFrotasAssessoradasConsultorFarmerLeve;
    }

    public void setIdFrotasAssessoradasConsultorFarmerLeve(List<Long> idFrotasAssessoradasConsultorFarmerLeve) {
        this.idFrotasAssessoradasConsultorFarmerLeve = idFrotasAssessoradasConsultorFarmerLeve;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
