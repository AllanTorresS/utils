package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.DispositivoMotoristaPedido;
import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de exibição que representa um MotoristaAnonimizacaoVo
 */
public class MotoristaAnonimizacaoVo {

    private Long id;
    private Long frotaId;
    private Long unidadeId;
    private Long grupoOperacionalId;
    private Long empresaAgregadaId;
    private Long cpf;
    private Integer status;
    private String statusConvertido;
    private String agregadoConvertido;
    private Integer agregado;
    private String nome;
    private String apelido;
    private String matricula;
    private Date dataNascimento;
    private Integer dddTelefoneCelular;
    private Integer telefoneCelular;
    private String email;
    private Integer cep;
    private String logradouroEndereco;
    private Integer numeroEndereco;
    private String complementoEndereco;
    private String bairro;
    private String municipio;
    private String uf;
    private Long numeroCnh;
    private Date dataVencimentoCnh;
    private String senhaCriptografada;
    private String saltSenhaCriptografada;
    private Boolean excluido;
    private Long versao;
    private String tokenCriptografado;
    private String saltTokenCriptografado;
    private Date dataExpiracaoToken;
    private Date dataCriacao;
    private Date dataAtualizacao;
    private UsuarioMotoristaAnonimizacaoVo usuarioMotorista;
    private Boolean utilizaAppMotorista;
    private String senhaAbastecimentoPdv;
    private Integer numeroTentativasSenha;
    private Boolean frentistaCTA;
    private List<Long> idsDispositivoMotorista;
    private List<Long> idsDispositivoMotoristaPedido;
    private List<AutorizacaoPagamentoAnonimizacaoVo> autorizacoesPagamento;
    private List<AutorizacaoPagamentoEdicaoAnonimizacaoVo> autorizacoesPagamentoEdicao;
    private List<FluxoAbastecimentoMotoristaConfigAnonimizacaoVo> fluxoAbastecimentoMotoristaConfigs;
    private Date dataInativacao;

    /**
     * Construtor básico para serialização JSON
     */
    public MotoristaAnonimizacaoVo() {

    }

    /**
     * Constrói o MotoristaAnonimizacaoVo com os dados fornecidos
     * @param motorista o motorista
     * @param autorizacaoPagamentos as autorizações do motorista
     * @param autorizacoesPagamentoEdicao as autorizaçãoEdições do motorista
     * @param dispositivosMotorista os dispositivos do motorista
     * @param dispositivoMotoristaPedidos os dispositivos pedido do motorista
     * @param fluxoAbastecimentoMotoristaConfigs os fluxos de abastecimento de exceção configurados para o motorista
     */
    public MotoristaAnonimizacaoVo(Motorista motorista, List<AutorizacaoPagamento> autorizacaoPagamentos, List<AutorizacaoPagamentoEdicao> autorizacoesPagamentoEdicao, List<DispositivoMotorista> dispositivosMotorista, List<DispositivoMotoristaPedido> dispositivoMotoristaPedidos, List<FluxoAbastecimentoMotoristaConfig> fluxoAbastecimentoMotoristaConfigs){
        if(motorista == null){
            return;
        }

        this.usuarioMotorista = new UsuarioMotoristaAnonimizacaoVo(motorista.getUsuarioMotorista());
        this.autorizacoesPagamento = autorizacaoPagamentos != null ? autorizacaoPagamentos.stream().map(a -> new AutorizacaoPagamentoAnonimizacaoVo(a)).collect(Collectors.toList()): null;
        this.autorizacoesPagamentoEdicao = autorizacaoPagamentos != null ? autorizacoesPagamentoEdicao.stream().map(a -> new AutorizacaoPagamentoEdicaoAnonimizacaoVo(a)).collect(Collectors.toList()): null;
        this.fluxoAbastecimentoMotoristaConfigs = fluxoAbastecimentoMotoristaConfigs != null ? fluxoAbastecimentoMotoristaConfigs.stream().map(FluxoAbastecimentoMotoristaConfigAnonimizacaoVo::new).collect(Collectors.toList()) : null;
        this.idsDispositivoMotorista = dispositivosMotorista != null ? dispositivosMotorista.stream().map(d -> d.getId()).collect(Collectors.toList()) : null;
        this.idsDispositivoMotoristaPedido = dispositivoMotoristaPedidos != null ? dispositivoMotoristaPedidos.stream().map(d -> d.getId()).collect(Collectors.toList()) : null;
        this.id = motorista.getId();
        this.apelido = motorista.getApelido();
        this.numeroCnh = motorista.getNumeroCnh();
        this.cpf = motorista.getCpf();
        this.matricula = motorista.getMatricula();
        this.nome = motorista.getNome();
        this.dataVencimentoCnh = motorista.getDataVencimentoCnh();
        this.status = motorista.getStatus();
        this.agregado = motorista.getAgregado();
        this.dataNascimento = motorista.getDataNascimento();
        this.email = motorista.getEmail();
        this.cep = motorista.getCep();
        this.logradouroEndereco = motorista.getLogradouroEndereco();
        this.numeroEndereco = motorista.getNumeroEndereco();
        this.complementoEndereco = motorista.getComplementoEndereco();
        this.bairro = motorista.getBairro();
        this.uf = motorista.getUf();
        this.municipio = motorista.getMunicipio();
        this.frotaId = motorista.getFrota() != null ? motorista.getFrota().getId() : null;
        this.versao = motorista.getVersao();
        this.utilizaAppMotorista = motorista.getUtilizaAppMotorista();
        this.senhaAbastecimentoPdv = motorista.getSenhaAbastecimentoPdv();
        this.numeroTentativasSenha = motorista.getNumeroTentativasSenha();
        this.dddTelefoneCelular = motorista.getDddTelefoneCelular();
        this.telefoneCelular = motorista.getTelefoneCelular();
        this.unidadeId = motorista.getUnidade() != null ? motorista.getUnidade().getId() : null;
        this.grupoOperacionalId = motorista.getGrupo() != null ? motorista.getGrupo().getId() : null ;
        this.grupoOperacionalId = motorista.getGrupo() != null ? motorista.getGrupo().getId() : null ;
        this.empresaAgregadaId = motorista.getEmpresaAgregada() != null ? motorista.getEmpresaAgregada().getId() : null ;
        this.frentistaCTA = motorista.getFrentistaCTA() ;
        this.statusConvertido = motorista.getStatusConvertido();
        this.agregadoConvertido = motorista.getAgregadoConvertido();
        this.senhaCriptografada = motorista.getSenhaCriptografada();
        this.saltSenhaCriptografada = motorista.getSaltSenhaCriptografada();
        this.excluido = motorista.getExcluido();
        this.tokenCriptografado = motorista.getTokenCriptografado();
        this.saltTokenCriptografado = motorista.getSaltTokenCriptografado();
        this.dataExpiracaoToken = motorista.getDataExpiracaoToken();
        this.dataCriacao = motorista.getDataCriacao();
        this.dataAtualizacao = motorista.getDataAtualizacao();
        this.dataInativacao = motorista.getDataInativacao();
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

    public Long getEmpresaAgregadaId() {
        return empresaAgregadaId;
    }

    public void setEmpresaAgregadaId(Long empresaAgregadaId) {
        this.empresaAgregadaId = empresaAgregadaId;
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

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }

    public String getAgregadoConvertido() {
        return agregadoConvertido;
    }

    public void setAgregadoConvertido(String agregadoConvertido) {
        this.agregadoConvertido = agregadoConvertido;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public void setAgregado(Integer agregado) {
        this.agregado = agregado;
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

    public String getSaltSenhaCriptografada() {
        return saltSenhaCriptografada;
    }

    public void setSaltSenhaCriptografada(String saltSenhaCriptografada) {
        this.saltSenhaCriptografada = saltSenhaCriptografada;
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

    public String getTokenCriptografado() {
        return tokenCriptografado;
    }

    public void setTokenCriptografado(String tokenCriptografado) {
        this.tokenCriptografado = tokenCriptografado;
    }

    public String getSaltTokenCriptografado() {
        return saltTokenCriptografado;
    }

    public void setSaltTokenCriptografado(String saltTokenCriptografado) {
        this.saltTokenCriptografado = saltTokenCriptografado;
    }

    public Date getDataExpiracaoToken() {
        return dataExpiracaoToken;
    }

    public void setDataExpiracaoToken(Date dataExpiracaoToken) {
        this.dataExpiracaoToken = dataExpiracaoToken;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public UsuarioMotoristaAnonimizacaoVo getUsuarioMotorista() {
        return usuarioMotorista;
    }

    public void setUsuarioMotorista(UsuarioMotoristaAnonimizacaoVo usuarioMotorista) {
        this.usuarioMotorista = usuarioMotorista;
    }

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

    public List<Long> getIdsDispositivoMotorista() {
        return idsDispositivoMotorista;
    }

    public void setIdsDispositivoMotorista(List<Long> idsDispositivoMotorista) {
        this.idsDispositivoMotorista = idsDispositivoMotorista;
    }

    public List<Long> getIdsDispositivoMotoristaPedido() {
        return idsDispositivoMotoristaPedido;
    }

    public void setIdsDispositivoMotoristaPedido(List<Long> idsDispositivoMotoristaPedido) {
        this.idsDispositivoMotoristaPedido = idsDispositivoMotoristaPedido;
    }

    public List<AutorizacaoPagamentoAnonimizacaoVo> getAutorizacoesPagamento() {
        return autorizacoesPagamento;
    }

    public void setAutorizacoesPagamento(List<AutorizacaoPagamentoAnonimizacaoVo> autorizacoesPagamento) {
        this.autorizacoesPagamento = autorizacoesPagamento;
    }

    public List<AutorizacaoPagamentoEdicaoAnonimizacaoVo> getAutorizacoesPagamentoEdicao() {
        return autorizacoesPagamentoEdicao;
    }

    public void setAutorizacoesPagamentoEdicao(List<AutorizacaoPagamentoEdicaoAnonimizacaoVo> autorizacoesPagamentoEdicao) {
        this.autorizacoesPagamentoEdicao = autorizacoesPagamentoEdicao;
    }

    public List<FluxoAbastecimentoMotoristaConfigAnonimizacaoVo> getFluxoAbastecimentoMotoristaConfigs() {
        return fluxoAbastecimentoMotoristaConfigs;
    }

    public void setFluxoAbastecimentoMotoristaConfigs(List<FluxoAbastecimentoMotoristaConfigAnonimizacaoVo> fluxoAbastecimentoMotoristaConfigs) {
        this.fluxoAbastecimentoMotoristaConfigs = fluxoAbastecimentoMotoristaConfigs;
    }

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }
}
