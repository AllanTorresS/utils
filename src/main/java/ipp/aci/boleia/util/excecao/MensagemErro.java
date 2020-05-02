package ipp.aci.boleia.util.excecao;

import java.util.List;

/**
 * Classe com informacoes relacionadas a mensagens de erro
 */
public class MensagemErro {

    private Integer codigo;
    private List<String> mensagens;

    public MensagemErro() {
        // construtor default
    }

    /**
     * Construtor
     *
     * @param codigo Codigo de erro
     * @param mensagens A mensagem a ser enviada ao cliente
     *
     */
    public MensagemErro(Integer codigo, List<String> mensagens) {
        this.codigo = codigo;
        this.mensagens = mensagens;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }
}
