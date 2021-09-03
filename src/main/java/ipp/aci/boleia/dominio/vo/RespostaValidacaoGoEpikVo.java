package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe com informacoes relacionadas a resposta da api da GoEpik
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaValidacaoGoEpikVo {

    @JsonProperty("OCR_Result")
    private String result;

    @JsonProperty("message")
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
