package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dados.IAutenticacaoUsuarioDados;
import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IUsuarioMotoristaDados;
import ipp.aci.boleia.dominio.CodigoValidacaoTokenJwt;
import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.UsuarioMotorista;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoAcesso;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoAutenticacaoRemota;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.TELEFONE_CENTRAL_ATENDIMENTO_NUMERO;
import static ipp.aci.boleia.util.UtilitarioFormatacao.TAMANHO_CPF;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroZerosEsquerda;

/**
 * Prove mecanismos para autenticacao do usuario
 */
@Component
public class ProvedorAutenticacao implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvedorAutenticacao.class);

    private static final Integer ULTIMA_TENTATIVA_LOGIN_INCORRETO = 3;

    @Autowired
    private IServicosDeUsuario servicoUsuario;

    @Autowired
    private IUsuarioMotoristaDados repositorioUsuarioMotorista;

    @Autowired
    private IServicosDePermissao servicoPermissoes;

    @Autowired
    private IServicosDeCodigoValidacaoTokenJwt servicosDeCodigoValidacaoTokenJwt;

    @Autowired
    private IAutenticacaoUsuarioDados autenticacaoDados;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IConfiguracaoSistemaDados configuracaoSistema;

    @Autowired
    private IServicosDeControleAcessoPortal servicosDeControleAcessoPortal;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        try {
            boolean isPrecos = obterParametroRequest("analistaPrecos") != null;
            boolean isUsernameCpf = obterParametroRequest("cpf") != null;
            boolean isAutenticacaoMotorista = obterParametroRequest("motorista") != null;

            Usuario usuario;
            if(isPrecos) {
                usuario = montarUsuarioPrecos(auth.getName());
            } else if (isUsernameCpf) {
                usuario = servicoUsuario.obterPorCpfComPermissoes(auth.getName(), isAutenticacaoMotorista);
            } else {
                usuario = servicoUsuario.obterPorLoginComPermissoes(auth.getName());
            }
            if (usuario == null || (!usuario.isInterno() && !usuario.isPrecos() && !isUsernameCpf && !isAutenticacaoMotorista)) {
                usuario = servicoUsuario.obterPorEmailComPermissoes(auth.getName());
            }


            if(usuario != null) {
                validarUsuario(usuario);
            } else {
                registrarTentativaAcesso(auth.getName());
                throw new UsernameNotFoundException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS));
            }

            return autenticarUsuario(usuario, auth.getCredentials());
        } catch (ExcecaoBoleiaRuntime e) {
            throw new AuthenticationServiceException(null, e);
        }
    }

    /**
     * Registra uma tentativa de acesso de um login.
     *
     * @param login Login usado na tentativa de acesso.
     */
    private void registrarTentativaAcesso(String login) {
        boolean isPrecos = obterParametroRequest("analistaPrecos") != null;
        boolean isUsuarioInterno = obterParametroRequest("usuarioInterno") != null;
        boolean isPdv = obterParametroRequest("pdv") != null;
        boolean isMotorista = obterParametroRequest("motorista") != null;
        boolean isLoginGestor = obterParametroRequest("gestor") != null;
        boolean isLoginPortal = !isPdv && !isMotorista && !isLoginGestor;

        if(isPdv) {
            servicosDeControleAcessoPortal.registrarTentativaAcesso(login, TipoAcesso.PDV, false);
        } else if(isLoginPortal) {
            servicosDeControleAcessoPortal.registrarTentativaAcesso(login, TipoAcesso.PORTAL, isUsuarioInterno || isPrecos);
        }
    }

    /**
     * Valida a presença de um token recaptcha válido na requisição, caso seja necessário para um username.
     *
     * @param username Username utilizado na tentativa de acesso.log
     * @param tipoAcesso Tipo de acesso.
     */
    private void validarRecaptcha(String username, TipoAcesso tipoAcesso) {
        if(servicosDeControleAcessoPortal.precisaRecaptchaAcesso(username, tipoAcesso)) {
            String tokenRecaptcha = obterParametroRequest("tokenRecaptcha");
            utilitarioAmbiente.validarRecaptcha(tokenRecaptcha);
        }
    }

    /**
     * Verifica se o usuario correspondente as credenciais informadas eh valido.
     * Lanca excecao caso o usuario nao tenha sido localizado ou caso esteja desativado
     * ou excluido logicamente.
     *
     * @param usuario O usuario, caso localizado
     */
    private void validarUsuario(Usuario usuario) {
        if (!StatusAtivacao.ATIVO.getValue().equals(usuario.getStatus())
                || (usuario.getExcluido() != null && usuario.getExcluido())) {
            throw new DisabledException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_USUARIO_INVALIDO));
        }
        if (!usuario.isPrecos() && servicoUsuario.possuiBloqueioTemporario(usuario.getId())) {
            throw new DisabledException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_USUARIO_BLOQUEADO));
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * Autentica um usuario validando sua senha em tempo de execucao
     * @param usuario a validar a senha
     * @param senha a checar
     * @return autenticacao construida
     */
    public Authentication autenticarUsuario(Usuario usuario, Object senha) {
        if (usuario.isInterno() || usuario.isPrecos()) {
            return autenticarUsuarioInterno(usuario, senha);
        } else {
            Authentication authentication = autenticarUsuarioExterno(usuario, senha);
            if (usuario.isMotorista()) {
                UsuarioMotorista usuarioMotorista = repositorioUsuarioMotorista.obterPorUsuario(usuario);
                if (usuarioMotorista != null && usuarioMotorista.getDataExpiracaoSenhaTemporaria() != null && usuarioMotorista.getDataExpiracaoSenhaTemporaria().before(utilitarioAmbiente.buscarDataAmbiente())) {
                    throw new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_SENHA_EXPIRADA);
                }
            }
            return authentication;
        }
    }

    /**
     * Autentica um usuario interno, ou seja, um usuario que pertence ao dominio Ipiranga
     *
     * @param usuario O usuario, caso localizado
     * @param senha A senha informada
     * @return Um objeto Authentication, do Spring Security, caso usuario seja autenticado com sucesso
     */
    private Authentication autenticarUsuarioInterno(Usuario usuario, Object senha) {
        try {
            validarRecaptcha(usuario.getLogin(), TipoAcesso.PORTAL);

            boolean sucessoAutenticacao = autenticacaoDados.autenticar(usuario.getLogin(), (String) senha);
            if (sucessoAutenticacao) {
                TipoPerfilUsuario perfilNecessario = usuario.isPrecos() ? TipoPerfilUsuario.PRECOS : TipoPerfilUsuario.INTERNO;
                if (!autenticacaoDados.possuiPerfil(usuario.getLogin(), perfilNecessario)) {
                    servicosDeControleAcessoPortal.registrarTentativaAcesso(usuario.getLogin(), TipoAcesso.PORTAL, true);
                    throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS));
                }
                if (!usuario.isPrecos()) {
                    usuario.setPrimeiroLogin(servicoUsuario.registrarSucessoAutenticacao(usuario.getId()));
                }
                usuario = servicoUsuario.povoarPermissoesUsuario(usuario);
                usuario.setTokenJWT(utilitarioJwt.criarTokenUsuarioBoleia(usuario));
                usuario.setTipoTokenJwt(TipoTokenJwt.USUARIO_BOLEIA);

                servicosDeControleAcessoPortal.resetarControleAcesso(usuario.getLogin(), TipoAcesso.PORTAL, true);
                return InformacoesAutenticacao.build(usuario.getLogin(), senha, usuario);
            } else {
                if (!usuario.isPrecos()) {
                    registrarErroAutenticacao(usuario.getId());
                }
                servicosDeControleAcessoPortal.registrarTentativaAcesso(usuario.getLogin(), TipoAcesso.PORTAL, true);
                throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS));
            }
        } catch(ExcecaoAutenticacaoRemota e) {
            throw new AuthenticationServiceException(mensagens.obterMensagem(e.getErro().getChaveMensagem(), e.getArgs()), e);
        }
    }

    /**
     * Autentica um usuario externo, ou seja, um usuario que nao pertence ao dominio Ipiranga
     *
     * @param usuario O usuario, caso localizado
     * @param senha A senha informada
     * @return Um objeto Authentication, do Spring Security, caso usuario seja autenticado com sucesso
     */
    private Authentication autenticarUsuarioExterno(Usuario usuario, Object senha) {

        boolean credenciaisValidas = credenciaisValidas(usuario, senha);

        boolean isLoginPDV = obterParametroRequest("pdv") != null;
        boolean isLoginMotorista = obterParametroRequest("motorista") != null;
        boolean isLoginGestor = obterParametroRequest("gestor") != null;
        boolean isLoginPortal = !isLoginPDV && !isLoginMotorista && !isLoginGestor;

        if(isLoginPortal) {
            validarRecaptcha(usuario.getEmail(), TipoAcesso.PORTAL);
        } else if(isLoginPDV) {
            validarRecaptcha(formatarNumeroZerosEsquerda(usuario.getCpf(), TAMANHO_CPF), TipoAcesso.PDV);
        }
        if (credenciaisValidas) {
            usuario = servicoUsuario.povoarPermissoesUsuario(usuario);
            String username = usuario.getEmail();
            if (!isLoginPDV || possuiPermissao(usuario, ChavePermissao.PORTAL_PDV_ACESSAR)) {
                usuario.setPrimeiroLogin(servicoUsuario.registrarSucessoAutenticacao(usuario.getId()));
                if (isLoginPDV) {
                    usuario.setTokenJWT(utilitarioJwt.criarTokenUsuarioPdv(usuario, 0, getHeader("Fingerprint")));
                    usuario.setTipoTokenJwt(TipoTokenJwt.USUARIO_PDV);
                    mesclarPermissoesUsuarioPdv(usuario, TipoTokenJwt.USUARIO_PDV.getPermissoes());
                    servicosDeControleAcessoPortal.resetarControleAcesso(formatarNumeroZerosEsquerda(usuario.getCpf(), TAMANHO_CPF), TipoAcesso.PDV, false);
                } else if (isLoginMotorista) {
                    CodigoValidacaoTokenJwt codigoValidacaoTokenJwt = servicosDeCodigoValidacaoTokenJwt.registrarNovoCodigoParaUsuario(usuario);
                    usuario.setCodigoValidacaoTokenJwt(codigoValidacaoTokenJwt);
                    usuario.setTokenJWT(utilitarioJwt.criarTokenUsuarioMotorista(usuario));
                    usuario.setTipoTokenJwt(TipoTokenJwt.USUARIO_MOTORISTA);
                    username = usuario.getLogin();
                } else if(isLoginGestor) {
                    usuario.setTokenJWT(utilitarioJwt.criarTokenDispositivoFrotista(usuario));
                    usuario.setTipoTokenJwt(TipoTokenJwt.DISPOSITIVO_FROTISTA);
                    username = usuario.getUsernameParaDispositivoFrotista();
                } else {
                    usuario.setTokenJWT(utilitarioJwt.criarTokenUsuarioBoleia(usuario));
                    usuario.setTipoTokenJwt(TipoTokenJwt.USUARIO_BOLEIA);
                    servicosDeControleAcessoPortal.resetarControleAcesso(usuario.getEmail(), TipoAcesso.PORTAL, false);
                }

                return InformacoesAutenticacao.build(username, senha, usuario);
            }
        }

        if (!credenciaisValidas) {
            if(isLoginPortal) {
                servicosDeControleAcessoPortal.registrarTentativaAcesso(usuario.getEmail(), TipoAcesso.PORTAL, false);
            } else if(isLoginPDV) {
                servicosDeControleAcessoPortal.registrarTentativaAcesso(formatarNumeroZerosEsquerda(usuario.getCpf(), TAMANHO_CPF), TipoAcesso.PDV, false);
            }
            registrarErroAutenticacao(usuario.getId());
            if (ULTIMA_TENTATIVA_LOGIN_INCORRETO.equals(usuario.getNumeroErrosLogin())) {
                throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.ULTIMA_TENTATIVA_LOGIN));
            }
        }

        throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INVALIDAS));
    }

    /**
     * Adiciona ao objeto {@link Usuario} as permissões vinculadas a um tipo de token JWT.
     * O usuário já está inicializado com as permissões que foram vinculadas a ele pelos Perfis de Acesso.
     *
     * @param usuario usuario recuperado do banco de dados
     * @param permissoesTokenPdv conjunto fixo de permissões vinculadas ao token
     */
    private void mesclarPermissoesUsuarioPdv(Usuario usuario, String[] permissoesTokenPdv) {
        Set<String> setPerms  = usuario.getPermissoes().stream().map(Permissao::getChave).collect(Collectors.toSet());
        setPerms.addAll(Arrays.asList(permissoesTokenPdv));

        Set<Permissao> perms = setPerms.stream().map(chavePermissao -> {
            Permissao permissao = new Permissao();
            permissao.setChave(chavePermissao);
            return permissao;
        }).collect(Collectors.toSet());

        usuario.setPermissoes(perms);
    }

    /**
     * Registra uma tentativa mal sucedida de autenticacao e, caso o usuario tenha sido bloqueado,
     * envia uma mensagem para a tela (lanca excecao) informando a situacao.
     *
     * @param idUsuario O id do usuario
     */
    private void registrarErroAutenticacao(Long idUsuario) {
        if (servicoUsuario.registrarErroAutenticacao(idUsuario)) {
            throw new DisabledException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_USUARIO_BLOQUEADO));
        }
    }

    /**
     * Retorna true caso as credenciais informadas estejam compativeis com aquelas armazenadas em banco de dados
     *
     * @param usuario o usuario localizado no banco de dados
     * @param credenciais A senha informada pelo usuario
     * @return True caso as credenciais estejam de acordo com o esperado
     */
    private boolean credenciaisValidas(Usuario usuario, Object credenciais) {
        if (StringUtils.isBlank(usuario.getSenhaHash()) || StringUtils.isBlank(usuario.getSenhaSalt())) {
            String numeroTelefone = configuracaoSistema.buscarConfiguracoes(TELEFONE_CENTRAL_ATENDIMENTO_NUMERO).getParametro();
            throw new BadCredentialsException(null, new ExcecaoBoleiaRuntime(Erro.AUTENTICACAO_CREDENCIAIS_INDEFINIDAS, numeroTelefone));
        }
        byte[] senha = converterCredenciaisParaByteArray(credenciais);
        byte[] salt = UtilitarioCriptografia.fromBase64(usuario.getSenhaSalt());
        byte[] hashEsperado = UtilitarioCriptografia.fromBase64(usuario.getSenhaHash());
        return UtilitarioCriptografia.verificarHashBCrypt(senha, salt, hashEsperado);
    }

    /**
     * Le a senha do usuario como um vetor de bytes
     * @param credenciais A senha do usuario
     * @return A senha em byte array
     */
    private byte[] converterCredenciaisParaByteArray(Object credenciais)  {
        return ((String) credenciais).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Obtem o usuario logado na aplicacao
     *
     * @return O usuario logado, caso exista
     */
    public static Usuario getUsuarioLogado() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth instanceof InformacoesAutenticacao) {
                return ((InformacoesAutenticacao) auth).getUsuario();
            }
        }
        return null;
    }

    /**
     * Atualiza o usuario logado no contexto do spring,
     * forçando essa atualização do spring security para
     * propaga-la ate o redis
     * @param usuarioLogado O usuario logado
     */
    public static void updateUsuarioLogado(Usuario usuarioLogado) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth instanceof InformacoesAutenticacao) {
                ((InformacoesAutenticacao) auth).setUsuario(usuarioLogado);
                context.setAuthentication(auth);
                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession();
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            }
        }
    }

    /**
     * Obtem o IP de origem da requisicao
     *
     * @return IP identificado
     */
    public static String getIpOrigemRequisicao() {
        try {
            RequestAttributes requestAtts = RequestContextHolder.currentRequestAttributes();
            if (requestAtts != null) {
                return ((ServletRequestAttributes) requestAtts).getRequest().getRemoteAddr();
            }
        } catch (IllegalStateException e) {
            // Nada a fazer. Exceção é lançada durante a execução dos serviços agendados.
        }
        return null;
    }

    /**
     * Obtem um parametro (query string) do request.
     * Metodo publico para permitir mock nos testes de unidade.
     *
     * @param nomeParam o nome do parametro
     * @return O valor do parametro
     */
    private String obterParametroRequest(String nomeParam) {
        HttpServletRequest request = getRequest();
        return request != null ? request.getParameter(nomeParam) : null;
    }

    /**
     * Obtem um header do request do usuario caso presente
     * @param nome O nome do header
     * @return O header caso exista
     */
    private String getHeader(String nome) {
        HttpServletRequest request = getRequest();
        return request != null ? request.getHeader(nome) : null;
    }

    /**
     * Obtem o request do usuario
     * @return O request
     */
    private HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (IllegalStateException e) {
            LOGGER.debug(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Monta um usuario volatil analista de preços para autenticação interna do SAA
     *
     * @param login Login proveniente da tela
     * @return Usuario montado
     */
    private Usuario montarUsuarioPrecos(String login) {
        Usuario usuario = new Usuario();
        usuario.setNome(login);
        usuario.setTipoPerfil(TipoPerfilUsuario.PRECOS.obterEntidade());
        usuario.setLogin(login);
        usuario.setDataUltimoLogin(new Date());
        usuario.setId(-1L);
        usuario.setStatus(StatusAtivacao.ATIVO.getValue());
        usuario.setGestor(true);
        usuario.setExcluido(false);
        return usuario;
    }

    /**
     * Verifica se um usuario possui a permissao requerida
     *
     * @param usuario o usuario
     * @param chavePermissao a chave da permissao
     * @return true caso possua a permissao
     */
    public boolean possuiPermissao(Usuario usuario, String chavePermissao) {
        if (usuario != null && usuario.getPermissoes() != null) {
            String chave = ChavePermissao.getChave(chavePermissao);
            for (Permissao p : usuario.getPermissoes()) {
                if (p.getChave().equals(chave)) {
                    return true;
                }
            }
        }
        return false;
    }
}