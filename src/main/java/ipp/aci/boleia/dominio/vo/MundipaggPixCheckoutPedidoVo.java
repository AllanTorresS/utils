package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe com informacoes do boleto Mundipagg
 */
public class MundipaggPixCheckoutPedidoVo {

    @JsonProperty("expiresIn")
    private String expiresIn;

    /**
     * Construtor do boleto Mundipagg
     */
    public MundipaggPixCheckoutPedidoVo() {
        expiresIn = "60";
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getExpiresIn() {
        return expiresIn;
    }
}
 