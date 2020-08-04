package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de Pedido
 */
public enum TipoPedido {

    AGENCIADOR_FRETE          (30, 9);

    private final int duracao;
    private final int tamanho;

    /**
     * Constroi as informacoes basicas do tipo de pedido
     *
     * @param duracao A duracao do token em minutos
     * @param tamanho o tamanho do pedido
     */
    TipoPedido(int duracao, int tamanho) {
        this.duracao = duracao;
        this.tamanho = tamanho;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getTamanho() {
        return tamanho;
    }
}