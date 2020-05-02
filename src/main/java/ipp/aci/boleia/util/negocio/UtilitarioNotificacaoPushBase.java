package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.dados.INotificacaoPushDados;
import ipp.aci.boleia.dominio.vo.BaseNotificacaoPushVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe base para a construção de Utilitarios de envio de notificação push.
 *
 * @author pedro.silva
 */
public abstract class UtilitarioNotificacaoPushBase {

    @Autowired
    private INotificacaoPushDados notificacaoPushDados;

    /**
     * Realiza o envio de uma notificação push via token.
     *
     * @param token Token push.
     * @param titulo Título da notificação.
     * @param mensagem Mensagem da notificação.
     * @param dados Dados extras enviados na notificação.
     * @param <T> VO de notificação push
     */
    protected <T extends BaseNotificacaoPushVo> void enviarNotificacaoViaToken(String token, String titulo, String mensagem, T dados) {
        notificacaoPushDados.enviarNotificacaoPushPorToken(token, titulo, mensagem, dados);
    }

    /**
     * Realiza o envio de uma notificação push via tópico.
     *
     * @param topico Tópico.
     * @param titulo Título da notificação.
     * @param mensagem Mensagem da notificação.
     * @param dados Dados extras enviados na notificação.
     * @param <T> VO de notificação push
     */
    protected <T extends BaseNotificacaoPushVo> void enviarNotificacaoViaTopico(String topico, String titulo, String mensagem, T dados) {
        notificacaoPushDados.enviarNotificacaoPushPorTopico(topico, titulo, mensagem, dados);
    }
}
