package ipp.aci.boleia.util.boot;

import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.MensagemErro;
import ipp.aci.boleia.util.seguranca.ChavePermissao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Intercepta todas as requisicoes HTTP do sistema, verificando se a sessao vinculada ainda esta valida.
 * Sao consideradas validas apenas as sessoes que tenham recebido requests desprovidos do parametro hsr=false
 * nos ultimos "sessao.getMaxInactiveInterval()" segundos.
 */
@Configuration
public class ConfiguracoesExpiracaoSessao extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracoesExpiracaoSessao.class);
    public static final String LAST_REQUEST_TIMESTAMP = "LAST_REQUEST_TIMESTAMP";

    @Value("${server.session.timeout}")
    private int maxInactiveInterval;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ExpiradorSessoesHandler(maxInactiveInterval));
    }

    /**
     * Intercepta todas as requisicoes HTTP do sistema, verificando se a sessao vinculada ainda esta valida.
     * Sao consideradas validas apenas as sessoes que tenham recebido requests desprovidos do parametro hsr=false
     * nos ultimos "sessao.getMaxInactiveInterval()" segundos.
     * Se a sessao estiver invalida, invoca o metodo session.invalidade, terminando-a e forcando o usuario a
     * se autenticar novamente.
     */
    public static class ExpiradorSessoesHandler extends HandlerInterceptorAdapter {

        private int maxInactiveInterval;

        /**
         * Construtor de um expirador de secoes a partir do intervalo maximo da sessao
         * @param maxInactiveInterval intervalo maximo
         */
        public ExpiradorSessoesHandler(int maxInactiveInterval) {
            this.maxInactiveInterval = maxInactiveInterval;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            try {
                HttpSession sessao = request.getSession(false);
                if (sessao != null) {
                    if (tempoSessaoExpirou(sessao) && !ignorarHandler(handler)) {
                        sessao.invalidate();
                        enviarErroAutenticacao(response);
                        return false;
                    } else if (request.getQueryString() == null || !request.getQueryString().contains("hsr=false")) {
                        sessao.setAttribute(LAST_REQUEST_TIMESTAMP, new Date().getTime());
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Problema na interceptacao para expiracao de sessoes", e);
            }
            return true;
        }

        /**
         * Verifica se um dado handler deve ser ignorado
         * @param handler handler a verificar
         * @return true para ignorar
         */
        private boolean ignorarHandler(Object handler) {
            if (handler instanceof HandlerMethod) {
                PreAuthorize preAuth = ((HandlerMethod) handler).getMethod().getAnnotation(PreAuthorize.class);
                if (preAuth != null) {
                    String valor = preAuth.value();
                    return ChavePermissao.PUBLICO.equals(valor);
                }
            }
            return false;
        }

        /**
         * Verifica se o tempo decorrido eh maior que o tempo maximo de ociosidade da sessao
         *
         * @param sessao A sessao
         * @return True caso a sessao esteja inativa por um periodo maior que o timeout configurado
         */
        private boolean tempoSessaoExpirou(HttpSession sessao) {
            Object lastRequestTime = sessao.getAttribute(LAST_REQUEST_TIMESTAMP);
            if (lastRequestTime != null) {
                long now = new Date().getTime();
                int timeout = maxInactiveInterval * 1000;
                return (now - (Long) lastRequestTime) > timeout;
            }
            return false;
        }


        /**
         * Envia o erro de autenticacao
         *
         * @param response resposta da requisicao
         * @throws IOException excecao na leitura ou escrita
         */
        private void enviarErroAutenticacao(HttpServletResponse response) throws IOException {
            MensagemErro msg = new MensagemErro(Erro.SESSAO_EXPIRADA_INATIVIDADE.getCodigo(), null);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            byte[] bytes = UtilitarioJson.toJSON(msg).getBytes(StandardCharsets.UTF_8);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        }
    }
}