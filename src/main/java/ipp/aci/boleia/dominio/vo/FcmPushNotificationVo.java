package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade de Notificação Push do Google Firebase Cloud Messaging
 */
public class FcmPushNotificationVo {

    private String to;
    private FcmBaseNotificationVo notification;
    private Object data;

    /**
     * Construtor sem parametros
     */
    public FcmPushNotificationVo(){
        this.notification = new FcmBaseNotificationVo();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FcmBaseNotificationVo getNotification() {
        return notification;
    }

    public void setNotification(FcmBaseNotificationVo notification) {
        this.notification = notification;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
