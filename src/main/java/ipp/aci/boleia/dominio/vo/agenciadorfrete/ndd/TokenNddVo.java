package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;

import java.util.Date;


/**
 * Classe com informacoes relacionadas ao token de autenticação da NDD
 */
public class TokenNddVo {
    private String token;
    private String refreshToken;
    private Date dataExpiracao;


    public TokenNddVo() {
        //Construtor default
    }

    public TokenNddVo(String token, String refreshToken, Date dataExpiracao) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.dataExpiracao = dataExpiracao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
