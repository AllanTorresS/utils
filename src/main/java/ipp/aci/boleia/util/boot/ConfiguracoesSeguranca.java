package ipp.aci.boleia.util.boot;

import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.boot.ConfiguracoesPersistenciaSessao.RepositorioSessoesRedis;
import ipp.aci.boleia.util.excecao.IExcecaoBoleia;
import ipp.aci.boleia.util.excecao.MensagemErro;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.rotas.Paginas;
import ipp.aci.boleia.util.rotas.Rotas;
import ipp.aci.boleia.util.seguranca.FiltroAutenticacaoJwt;
import ipp.aci.boleia.util.seguranca.FiltroAutorizacaoApiDocs;
import ipp.aci.boleia.util.seguranca.InformacoesAutenticacao;
import ipp.aci.boleia.util.seguranca.ProvedorAutenticacao;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuracoes de seguranca do sistema
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ConfiguracoesSeguranca extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracoesSeguranca.class);
    private static final String URI_HEALTH_CHECK = "/health";

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProvedorAutenticacao provedorAutenticacao;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private FiltroAutenticacaoJwt filtroAutenticacaoJwt;

    @Autowired
    private FiltroAutorizacaoApiDocs filtroAutorizacaoApiDocs;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Value("${max.sessions.per.user}")
    private Integer maximoSessoesPorUsuario;

    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.authenticationProvider(provedorAutenticacao);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(
                    URI_HEALTH_CHECK,
                    "/",
                    "/home",
                    "/home/*",
                    "/api/logout",
                    "/info",
                    "/api/publico/**",
                    "/v2/api-docs/**",
                    "/appMotorista",
                    "/pdv", "/pdv/",
                    "/**/*.html",
                    "/**/*.css", "/**/*.js",
                    "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico",
                    "/**/*.ttf", "/**/*.otf", "/**/*.eot", "/**/*.woff", "/**/*.woff2")
                    .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
                    .addFilterBefore(filtroAutenticacaoJwt, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(filtroAutorizacaoApiDocs, UsernamePasswordAuthenticationFilter.class)
            .formLogin()
                .loginProcessingUrl(Rotas.LOGIN)
                .loginPage(Paginas.LOGIN)
                .permitAll()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
            .httpBasic()
                .and()

            // O filtroAutenticacaoJwt previne ataques de XSRF
            .csrf()
                .disable()

            .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity();

        http
            .cors()
            .and()
            .sessionManagement()
                .maximumSessions(maximoSessoesPorUsuario)
                .expiredSessionStrategy(sessionExpiredStrategy())
                .sessionRegistry(sessionRegistry());


        if (securityProperties.isRequireSsl()) {
            // Com excecao do healthcheck, todas as requisições devem utilizar HTTPS
            http
                .requiresChannel().antMatchers(URI_HEALTH_CHECK).requiresInsecure()
                .and()
                .requiresChannel().anyRequest().requiresSecure();
        }
    }

    /**
     * Trata a sessão expirada retornando erro de acesso não autorizado.
     *
     * @return O tratador do evento de expiração.
     */
    public SessionInformationExpiredStrategy sessionExpiredStrategy(){
        return sessionInformationExpiredEvent ->
            sessionInformationExpiredEvent.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Tratador para o evento de solicitacao de autenticacao (quando o usuario acessa um recurso sem estar autenticado)
     *
     * @return O tratador do evento
     */
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.TEXT_HTML_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().append("<script>window.location.href='"  + Paginas.LOGIN + "';</script>");
        };
    }

    /**
     * Tratador de evento de sucesso na autenticacao (tela de login)
     *
     * @return Um handler para o evento
     */
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                httpServletResponse.setStatus(HttpStatus.OK.value());
                preencherCabecalhosResposta(httpServletResponse);
                InformacoesAutenticacao info = (InformacoesAutenticacao) authentication;
                ServletOutputStream out = httpServletResponse.getOutputStream();
                out.write(utilitarioJwt.montarRespostaTokenJWT(info.getUsuario().getTokenJWT()));
                out.flush();
            }
        };
    }

    /**
     * Tratador de evento de erro na autenticacao (tela de login)
     *
     * @return Um handler para o evento
     */
    public AuthenticationFailureHandler failureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            preencherCabecalhosResposta(httpServletResponse);
            String content = criarMensagemErro(e);
            httpServletResponse.getWriter().append(content);
        };
    }

    /**
     * Cria uma mensagem de erro no formato JSON a partir da excecao recebida.
     *
     * @param e A excecao a ser tratada
     * @return Uma mensagem no formato JSON
     */
    private String criarMensagemErro(AuthenticationException e) {

        int tipoErro = 401;
        String mensagemErro = "Authentication failure";

        Throwable causa = e.getCause();
        if (causa != null && causa instanceof IExcecaoBoleia) {
            IExcecaoBoleia exBoleia = (IExcecaoBoleia) causa;
            tipoErro = exBoleia.getErro().getCodigo();
            mensagemErro = mensagens.obterMensagem(exBoleia.getErro().getChaveMensagem(), (Object[]) exBoleia.getArgs());
        }

        MensagemErro msg = new MensagemErro(tipoErro, Collections.singletonList(mensagemErro));
        return UtilitarioJson.toJSON(msg);
    }

    /**
     * Preenche cabeçalhos que devem ser enviados na resposta
     * @param response a resposta HTTP
     */
    private void preencherCabecalhosResposta(HttpServletResponse response) {
    }

    /**
     * Registra a sessao do spring
     * @return a sessao
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        try {
            FindByIndexNameSessionRepository sessionRepo = getApplicationContext().getBean(RepositorioSessoesRedis.class);
            return new SpringSessionBackedSessionRegistry(sessionRepo);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.debug(e.getMessage(), e);
            FindByIndexNameSessionRepository sessionRepo = new InMemorySessionRepository();
            return new SpringSessionBackedSessionRegistry(sessionRepo);
        }
    }

    /**
     * Simula um {@link FindByIndexNameSessionRepository} para utilizacao em ambientes locais, desprovidos de repositório de sessões distribuído
      */
    public static class InMemorySessionRepository extends MapSessionRepository implements FindByIndexNameSessionRepository<ExpiringSession> {
        @Override
        public Map<String, ExpiringSession> findByIndexNameAndIndexValue(String indexName, String indexValue) {
            return new HashMap<>();
        }
    }

    /**
     * Permite atribucao de valor mock para testes de unidade
     *
     * @param maximoSessoesPorUsuario O numero maximo de sessoes ativas para um dado usuario
     */
    public void setMaximoSessoesPorUsuario(Integer maximoSessoesPorUsuario) {
        this.maximoSessoesPorUsuario = maximoSessoesPorUsuario;
    }
}