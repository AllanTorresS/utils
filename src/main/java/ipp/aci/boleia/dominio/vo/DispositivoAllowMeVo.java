package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe com informacoes relacionadas ao dispositivo Allow Me
 */
public class DispositivoAllowMeVo {

    private String id;

    private List<String> capabilities = new ArrayList<>();

    @JsonProperty("confirmed_at")
    private String confirmedAt;

    private String number;

    private String token;

    @JsonProperty("otp_activated")
    private Boolean otpActivated;

    @JsonProperty("otp_activated_at")
    private Date otpActivatedAt;

    @JsonProperty("qrcode_token")
    private String qrcodeToken;

    private String seed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getOtpActivated() {
        return otpActivated;
    }

    public void setOtpActivated(Boolean otpActivated) {
        this.otpActivated = otpActivated;
    }

    public Date getOtpActivatedAt() {
        return otpActivatedAt;
    }

    public void setOtpActivatedAt(Date otpActivatedAt) {
        this.otpActivatedAt = otpActivatedAt;
    }

    public String getQrcodeToken() {
        return qrcodeToken;
    }

    public void setQrcodeToken(String qrcodeToken) {
        this.qrcodeToken = qrcodeToken;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}
