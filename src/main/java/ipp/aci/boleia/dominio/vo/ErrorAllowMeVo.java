package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe com informacoes relacionadas as mensagens de erro do sistema Allow Me
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorAllowMeVo {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("user_message")
    private String userMessage;

    @JsonProperty("developer_message")
    private String developerMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }




}
