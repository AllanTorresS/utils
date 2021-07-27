package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.UsuarioMotorista;

import java.util.Date;

/**
 * Classe de exibição que representa um usuário motorista anonimização
 */
public class UsuarioMotoristaAnonimizacaoVo {

    private Long id;
    private UsuarioAnonimizacaoVo usuario;
    private String tokenAllowMe;
    private String idAllowMe;
    private String seedAllowMe;
    private String udid;
    private Date dataExpiracaoSenhaTemporaria;
    private Boolean excluido;

    /**
     * Construtor básico para serialização JSON
     */
    public UsuarioMotoristaAnonimizacaoVo() {

    }

    /**
     * Constrói o UsuarioMotoristaAnonimizacaoVo com os dados fornecidos
     *
     * @param usuarioMotorista a entidade contendo informações
     */
    public UsuarioMotoristaAnonimizacaoVo(UsuarioMotorista usuarioMotorista) {
        if(usuarioMotorista == null){
            return;
        }
        this.usuario = new UsuarioAnonimizacaoVo(usuarioMotorista.getUsuario());
        this.id = usuarioMotorista.getId();
        this.dataExpiracaoSenhaTemporaria = usuarioMotorista.getDataExpiracaoSenhaTemporaria();
        this.tokenAllowMe = usuarioMotorista.getTokenAllowMe();
        this.idAllowMe = usuarioMotorista.getIdAllowMe();
        this.seedAllowMe = usuarioMotorista.getSeedAllowMe();
        this.excluido = usuarioMotorista.getExcluido();
        this.udid = usuarioMotorista.getUdid();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioAnonimizacaoVo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioAnonimizacaoVo usuario) {
        this.usuario = usuario;
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

    public String getSeedAllowMe() {
        return seedAllowMe;
    }

    public void setSeedAllowMe(String seedAllowMe) {
        this.seedAllowMe = seedAllowMe;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public Date getDataExpiracaoSenhaTemporaria() {
        return dataExpiracaoSenhaTemporaria;
    }

    public void setDataExpiracaoSenhaTemporaria(Date dataExpiracaoSenhaTemporaria) {
        this.dataExpiracaoSenhaTemporaria = dataExpiracaoSenhaTemporaria;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
