package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusMotorGeradorRelatorio;

/**
 * Representa dados do resultado do status do motor gerador de relatorio
 */
public class ResultadoMotorGeradorRelatorioVo {
    private StatusMotorGeradorRelatorio status;
    private String msgErro;

    /**
     * Construtor com o resultado do status do MotorGeradorRelatorio default
     */
    public ResultadoMotorGeradorRelatorioVo(){
    }

    /**
     * Construtor com o resultado do status do MotorGeradorRelatorio
     *
     * @param status O status do relatorio
     */
    public ResultadoMotorGeradorRelatorioVo(StatusMotorGeradorRelatorio status){
        this.status = status;
    }

    /**
     * Construtor com o resultado do status do MotorGeradorRelatorio
     *
     *  @param status O status do relatorio
     *  @param msgErro A mensagem explicando um erro do relatorio
     */
    public ResultadoMotorGeradorRelatorioVo(StatusMotorGeradorRelatorio status, String msgErro) {
        this.status = status;
        this.msgErro = msgErro;
    }

    public StatusMotorGeradorRelatorio getStatus() {
        return status;
    }

    public void setStatus(StatusMotorGeradorRelatorio status) {
        this.status = status;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }
}
