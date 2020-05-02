package ipp.aci.boleia.dominio.vo;

/**
 * Representa as credencias da integração com o integrador
 */
public class AutenticacaoIntegradorVo {

    private String chave;
    private String token;

    public AutenticacaoIntegradorVo() {
        //Construtor default
    }

    /**
     * Construtor da autenticacao
     * @param chave A chave para autenticação do integrador
     * @param token O token para autenticação do integrador
     */
    public AutenticacaoIntegradorVo(String chave, String token) {
        this.chave = chave;
        this.token = token;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
