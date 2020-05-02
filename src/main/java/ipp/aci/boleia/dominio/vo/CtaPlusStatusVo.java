package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção de status da requisição ao CTA Plus
 */
public class CtaPlusStatusVo {

    private String codigo;
    private String mensagem;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
