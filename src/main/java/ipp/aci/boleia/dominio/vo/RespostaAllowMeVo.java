package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe com informacoes relacionadas a resposta recebida do sistema Allow Me
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaAllowMeVo {

    private String token;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    private String data;

    @JsonProperty("passcode_attempts_left")
    private Integer passcodeAttemptsLeft;

    @JsonProperty("auth_attempts_left")
    private Integer authAttemptsLeft;

    private Boolean authorized;

    private UsuarioAllowMeVo user;

    private List<ErrorAllowMeVo> errors;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPasscodeAttemptsLeft() {
        return passcodeAttemptsLeft;
    }

    public void setPasscodeAttemptsLeft(Integer passcodeAttemptsLeft) {
        this.passcodeAttemptsLeft = passcodeAttemptsLeft;
    }

    public Integer getAuthAttemptsLeft() {
        return authAttemptsLeft;
    }

    public void setAuthAttemptsLeft(Integer authAttemptsLeft) {
        this.authAttemptsLeft = authAttemptsLeft;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public UsuarioAllowMeVo getUser() {
        return user;
    }

    public void setUser(UsuarioAllowMeVo user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ErrorAllowMeVo> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorAllowMeVo> errors) {
        this.errors = errors;
    }
}
