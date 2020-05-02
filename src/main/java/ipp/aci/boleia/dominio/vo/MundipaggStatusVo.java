package ipp.aci.boleia.dominio.vo;

/**
 * Classe com informacoes relacionadas ao status do pedido na Mundipagg
 */
public class MundipaggStatusVo {

    private String status;

    public MundipaggStatusVo() {

    }

    /**
     * Construtor do status do pedido
     * @param status O status
     */
    public MundipaggStatusVo(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 