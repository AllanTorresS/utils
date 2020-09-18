package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.TipoRelatorioMotorGerador;

/**
 * Resposta da tentativa de autenticação entre serviços do Pró-Frotas
 */
public class RespostaIntegracaoVo implements IRespostaModuloInternoVo {

    private Integer statusResposta;
    private String tokenJwt;

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
}
