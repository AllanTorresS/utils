package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção da versão e plataforma do aplicativo motorista
 */
public class VersaoAplicativoVo {

    private String versao;
    private String udid;
    private String plataforma;

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}
