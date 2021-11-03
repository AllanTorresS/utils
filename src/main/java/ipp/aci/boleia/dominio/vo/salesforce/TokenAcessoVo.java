package ipp.aci.boleia.dominio.vo.salesforce;

/**
 * Vo com os dados de acesso para uma integração para o salesforce.
 * 
 * @author pedro.silva
 */
public class TokenAcessoVo {
    private String token;
    private String urlInstancia;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlInstancia() {
        return this.urlInstancia;
    }

    public void setUrlInstancia(String urlInstancia) {
        this.urlInstancia = urlInstancia;
    }

}
