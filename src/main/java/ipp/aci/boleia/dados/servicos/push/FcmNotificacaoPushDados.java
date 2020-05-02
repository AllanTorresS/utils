package ipp.aci.boleia.dados.servicos.push;


import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.INotificacaoPushDados;
import ipp.aci.boleia.dominio.vo.FcmPushNotificationVo;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * Oferece funcionalidades para o envio de notificações push
 */
@Repository
public class FcmNotificacaoPushDados implements INotificacaoPushDados {

    @Autowired
    private IClienteHttpDados clienteRest;

    @Value("${google.fcm.api.url}")
    private String googleFcmUrl;

    @Value("${google.fcm.api.key}")
    private String googleFcmKey;

    @Override
    public void enviarNotificacaoPushPorToken(String tokenDispositivo, String titulo, String mensagem, Object dados) {
        Header header = new BasicHeader("Authorization","key=" + googleFcmKey);
        FcmPushNotificationVo push = new FcmPushNotificationVo();
        push.setTo(tokenDispositivo);
        push.getNotification().setTitle(titulo);
        push.getNotification().setBody(mensagem);
        if(dados != null){
            push.setData(dados);
        }
        clienteRest.doPostJson(googleFcmUrl, push, new Header[]{header}, null);
    }

    @Override
    public void enviarNotificacaoPushPorTopico(String topico, String titulo, String mensagem, Object dados) {
        enviarNotificacaoPushPorToken("/topics/" + topico, titulo, mensagem, dados);
    }
}
