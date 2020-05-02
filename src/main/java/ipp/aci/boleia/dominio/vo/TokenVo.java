package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Representa um token retornado pela integração de autorização
 */
public class TokenVo {

    private String token;
    private Date dataExpiracaoToken;

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
}
