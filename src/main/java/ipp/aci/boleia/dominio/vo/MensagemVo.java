package ipp.aci.boleia.dominio.vo;

/**
 * Classe utilizada para retorno de mensagens de serviços
 */
public class MensagemVo {
    private String mensagem;

    public MensagemVo() {
        // serializacao json
    }

    /**
     * Construtor
     * @param mensagem A mensagem a ser enviada ao cliente
     */
    public MensagemVo(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
