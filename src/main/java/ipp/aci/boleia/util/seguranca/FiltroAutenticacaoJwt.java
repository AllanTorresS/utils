package ipp.aci.boleia.util.seguranca;

import com.auth0.jwt.interfaces.DecodedJWT;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAcessoSistemaExterno;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.servico.SistemaExternoSd;
import ipp.aci.boleia.dominio.vo.ResultadoValidacaoAcessoVo;
import static ipp.aci.boleia.util.UtilitarioFormatacao.obterString;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import ipp.aci.boleia.util.excecao.MensagemErro;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.rotas.ExternoRotas;
import ipp.aci.boleia.util.rotas.Rotas;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;


/**
 * Filtro de autenticacao JWT
 */
@Component
public class FiltroAutenticacaoJwt implements Filter {

    private static final String PARAM_DOWNLOAD_TOKEN = "downloadToken";
    private static final Logger LOGGER = LoggerFactory.getLogger(FiltroAutenticacaoJwt.class);

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IServicosDeUsuario servicosDeUsuario;

    @Autowired
    private IServicosDeValidacaoAcesso validacaoAcesso;

    @Autowired
    private IServicosDeReabilitacaoSistemaExterno servicosReabilitacao;

    @Autowired
    private IServicosDePermissao servicoPermissoes;

    @Autowired
    private RenovadorTokenJwt renovadorTokenJwt;

    @Autowired
    private IFiltroInterceptacaoForwardJwt filtroInterceptacaoForwardJwt;

    @Autowired
    private SistemaExternoSd sistemaExternoSd;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (filtroInterceptacaoForwardJwt.encaminharRequisicao(request, response)) {
            return;
        }

        if (isUrlProtegida(request) && isMetodoDiferenteDeOptions(request)) {
            String encodedJwtToken;
            if (isUrlWebHook(request)) {
                String encodedBasicToken = utilitarioJwt.extrairTokenBasic(request);
                SistemaExterno sistemaExterno = validarTokenJwt(response, encodedBasicToken);

                if (sistemaExterno != null) {
                    encodedJwtToken = utilitarioJwt.criarTokenSistemaExterno(sistemaExterno, 0L);
                } else {
                    return;
                }
            } else {
                encodedJwtToken = utilitarioJwt.extrairToken(request);
            }

            String fingerprint = utilitarioJwt.extrairFingerprint(request);

            if (StringUtils.isNotEmpty(request.getParameter(PARAM_DOWNLOAD_TOKEN))) {
                if (!validarTokenDownload(request)) {
                    enviarErroAutenticacao(response);
                    return;
                }
            } else if (encodedJwtToken == null) {
                enviarErroAutenticacao(response);
                return;
            } else {
                if (deveInterromperFiltroSessaoOuTokenInvalido(response, encodedJwtToken, fingerprint)) {
                    return;
                }
                Usuario usuario = ambiente.getUsuarioLogado();
                if (usuario != null && !StringUtils.isEmpty(request.getHeader(ambiente.getChaveHeaderUsuarioRelatorio()))) {
                    registrarUsuarioRelatorioSessao(request.getHeader(ambiente.getChaveHeaderUsuarioRelatorio()));
                }
            }

            if (request.getRequestURL().toString().contains("/downloadTokenGeneration")) {
                enviarTokenDownload(request, response);
                return;
            }

        }

        if (isMetodoDiferenteDeOptions(request) && isObjetoEstatico(request)) {
            adicionarSameSiteResponseHeader(request, response);
        }

        if (request.getRequestURL().toString().contains("/logout")) {
            limparCookies(request, response);
        }

        chain.doFilter(request, response);
    }

    /**
     * Registra um usu??rio que requisitou emiss??o de relat??rio na sess??o
     * @param id O id do usu??rio
     */
    private void registrarUsuarioRelatorioSessao(String id) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        try {
            Long idUsuario = Long.parseLong(id);
            Usuario usuario = servicosDeUsuario.obterPorIdSemIsolamentoComPermissoes(idUsuario);
            if (usuario != null) {
                session.setAttribute(ambiente.getChaveHeaderUsuarioRelatorio(), usuario);
            }
        } catch (NumberFormatException nfe) {
            LOGGER.debug(nfe.getMessage(), nfe);
        }
    }

    private void limparCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    }

    /**
     * Verifica se uma URL aponta para um recurso est??tico
     * @param request a requisi????o
     * @return true caso a URL aponte para um recurso est??tico, false caso contr??rio
     */
    private boolean isObjetoEstatico(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.contains(Rotas.BASE_API_ESTATICA + "/");
    }

    /**
     * Adiciona no cabe??alho da resposta o cookie cross site da session como samesite none e secure (Google vers??o >=80)
     * para todas as requisi????es
     *
     * @param request A requisi????o
     * @param response A resposta
     */
    private void adicionarSameSiteResponseHeader(HttpServletRequest request, HttpServletResponse response) {

        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            response.setHeader(HttpHeaders.SET_COOKIE, "SESSION="+sessao.getId()+"; SameSite=None; Secure");
        }
    }

    /**
     * Verifica se a sess??o e o status do usuario autenticado s??o v??lidos.
     * Se sim, envia um novo token caso necess??rio (renova????o autom??tica).
     * Caso contr??rio, verifica a validade do token.
     * <br>
     * Se alguma valida????o falhar, a resposta de erro ?? escrita no objeto {@link HttpServletResponse}.
     *
     * @param response a resposta HTTP
     * @param encodedJwtToken o token jwt
     * @param fingerprint a string que representa o fingerprint
     * @return <b>true</b> para encerrar a execu????o do  e retornar ao cliente
     *     a resposta de erro que j?? foi escrita no par??metro {@link HttpServletResponse}.
     *     <br><b>false</b> para prosseguir com a execu????o do .
     * @throws IOException excecao de entrada e saida
     */
    private boolean deveInterromperFiltroSessaoOuTokenInvalido(HttpServletResponse response, String encodedJwtToken, String fingerprint) throws IOException {
        if (validarSessaoUsuario(encodedJwtToken)) {
            if (!validarStatusUsuario(ambiente.getUsuarioLogado(), fingerprint, encodedJwtToken, response)) {
                return true;
            }
            renovadorTokenJwt.enviarNovoTokenCasoNecessario(encodedJwtToken, fingerprint, response);
        } else if (!validarTokenJwt(response, encodedJwtToken, fingerprint)) {
            return true;
        }
        return false;
    }

    /**
     * Valida o token de autentica????o basic recebido no webhook
     *
     * @param response A resposta HTTP
     * @param encodedJwtToken O token
     * @return True caso o token seja valido. False caso contrario
     * @throws IOException Em caso de erro no envio do response HTTP
     */
    private SistemaExterno validarTokenJwt(HttpServletResponse response, String encodedJwtToken) throws IOException {
        try {
            String token = new String(UtilitarioCriptografia.fromBase64(encodedJwtToken));
            String[] clientSecret = token.split(":");
            if (clientSecret.length != 2) {
                enviarErroAutenticacao(response);
                return null;
            }
            return sistemaExternoSd.verificarAutorizacaoBasic(clientSecret[0], clientSecret[1]);
        } catch (Exception e) {
            enviarErroAutenticacao(response);
            LOGGER.debug(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Valida o token jwt recebido
     *
     * @param response A resposta HTTP
     * @param encodedJwtToken O token
     * @param fingerprint O fingerprint do usuario
     * @return True caso o token seja valido. False caso contrario
     * @throws IOException Em caso de erro no envio do response HTTP
     */
    private boolean validarTokenJwt(HttpServletResponse response, String encodedJwtToken, String fingerprint) throws IOException {
        try {
            DecodedJWT token = utilitarioJwt.validarTokenAutenticacao(encodedJwtToken, fingerprint);
            if (token == null) {
                enviarErroAutenticacao(response);
                return false;
            }

            Usuario usuario = montarUsuarioParaRegistroContextoSeguranca(encodedJwtToken, token);
            if (!validarStatusUsuario(usuario, fingerprint, encodedJwtToken, response)) {
                return false;
            }
            registrarUsuarioContextoSeguranca(usuario, encodedJwtToken, token);

            renovadorTokenJwt.enviarNovoTokenCasoNecessario(encodedJwtToken, fingerprint, response);

        } catch (ExcecaoTokenJwtExpirado e) {
            if (servicosReabilitacao.iniciarProcedimentoReabilitacao(encodedJwtToken, fingerprint)) {
                enviarErroAutenticacao(response, e.getErro().getCodigo(), null);
            } else {
                enviarErroAutenticacao(response);
            }
            LOGGER.debug(e.getMessage(), e);
            return false;
        }

        return true;
    }

    /**
     * Verifica se a sessao do usuario contem o mesmo token recebido no header da requisicao.
     *
     * @param encodedJwtToken O token recebido na requisicao
     * @return True caso a sessao do usuario contenha o mesmo token recebido na requisicao
     */
    private boolean validarSessaoUsuario(String encodedJwtToken) {
        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        return usuarioLogado != null && encodedJwtToken.equals(usuarioLogado.getTokenJWT());
}

    /**
     * Verifica se o usuario possui algum tipo de impedimento de acesso.
     *
     * @param usuario O usuario a ser validado
     * @param fingerprint O fingerprint enviado na requisicao
     * @param encodedJwtToken O token jwt recebido na requisicao
     * @param response A resposta HTTP a ser enviada ao usuario
     * @return False caso o usuario possua qualquer impedimento de acesso ao sistema
     * @throws IOException Quando algum erro ocorre na escrita da resposta HTTP
     */
    private boolean validarStatusUsuario(Usuario usuario, String fingerprint, String encodedJwtToken, HttpServletResponse response) throws IOException {
        ResultadoValidacaoAcessoVo resultado = validacaoAcesso.validarAcesso(usuario, fingerprint, encodedJwtToken);
        boolean valido = StatusAcessoSistemaExterno.SUCESSO.equals(resultado.getStatus());
        if (!valido) {
            enviarErroAutenticacao(response, resultado.getStatus().getCodigo(), resultado.getMensagem());
        }
        return valido;
    }

    /**
     * Verifica se a url do request recebido deve ser protegida por token JWT
     * @param request A requisicao
     * @return true caso deva ser protegida
     */
    private boolean isUrlProtegida(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return (url.contains(Rotas.BASE_API + "/") || url.contains(Rotas.LOGOUT))
                && !url.contains(Rotas.BASE_API_DOCS + "/")
                && !url.contains(Rotas.BASE_API_PUBLICA + "/")
                && !url.contains(Rotas.BASE_API_ESTATICA + "/");
    }

    /**
     * Verifica se a url ?? de um webhook da solu????o.
     * Webhooks respondem por uma autentica????o do tipo basic
     * @param request A requisicao
     * @return true caso seja um webhook
     */
    private boolean isUrlWebHook(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return (url.contains(ExternoRotas.WEBHOOK_API + "/"));
    }

    /**
     * Indica se o m??todo (verbo) da requisi????o HTTP n??o foi OPTIONS
     *
     * @param request Requisi????o
     * @return Indica????o se esta n??o ?? do tipo options
     */
    private boolean isMetodoDiferenteDeOptions(HttpServletRequest request) {
        return !HttpMethod.OPTIONS.matches(request.getMethod());
    }

    /**
     * Verifica se o token de download recebido esta coerente com a url requisitada
     * @param request a requisicao
     * @return true caso caso o token esteja v??lido
     */
    private boolean validarTokenDownload(HttpServletRequest request) {
        String strToken = request.getParameter(PARAM_DOWNLOAD_TOKEN);
        String url = utilitarioJwt.validarTokenDownloadArquivo(strToken);
        return url != null && request.getRequestURL().toString().endsWith(url);
    }

    /**
     * Monta um objeto Usuario para povoamento no contexto de seguranca do Spring, a partir dos dados do token
     *
     * @param strToken O token codificado
     * @param token O token decodificado
     */
    private Usuario montarUsuarioParaRegistroContextoSeguranca(String strToken, DecodedJWT token) {
        Usuario usuario;
        if (utilitarioJwt.isTokenUsuarioPDV(token)) {
            usuario = servicosDeUsuario.obterPorEmailComPermissoes(utilitarioJwt.getNomeUsuario(token));
            usuario.setPermissoes(new HashSet<>());
            usuario.getPermissoes().addAll(utilitarioJwt.extrairPermissoesToken(token));
        } else if (utilitarioJwt.isTokenUsuarioMotorista(token)) {
            usuario = utilitarioJwt.montarUsuarioMotoristaJwt(token, strToken);
        } else if(utilitarioJwt.isTokenDispositivoFrotista(token)) {
            usuario = servicosDeUsuario.obterPorEmailComPermissoes(utilitarioJwt.getNomeUsuario(token));

            /*
              Usu??rio gestor n??o possui perfil de usu??rio. Portanto, as permiss??es devem ser carregadas separadamente.
             */
            usuario.setPermissoes(new HashSet<>());
            usuario.getPermissoes().addAll(servicoPermissoes.obterPermissoes(TipoPerfilUsuario.FROTA));
        } else if (utilitarioJwt.isTokenUsuarioBoleia(token)) {
            usuario = servicosDeUsuario.obterPorIdComPermissoes(utilitarioJwt.getIdentificadorUsuario(token));
            if (usuario != null){
                servicosDeUsuario.povoarPermissoesUsuario(usuario);
            }else{
                usuario = utilitarioJwt.montarUsuarioJwt(token, strToken);
            }
        } else {
            usuario = utilitarioJwt.montarUsuarioJwt(token, strToken);
        }
        usuario.setTokenJWT(strToken);
        usuario.setTipoTokenJwt(utilitarioJwt.getTipoToken(token));
        return usuario;
    }

    /**
     * Registra o usuario no contexto de seguranca do Spring
     *
     * @param usuario O usuario montado a partir dos dados do token
     * @param strToken O token codificado
     * @param token O token decodificado
     */
    private void registrarUsuarioContextoSeguranca(Usuario usuario, String strToken, DecodedJWT token) {
        InformacoesAutenticacao info;
        if (utilitarioJwt.isTokenUsuarioPDV(token)) {
            info = InformacoesAutenticacao.build(usuario.getEmail(), strToken, usuario);
        } else if(utilitarioJwt.isTokenDispositivoFrotista(token)) {
            info = InformacoesAutenticacao.build(usuario.getUsernameParaDispositivoFrotista(), strToken, usuario);
        } else if (usuario.getLogin()!= null) {
            info = InformacoesAutenticacao.build(usuario.getLogin(), strToken, usuario);
        } else {
            info = InformacoesAutenticacao.build(usuario.getEmail(), strToken, usuario);
        }
        SecurityContextHolder.getContext().setAuthentication(info);
    }

    /**
     * Envia uma resposta contendo um token temporario para execucao de um download de arquivo
     *
     * @param request A requisicao
     * @param response A resposta
     */
    private void enviarTokenDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String param = request.getParameter("param");
        if (StringUtils.isNotEmpty(param)) {
            TokenDownloadParam tokenParam = UtilitarioJson.toObject(param, TokenDownloadParam.class);
            String token = utilitarioJwt.criarTokenDownloadArquivo(tokenParam.getFileUrl());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ServletOutputStream out = response.getOutputStream();
            out.write(("{\"token\":\"" + token + "\"}").getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    /**
     * Reporta um erro de token invalido com c??digo e mensagem personalizados
     *
     * @param response A resposta HTTP
     * @param codigoErro codigo do erro
     * @param mensagemErro mensagem amig??vel do erro
     * @throws IOException em caso de erro na escrita do corpo da reposta
     */
    private void enviarErroAutenticacao(HttpServletResponse response, Integer codigoErro, String mensagemErro) throws IOException {
        LOGGER.error("Erro na autentica????o. [{}] {}", codigoErro, mensagemErro);

        int codigo = codigoErro != null ? codigoErro : Erro.TOKEN_JWT_INVALIDO.getCodigo();
        String mensagem = obterString(mensagemErro, mensagens.obterMensagem(Erro.TOKEN_JWT_INVALIDO.getChaveMensagem()));
        MensagemErro msgVo = new MensagemErro(codigo, Collections.singletonList(mensagem));

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        byte[] bytes = UtilitarioJson.toJSON(msgVo).getBytes(StandardCharsets.UTF_8);
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
    }

    /**
     * Reporta um erro de token invalido
     *
     * @param response A resposta HTTP
     * @throws IOException em caso de erro na escrita do corpo da reposta
     */
    private void enviarErroAutenticacao(HttpServletResponse response) throws IOException {
        enviarErroAutenticacao(response, null, null);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nada a fazer
    }

    @Override
    public void destroy() {
        // nada a fazer
    }
}
