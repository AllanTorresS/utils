package ipp.aci.boleia.util.boot;

import ipp.aci.boleia.util.rotas.FrotistaRotas;
import ipp.aci.boleia.util.seguranca.FiltroRequisicoes;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe de configuracoes da taxa limite de requisicoes a api de frota
 */
@Configuration
public class ConfiguracoesRateLimitApiFrotista extends WebMvcConfigurerAdapter {

    @Autowired
    private FiltroRequisicoes filtroRequisicoes;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
            .addInterceptor(new RateLimitApiFrotistaHandler(filtroRequisicoes, utilitarioJwt))
            .addPathPatterns(FrotistaRotas.BASE_API + "/**");
    }

    /**
     * Manipulador da taxa de requisicoes da api do frotista
     */
    public static class RateLimitApiFrotistaHandler extends HandlerInterceptorAdapter {

        private static final long INTERVALO_MINIMO_REQUISICOES = 5000L;

        private FiltroRequisicoes filtroRequisicoes;
        private UtilitarioJwt utilitarioJwt;

        /**
         * Contrutor da taxa limite das requições a api da frota
         * @param filtroRequisicoes filtor de requisicoes
         * @param utilitarioJwt utilitario jwt
         */
        public RateLimitApiFrotistaHandler(FiltroRequisicoes filtroRequisicoes, UtilitarioJwt utilitarioJwt) {
            this.filtroRequisicoes = filtroRequisicoes;
            this.utilitarioJwt = utilitarioJwt;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String tokenRequisicao = utilitarioJwt.extrairToken(request);
            filtroRequisicoes.exigirIntervaloMinimoTempoEntreRequisicoes(request, INTERVALO_MINIMO_REQUISICOES, tokenRequisicao);
            return true;
        }
    }
}