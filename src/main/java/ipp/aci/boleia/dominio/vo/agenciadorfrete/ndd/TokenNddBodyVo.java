package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;


/**
 * Classe que representa o body do serviço de autenticação da Ndd
 */
public class TokenNddBodyVo {

    private String grantType;
    private String clientId;
    private String clientSecret;
    private String refreshToken;

    public TokenNddBodyVo(){
        //Construtor default
    }

    public TokenNddBodyVo(String clientId, String grantType){
        this.clientId = clientId;
        this.grantType = grantType;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grant_type) {
        this.grantType = grant_type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String client_id) {
        this.clientId = client_id;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String client_secret) {
        this.clientSecret = client_secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refresh_token) {
        this.refreshToken = refresh_token;
    }
}
