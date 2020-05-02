package ipp.aci.boleia.dominio.vo;

import java.util.Date;
import java.util.List;

/**
 * Objeto que representa um e-mail
 */
public class EmailVo {

    private String remetente;
    private Date dataEnvio;
    private Date dataRecebimento;
    private List<String> anexos;

    /**
     * Construtor padrão
     */
    public EmailVo() {
    }

    /**
     * Instancia a classe
     * @param remetente O remetente do e-mail
     * @param dataEnvio A data de envio do e-mail
     * @param dataRecebimento Data de recebimento do e-mail
     * @param anexos Lista de anexos
     */
    public EmailVo(String remetente, Date dataEnvio, Date dataRecebimento, List<String> anexos) {
        this.remetente = remetente;
        this.dataEnvio = dataEnvio;
        this.dataRecebimento = dataRecebimento;
        this.anexos = anexos;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public List<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<String> anexos) {
        this.anexos = anexos;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
}
