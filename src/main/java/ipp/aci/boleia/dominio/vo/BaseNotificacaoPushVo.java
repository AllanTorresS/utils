package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.TipoNotificacaoPush;

/**
 * Classe base para a criação de VOs de notificação push.
 *
 * @author pedro.silva
 */
public abstract class BaseNotificacaoPushVo {

    private TipoNotificacaoPush tipoNotificacao;

    public TipoNotificacaoPush getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(TipoNotificacaoPush tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }
}
