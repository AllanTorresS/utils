package ipp.aci.boleia.dados;

/**
 * Contrato para implementacao de reposiórios de mensagens.
 */
@FunctionalInterface
public interface IMensagemDados {

    /**
     * Envia uma mensagem para o numero de telefone informado
     *
     * @param mensagem A mensagem
     * @param numero   O telefone alvo
     */
    void enviarSMS(String mensagem, String numero);
}