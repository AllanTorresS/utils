package ipp.aci.boleia.dominio.vo;

/**
 * VO com os dados da notificação push enviada para o Gestor da Frota.
 *
 * @author pedro.silva
 */
public class NotificacaoPushGestorFrotaVo extends BaseNotificacaoPushVo {
    private String categoriaNotificacao;
    private String subcategoriaNotificacao;
    private Long idAutorizacaoPagamento;

    public String getCategoriaNotificacao() {
        return categoriaNotificacao;
    }

    public void setCategoriaNotificacao(String categoriaNotificacao) {
        this.categoriaNotificacao = categoriaNotificacao;
    }

    public String getSubcategoriaNotificacao() {
        return subcategoriaNotificacao;
    }

    public void setSubcategoriaNotificacao(String subcategoriaNotificacao) {
        this.subcategoriaNotificacao = subcategoriaNotificacao;
    }

    public Long getIdAutorizacaoPagamento() {
        return idAutorizacaoPagamento;
    }

    public void setIdAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        this.idAutorizacaoPagamento = idAutorizacaoPagamento;
    }
}
