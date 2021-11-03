package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe com informacoes relacionadas ao usuario do sistema Allow Me
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioAllowMeVo {

    private String id;

    private String name;

    private String username;

    private List<DispositivoAllowMeVo> devices;

    private String code;

    @JsonProperty("keep_otp_devices")
    private Boolean keepOtpDevices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DispositivoAllowMeVo> getDevices() {
        return devices;
    }

    public void setDevices(List<DispositivoAllowMeVo> devices) {
        this.devices = devices;
    }

    public void setKeepOtpDevices(Boolean keepOtpDevices) {
        this.keepOtpDevices = keepOtpDevices;
    }

    public Boolean getKeepOtpDevices() {
        return keepOtpDevices;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
