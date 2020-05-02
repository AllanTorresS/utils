package ipp.aci.boleia.util.negocio;


import ipp.aci.boleia.dominio.Notificacao;
import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoNotificacaoPush;
import ipp.aci.boleia.dominio.vo.NotificacaoPushGestorFrotaVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroZerosEsquerda;

/**
 * Utilitário responsável pelo envio das notificações push para o app do Gestor da Frota.
 *
 * @author pedro.silva
 */
@Component
public class UtilitarioNotificacaoPushGestorFrota extends UtilitarioNotificacaoPushBase {

    /**
     * Envia uma notificação push para o gestor da frota informando que uma
     * notificação foi gerada no portal.
     *
     * @param notificacaoUsuario Notificação emitida no portal para um gestor frotista.
     */
    @Async
    public void enviarNotificacaoPortal(NotificacaoUsuario notificacaoUsuario) {
        Usuario usuario = notificacaoUsuario.getUsuario();
        Notificacao notificacao = notificacaoUsuario.getNotificacao();

        NotificacaoPushGestorFrotaVo vo = new NotificacaoPushGestorFrotaVo();
        vo.setCategoriaNotificacao(notificacao.getSubcategoria().getCategoria().getChave());
        vo.setSubcategoriaNotificacao(notificacao.getSubcategoria().getChave());
        vo.setTipoNotificacao(TipoNotificacaoPush.NOTIFICACAO_PORTAL);
        vo.setIdAutorizacaoPagamento(notificacaoUsuario.getNotificacao().getIdAutorizacaoPagamento());

        String topico = "gestorFrota-" + formatarNumeroZerosEsquerda(usuario.getCpf(), 11);
        enviarNotificacaoViaTopico(topico, "", notificacao.getTitulo(), vo);
    }
}