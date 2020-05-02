package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade Base de Notificação do Google Firebase Cloud Messaging
 */
public class FcmBaseNotificationVo {

    private String title;
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
