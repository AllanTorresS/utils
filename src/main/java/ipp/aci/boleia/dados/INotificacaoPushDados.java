package ipp.aci.boleia.dados;

/**
 * Contrato para implementacao de repositorios capazes de enviar notificações para dispositivos móveis
 */
public interface INotificacaoPushDados {

    /**
     * Envia uma notificação push para um dado dispositivo
     *
     * @param tokenDispositivo O token de identificação do dispositivo
     * @param titulo O título da notificação
     * @param mensagem A Mensagem da notificação
     * @param dados O objeto que representa os dados adicionais da notificação
     */
    void enviarNotificacaoPushPorToken(String tokenDispositivo, String titulo, String mensagem, Object dados);

    /**
     * Envia uma notificação push para um tópico
     *
     * @param topico o topico que receberá a notificação
     * @param titulo o título da notificação
     * @param mensagem a mensagem da notificação
     * @param dados o objeto que representa os dados adicionais da notificação
     */
    void enviarNotificacaoPushPorTopico(String topico, String titulo, String mensagem, Object dados);
}
