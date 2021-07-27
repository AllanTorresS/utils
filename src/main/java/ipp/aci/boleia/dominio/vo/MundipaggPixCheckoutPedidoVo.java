package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe com informacoes do boleto Mundipagg
 */
public class MundipaggPixCheckoutPedidoVo {

    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * Construtor do boleto Mundipagg
     */
    public MundipaggPixCheckoutPedidoVo() {
        expiresIn = 60;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }
}
 