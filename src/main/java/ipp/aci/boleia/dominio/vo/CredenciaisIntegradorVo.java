package ipp.aci.boleia.dominio.vo;

/**
 * Representa as credencias da integração com o integrador
 */
public class CredenciaisIntegradorVo {

    private String jwt;
    private String refreshToken;

    public CredenciaisIntegradorVo() {
        //Construtor default
    }

    /**
     * Construtor
     * @param jwt o token jwt
     * @param refreshToken o refresh token
     */
    public CredenciaisIntegradorVo(String jwt, String refreshToken){
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
