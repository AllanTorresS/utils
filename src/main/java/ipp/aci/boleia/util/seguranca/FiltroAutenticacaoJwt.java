package ipp.aci.boleia.util.seguranca;

import com.auth0.jwt.interfaces.DecodedJWT;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAcessoSistemaExterno;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.vo.ResultadoValidacaoAcessoVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import ipp.aci.boleia.util.excecao.MensagemErro;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;

import static ipp.aci.boleia.util.UtilitarioFormatacao.obterString;


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

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (isUrlProtegida(request) && isMetodoDiferenteDeOptions(request)) {

            String encodedJwtToken = utilitarioJwt.extrairToken(request);
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
            }

            if (request.getRequestURL().toString().contains("/downloadTokenGeneration")) {
                enviarTokenDownload(request, response);
                return;
            }

            adicionarSameSiteResponseHeader(request, response);
        }

        chain.doFilter(request, response);
    }

    /**
     * Adiciona no cabeçalho da resposta o cookie cross site da session como samesite none e secure (Google versão >=80)
     * para todas as requisições
     *
     * @param request requisição realizada
     * @param response a resposta HTTP
     */
    private void adicionarSameSiteResponseHeader(HttpServletRequest request, HttpServletResponse response) {

        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            response.setHeader(HttpHeaders.SET_COOKIE, "SESSION="+sessao.getId()+"; SameSite=None; Secure");
        }
    }

    /**
     * Verifica se a sessão e o status do usuario autenticado são válidos.
     * Se sim, envia um novo token caso necessário (renovação automática).
     * Caso contrário, verifica a validade do token.
     * <br>
     * Se alguma validação falhar, a resposta de erro é escrita no objeto {@link HttpServletResponse}.
     *
     * @param response a resposta HTTP
     * @param encodedJwtToken o token jwt
     * @param fingerprint a string que representa o fingerprint
     * @return <b>true</b> para encerrar a execução do  e retornar ao cliente
     *     a resposta de erro que já foi escrita no parâmetro {@link HttpServletResponse}.
     *     <br><b>false</b> para prosseguir com a execução do .
     * @throws IOException excecao de entrada e saida
     */
    private boolean deveInterromperFiltroSessaoOuTokenInvalido(HttpServletResponse response, String encodedJwtToken, String fingerprint) throws IOException {
        if (validarSessaoUsuario(encodedJwtToken)) {
            if (!validarStatusUsuario(ambiente.getUsuarioLogado(), fingerprint, encodedJwtToken, response)) {
                return true;
            }
            renovadorTokenJwt.enviarNovoTokenCasoNecessario(encodedJwtToken, fingerprint, response);
        } else {
            if (!validarTokenJwt(response, encodedJwtToken, fingerprint)) {
                return true;
            }
        }
        return false;
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
     * Indica se o método (verbo) da requisição HTTP não foi OPTIONS
     *
     * @param request Requisição
     * @return Indicação se esta não é do tipo options
     */
    private boolean isMetodoDiferenteDeOptions(HttpServletRequest request) {
        return !HttpMethod.OPTIONS.matches(request.getMethod());
    }

    /**
     * Verifica se o token de download recebido esta coerente com a url requisitada
     * @param request a requisicao
     * @return true caso caso o token esteja válido
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
              Usuário gestor não possui perfil de usuário. Portanto, as permissões devem ser carregadas separadamente.
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
     * Reporta um erro de token invalido com código e mensagem personalizados
     *
     * @param response A resposta HTTP
     * @param codigoErro codigo do erro
     * @param mensagemErro mensagem amigável do erro
     * @throws IOException em caso de erro na escrita do corpo da reposta
     */
    private void enviarErroAutenticacao(HttpServletResponse response, Integer codigoErro, String mensagemErro) throws IOException {
        LOGGER.error("Erro na autenticação. [{}] {}", codigoErro, mensagemErro);

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
