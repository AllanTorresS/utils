package ipp.aci.boleia.dominio.vo;

/**
 * Resposta da tentativa de autenticação entre serviços do Pró-Frotas
 */
public class RespostaIntegracaoVo {

    private Integer statusResposta;
    private String tokenJwt;
    private Object conteudoResposta;

    /**
     * Construtor padrão
     */
    public RespostaIntegracaoVo() {
    }

    public Integer getStatusResposta() {
        return statusResposta;
    }

    public void setStatusResposta(Integer statusResposta) {
        this.statusResposta = statusResposta;
    }

    public String getTokenJwt() {
        return tokenJwt;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    public Object getConteudoResposta() {
        return conteudoResposta;
    }

    public void setConteudoResposta(Object conteudoResposta) {
        this.conteudoResposta = conteudoResposta;
    }
}
