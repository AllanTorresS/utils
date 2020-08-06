package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusAcumuloKmv;

/**
 * Classe com informacoes relacionadas ao kmv
 */
public class KmvVo {

    private Integer km;
    private StatusAcumuloKmv statusAcumuloKmv;
    private String msgErro;

    public KmvVo() {
        //Construtor default
    }

    /**
     * Construtor do KMV padrão em casos de sucesso
     * @param statusAcumuloKmv O status do acumulo de pontos do kmv
     * @param km Os quilometros de vantagem
     */
    public KmvVo(StatusAcumuloKmv statusAcumuloKmv, Integer km) {
        this.km = km;
        this.statusAcumuloKmv = statusAcumuloKmv;
    }

    /**
     * Construtor do KMV
     * @param statusAcumuloKmv O status do acumulo de pontos do kmv
     * @param km Os quilometros de vantagem
     * @param msgErro mensagem de controle especificando detalhes do caso de falha
     */
    public KmvVo(StatusAcumuloKmv statusAcumuloKmv, Integer km, String msgErro) {
        this.km = km;
        this.statusAcumuloKmv = statusAcumuloKmv;
        this.msgErro = msgErro;
    }

    public Integer getKm() {
            return km;
        }

    public void setKm(Integer km) {
        this.km = km;
    }

    public StatusAcumuloKmv getStatusAcumuloKmv() {
        return statusAcumuloKmv;
    }

    public void setStatusAcumuloKmv(StatusAcumuloKmv statusAcumuloKmv) {
        this.statusAcumuloKmv = statusAcumuloKmv;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }
}
