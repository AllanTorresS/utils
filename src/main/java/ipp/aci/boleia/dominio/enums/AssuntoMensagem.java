package ipp.aci.boleia.dominio.enums;


/**
 * Indica o assunto da transacao (mensagem)
 */
public enum AssuntoMensagem {

    CODIGO_REGERADO("codigoAcessoRegerado"),
    CODIGO_ABASTECIMENTO("codigoAbastecimento"),
    SENHA_CONTINGENCIA("senhaContingencia"),
    CODIGO_DESBLOQUEIO("codigoAcessoDesbloqueio");

    private final String codigoAssunto;

    /**
     * Construtor
     *
     * @param codigoAssunto O codigoAssunto do assunto
     */
    AssuntoMensagem(String codigoAssunto) {
        this.codigoAssunto = codigoAssunto;
    }

    public String getCodigoAssunto() {
        return this.codigoAssunto;
    }
}
