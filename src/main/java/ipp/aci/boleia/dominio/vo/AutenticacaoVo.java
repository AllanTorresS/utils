package ipp.aci.boleia.dominio.vo;

/**
 * Representa as credencias do parceiro de integração
 */
public class AutenticacaoVo {

    private String login;
    private String senha;

    public AutenticacaoVo() {
        //Construtor default
    }

    /**
     * Construtor da autenticacao
     * @param login O login do parceiro de integração
     * @param senha A senha do parceiro de integração
     */
    public AutenticacaoVo(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
