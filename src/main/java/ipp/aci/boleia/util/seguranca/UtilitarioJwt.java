package ipp.aci.boleia.util.seguranca;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.ModuloInterno;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Rede;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroZerosEsquerda;

/**
 * Ferramentas para facilitar a manipulacao de tokens JWT
 */
@Component
public class UtilitarioJwt {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilitarioJwt.class);

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String BEARER_NAMESPACE = "Bearer";
    private static final String FINGERPRINT_HEADER_NAME = "Fingerprint";

    private static final String CAMPO_ISSUER                     = "Boleia";
    private static final String CAMPO_USUARIO_ID                 = "usuario.id";
    private static final String CAMPO_USUARIO_NOME               = "usuario.nome";
    private static final String CAMPO_USUARIO_TIPO               = "usuario.tipo";
    private static final String CAMPO_USUARIO_PERMISSOES         = "usuario.permissoes";
    private static final String CAMPO_USUARIO_FROTA              = "usuario.frota";
    private static final String CAMPO_USUARIO_REDE               = "usuario.rede";
    private static final String CAMPO_USUARIO_PONTOS_VENDA       = "usuario.pontosVenda";
    private static final String CAMPO_USUARIO_PONTOS_VENDA_CORP  = "usuario.pontosVendaCorp";
    private static final String CAMPO_USUARIO_MOTORISTA          = "usuario.motorista";
    private static final String CAMPO_USUARIO_FINGERPRINT        = "usuario.fingerprint";
    private static final String CAMPO_TOKEN_VERSAO               = "token.versao";
    private static final String CAMPO_TOKEN_TIPO                 = "token.tipo";
    private static final String CAMPO_TOKEN_CONTADOR_RENOVACOES  = "token.contadorRenovacoes";
    private static final String CAMPO_TOKEN_DATA_GERACAO         = "token.dataGeracao";
    private static final String CAMPO_TOKEN_CODIGO_VALIDACAO     = "token.codigoValidacao";
    private static final String CAMPO_URL = "url";

    private static final double LIMIAR_EXPIRACAO_TOKEN = 0.50;
    private static final double DURACAO_TURNO_TRABALHO_HORAS = 10;

    @Value("${jwt.signing.secret}")
    private String senhaAssinatura;

    @Autowired
    private VerificadorVersaoTokenJwt verificadorVersaoTokenJwt;

    private Algorithm algoritmo;
    private JWTVerifier verificador;

    /**
     * Instancia o algoritmo de assinatura dos tokens
     */
    @PostConstruct
    public void init() {
        try {
            this.algoritmo = Algorithm.HMAC256(senhaAssinatura);
            this.verificador = JWT.require(algoritmo).withIssuer(CAMPO_ISSUER).build();
        } catch (UnsupportedEncodingException e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Recupera o token JWT a partir dos headers HTTP
     *
     * @param request A requisicao HTTP
     * @return O token codificado
     */
    public String extrairToken(HttpServletRequest request) {
        String token = null;
        String authorization = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if(!StringUtils.isEmpty(authorization)) {
            if(authorization.contains(BEARER_NAMESPACE)) {
                authorization = authorization.replace(BEARER_NAMESPACE, "");
                token = StringUtils.trim(authorization);
            }
        }
        return token;
    }

    /**
     * Exige um tipo de token na requisição.
     * Caso nao possua, lança um erro de autorização.
     *
     * @param request Requisição usada
     * @param tipoTokenJwt Tipo de token exigido.
     */
    public void exigirTipoToken(HttpServletRequest request, TipoTokenJwt tipoTokenJwt) {
        String token = extrairToken(request);
        if(!isTipoTokenIgual(JWT.decode(token), tipoTokenJwt)) {
            throw new AccessDeniedException(null);
        }
    }

    /**
     * Recupera o header de fingerprint do request
     *
     * @param request A requisicao HTTP
     * @return O fingerprint
     */
    public String extrairFingerprint(HttpServletRequest request) {
        return request.getHeader(FINGERPRINT_HEADER_NAME);
    }

    /**
     * Cria um token JWT para o sistema SalesForce
     *
     * @return O token JWT
     */
    public String criarTokenSalesForce() {
        return criarTokenJWT(
            TipoTokenJwt.SALES_FORCE,
            null,
            TipoTokenJwt.SALES_FORCE.getTipoPerfil(),
            gerarIdAleatorio(),
            "SalesForce",
            null,
            null,
            TipoTokenJwt.SALES_FORCE.getPermissoes(),
            null,
            null,
            null,
            null,
            null);
    }

    /**
     * Cria um token JWT para o um dispositivo de motorista
     *
     * @param dispositivo O dispositivo do motorista
     * @return O token JWT
     */
    public String criarTokenDispositivoMotorista(DispositivoMotorista dispositivo) {
        return criarTokenJWT(
            TipoTokenJwt.DISPOSITIVO_MOTORISTA,
            null,
            TipoTokenJwt.DISPOSITIVO_MOTORISTA.getTipoPerfil(),
            dispositivo.getId(),
            dispositivo.getMotorista().getNome(),
            UtilitarioFingerprint.montarFingerprint(dispositivo),
            null,
            TipoTokenJwt.DISPOSITIVO_MOTORISTA.getPermissoes(),
            dispositivo.getFrota().getId(),
            null,
            dispositivo.getMotorista().getId(),
            null,
            null);
    }

    /**
     * Cria um token JWT para acesso as APIs de integracao com os sistemas dos frotistas
     *
     * @param frota A frota
     * @return O token JWT
     */
    public String criarTokenApiFrotista(Frota frota) {
        return criarTokenJWT(
            TipoTokenJwt.API_FROTISTA,
            null,
            TipoTokenJwt.API_FROTISTA.getTipoPerfil(),
            gerarIdAleatorio(),
            frota.getNomeRazaoFrota(),
            null,
            0L,
            TipoTokenJwt.API_FROTISTA.getPermissoes(),
            frota.getId(),
            null,
            null,
            null,
            null);
    }

    /**
     * Cria um token JWT para um usuario do PDV
     *
     * @param usuario O usuario do PDV
     * @param contadorRenovacoes O contador de renovacoes do token
     * @param fingerprint O fingerprint do dispositivo
     * @return O token gerado
     */
    public String criarTokenUsuarioPdv(Usuario usuario, long contadorRenovacoes, String fingerprint) {

        Set<Long> idsPvs      = usuario.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toSet());
        Set<Long> codsCorpPvs = usuario.getPontosDeVenda().stream().map(PontoDeVenda::getCodigoCorporativo).collect(Collectors.toSet());

        Set<String> setPerms  = usuario.getPermissoes().stream().map(Permissao::getChave).collect(Collectors.toSet());
        setPerms.addAll(Arrays.asList(TipoTokenJwt.USUARIO_PDV.getPermissoes()));
        List<String> permissoes = new ArrayList<>(setPerms);

        return criarTokenJWT(
            TipoTokenJwt.USUARIO_PDV,
            null,
            TipoPerfilUsuario.obterPorValor(usuario.getTipoPerfil().getId()),
            usuario.getId(),
            usuario.getEmail(),
            fingerprint,
            contadorRenovacoes,
            permissoes.toArray(new String[permissoes.size()]),
            null,
            usuario.getRede().getId(),
            null,
            idsPvs.toArray(new Long[idsPvs.size()]),
            codsCorpPvs.toArray(new Long[codsCorpPvs.size()]));
    }

    /**
     * Cria um token JWT para um sistema de integração
     *
     * @param sistema O sistema de integração
     * @param contadorRenovacoes O contador de renovacoes do token
     * @return O token gerado
     */
    public String criarTokenSistemaExterno(SistemaExterno sistema, Long contadorRenovacoes) {

        Set<String> setPerms  = sistema.getPermissoes().stream().map(Permissao::getChave).collect(Collectors.toSet());
        List<String> permissoes = new ArrayList<>(setPerms);

        return criarTokenJWT(
                TipoTokenJwt.SISTEMA_EXTERNO,
                null,
                TipoPerfilUsuario.SISTEMA_EXTERNO,
                sistema.getId(),
                null,
                null,
                contadorRenovacoes,
                permissoes.toArray(new String[permissoes.size()]),
                null,
                null,
                null,
                null,
                null);
    }


    /**
     * Cria um token JWT para um módulo interno da aplicação
     *
     * @param moduloInterno O módulo interno
     * @return O token gerado
     */
    public String criarTokenModuloInterno(ModuloInterno moduloInterno) {

        Set<String> setPerms  = moduloInterno.getPermissoes().stream().map(Permissao::getChave).collect(Collectors.toSet());
        List<String> permissoes = new ArrayList<>(setPerms);

        return criarTokenJWT(
                TipoTokenJwt.MODULO_INTERNO,
                null,
                TipoPerfilUsuario.MODULO_INTERNO,
                moduloInterno.getId(),
                null,
                null,
                0L,
                permissoes.toArray(new String[permissoes.size()]),
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Cria um token JWT para uma comanda digital para Revenda
     *
     * @param comanda A comanda digital
     * @return O token JWT
     */
    public String criarTokenComandaDigitalRevenda(ComandaDigital comanda) {
        return criarTokenJWT (
                TipoTokenJwt.COMANDA_DIGITAL,
                null,
                TipoPerfilUsuario.REVENDA,
                comanda.getId(),
                comanda.getNome(),
                UtilitarioFingerprint.montarFingerprint(comanda),
                null,
                TipoTokenJwt.COMANDA_DIGITAL.getPermissoes(),
                null,
                comanda.getPontoVenda().getRede().getId(),
                null,
                new Long[]{comanda.getPontoVenda().getId()} ,
                new Long[]{comanda.getPontoVenda().getCodigoCorporativo()});
    }

    /**
     * Cria um token JWT para uma comanda digital para Frota
     *
     * @param comanda A comanda digital
     * @return O token JWT
     */
    public String criarTokenComandaDigitalFrota(ComandaDigital comanda) {
        return criarTokenJWT (
                TipoTokenJwt.COMANDA_DIGITAL,
                null,
                TipoPerfilUsuario.FROTA,
                comanda.getId(),
                comanda.getNome(),
                UtilitarioFingerprint.montarFingerprint(comanda),
                null,
                TipoTokenJwt.COMANDA_DIGITAL.getPermissoes(),
                comanda.getFrota().getId(),
                null,
                null,
                null,
                null);
    }

    /**
     * Cria um token para um usuario do Boleia, autenticado via login e senha diretamente no Portal
     *
     * @param usuario O usuario
     * @return O token gerado
     */
    public String criarTokenUsuarioBoleia(Usuario usuario) {
        return criarTokenJWT (
            TipoTokenJwt.USUARIO_BOLEIA,
            null,
            TipoPerfilUsuario.obterPorValor(usuario.getTipoPerfil().getId()),
            usuario.getId(),
            usuario.getNome(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null);
    }

    /**
     * Cria um token para o usuario de um motorista, autenticado via login e senha diretamente no Portal
     *
     * @param usuario O usuario
     * @return O token gerado
     */
    public String criarTokenUsuarioMotorista(Usuario usuario) {
        return criarTokenJWT (
                TipoTokenJwt.USUARIO_MOTORISTA,
                usuario.getCodigoValidacaoTokenJwt().getCodigoValidacao(),
                TipoTokenJwt.USUARIO_MOTORISTA.getTipoPerfil(),
                usuario.getId(),
                formatarNumeroZerosEsquerda(usuario.getCpf(), 11),
                null,
                null,
                TipoTokenJwt.USUARIO_MOTORISTA.getPermissoes(),
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Cria um token para o usuario frotista no app do gestor
     *
     * @param usuario O usuario
     * @return O token gerado
     */
    public String criarTokenDispositivoFrotista(Usuario usuario) {
        return criarTokenJWT (
                TipoTokenJwt.DISPOSITIVO_FROTISTA,
                null,
                TipoTokenJwt.DISPOSITIVO_FROTISTA.getTipoPerfil(),
                usuario.getId(),
                usuario.getEmail()  ,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    /**
     * Cria um token para download de um arquivo
     *
     * @param url A URL desejada para download
     * @return O token gerado
     */
    public String criarTokenDownloadArquivo(String url) {
        try {
            return JWT.create()
                .withExpiresAt(UtilitarioCalculoData.adicionarHorasData(new Date(), TipoTokenJwt.DOWNLOAD_ARQUIVO.getDuracaoHoras()))
                .withClaim(CAMPO_URL, url)
                .withIssuer(CAMPO_ISSUER)
                .sign(algoritmo);
        } catch (JWTCreationException e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
    
    /**
     * Cria um token JWT de acordo com os parametros informados
     *
     * @param tipo O tipo do token
     * @param codigoValidacaoTokenJwt Código de validação do token jwt para usuário autenticados via token jwt
     * @param tipoPerfil O tipo de perfil do usuario
     * @param idUsuario  O id do usuario
     * @param nomeUsuario O nome do usuario
     * @param fingerprint O identificador do hardware do dispositivo do usuario
     * @param contadorRenovacoes O contador de renovacoes do token
     * @param permissoes As permissoes a serem concedidas
     * @param idFrota  O id da frota associado
     * @param idRede O id da rede associada
     * @param idMotorista O id do motorista associado
     * @param idsPontoVenda Os ids dos ponto de venda associados
     * @param codsCorpPontoVenda Os codigos corporativos dos pontos de venda associados
     * @return O token JWT contendo as informacoes recebidas
     */
    private String criarTokenJWT(TipoTokenJwt tipo, Long codigoValidacaoTokenJwt, TipoPerfilUsuario tipoPerfil, Long idUsuario, String nomeUsuario, String fingerprint, Long contadorRenovacoes, String[] permissoes, Long idFrota, Long idRede, Long idMotorista, Long[] idsPontoVenda, Long[] codsCorpPontoVenda) {
        try {
            Date agora = new Date();
            return JWT.create()
                .withExpiresAt(UtilitarioCalculoData.adicionarHorasData(agora, tipo.getDuracaoHoras()))
                .withClaim(CAMPO_USUARIO_ID,                     idUsuario)
                .withClaim(CAMPO_USUARIO_NOME,                   nomeUsuario)
                .withClaim(CAMPO_USUARIO_TIPO,                   tipoPerfil.name())
                .withClaim(CAMPO_USUARIO_FROTA,                  idFrota)
                .withClaim(CAMPO_USUARIO_REDE,                   idRede)
                .withArrayClaim(CAMPO_USUARIO_PONTOS_VENDA,      idsPontoVenda)
                .withArrayClaim(CAMPO_USUARIO_PONTOS_VENDA_CORP, codsCorpPontoVenda)
                .withClaim(CAMPO_USUARIO_MOTORISTA,              idMotorista)
                .withClaim(CAMPO_USUARIO_FINGERPRINT,            UtilitarioFingerprint.calcularHashFingerprint(fingerprint))
                .withClaim(CAMPO_TOKEN_VERSAO,                   verificadorVersaoTokenJwt.getVersaoCorrente(tipo))
                .withClaim(CAMPO_TOKEN_TIPO,                     tipo.name())
                .withClaim(CAMPO_TOKEN_DATA_GERACAO,             agora)
                .withClaim(CAMPO_TOKEN_CONTADOR_RENOVACOES,      contadorRenovacoes != null ? contadorRenovacoes : 0L)
                .withArrayClaim(CAMPO_USUARIO_PERMISSOES,        permissoes)
                .withClaim(CAMPO_TOKEN_CODIGO_VALIDACAO,         codigoValidacaoTokenJwt)
                .withIssuer(CAMPO_ISSUER)
                .sign(algoritmo);
        } catch (JWTCreationException e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Valida um token JWT
     *
     * @param tokenString O token a ser validado
     * @param fingerprintDaRequisicao O identificador do hardware do cliente, caso presente na requisicao
     * @return O token JWT decodificado caso valido, ou null caso contrario
     * @throws ExcecaoTokenJwtExpirado Caso o token JWT passado esteja expirado
     */
    public DecodedJWT validarTokenAutenticacao(String tokenString, String fingerprintDaRequisicao) throws ExcecaoTokenJwtExpirado {
        try {
            DecodedJWT token = verificador.verify(tokenString);
            TipoTokenJwt tipoToken = getTipoToken(token);
            String versao = getVersao(token);
            if (!verificadorVersaoTokenJwt.isVersaoValida(tipoToken, versao)) {
                LOGGER.info("Versao do Token JWT invalida: {} {}", tokenString, versao);
                token = null;
            }
            if (verificadorVersaoTokenJwt.isFingerprintObrigatorio(fingerprintDaRequisicao, tipoToken, versao)) {
                String hashFingerprintToken = getFingerprint(token);
                if (fingerprintDaRequisicao == null || hashFingerprintToken == null) {
                    LOGGER.info("Fingerprint nao informado na requisicao ou no token: {} {}", tokenString, fingerprintDaRequisicao);
                    token = null;
                } else {
                    String hashFingerprintEsperado = UtilitarioFingerprint.calcularHashFingerprint(fingerprintDaRequisicao);
                    if (!hashFingerprintToken.equals(hashFingerprintEsperado)) {
                        LOGGER.info("Fingerprint do Token JWT invalido: {} {}", tokenString, hashFingerprintToken);
                        token = null;
                    }
                }
            }
            return token;
        } catch(TokenExpiredException e) {
            LOGGER.debug(e.getMessage(), e);
            throw new ExcecaoTokenJwtExpirado();
        } catch(JWTVerificationException e) {
            LOGGER.info("Token JWT invalido: {}", tokenString, e);
            return null;
        }
    }

    /**
     * Valida o token JWT utilizado para download de arquivos
     *
     * @param tokenString O token a ser validado
     * @return O token JWT
     */
    public String validarTokenDownloadArquivo(String tokenString) {
        try {
            DecodedJWT token = verificador.verify(tokenString);
            return token.getClaim(CAMPO_URL).asString();
        } catch(JWTVerificationException e) {
            LOGGER.info("Token JWT invalido: {}", tokenString, e);
            return null;
        }
    }

    /**
     * Monta um objeto {@link Usuario} a partir das informacoes do token JWT
     *
     * @param jwt O token
     * @param encodedJwtToken token encriptado
     * @return O objeto usuario com os dados contidos no token
     */
    public Usuario montarUsuarioJwt(DecodedJWT jwt, String encodedJwtToken) {
        Long idUsuario = jwt.getClaim(CAMPO_USUARIO_ID).asLong();
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario != null ? idUsuario : gerarIdAleatorio());
        usuario.setLogin(Long.toString(gerarIdAleatorio()));
        usuario.setNome(jwt.getClaim(CAMPO_USUARIO_NOME).asString());
        usuario.setDataUltimoLogin(new Date());
        usuario.setStatus(StatusAtivacao.ATIVO.getValue());
        usuario.setGestor(false);
        usuario.setExcluido(false);
        usuario.setPermissoes(new HashSet<>());
        usuario.setTokenJWT(encodedJwtToken);
        usuario.setTipoTokenJwt(getTipoToken(jwt));
        usuario.getPermissoes().addAll(extrairPermissoesToken(jwt));
        povoarCamposIsolamentoDados(usuario, jwt);
        return usuario;
    }

    /**
     * Monta um objeto {@link Usuario} para o perfil {@link TipoPerfilUsuario}.MOTORISTA
     * a partir das informacoes do token JWT
     *
     * @param jwt O token
     * @param encodedJwtToken token encriptado
     * @return O objeto usuario com os dados contidos no token
     */
    public Usuario montarUsuarioMotoristaJwt(DecodedJWT jwt, String encodedJwtToken) {
        Usuario usuario = montarUsuarioJwt(jwt, encodedJwtToken);
        usuario.setCpf(Long.parseLong(usuario.getNome()));
        usuario.setNome(null);
        return usuario;
    }

    /**
     * Extrai a lista de permissoes contidas no token JWT
     *
     * @param token O token
     * @return A lista de permissoes
     */
    public List<Permissao> extrairPermissoesToken(DecodedJWT token) {
        List<Permissao> resultado = new ArrayList<>();
        List<String> permissoesJwt = token.getClaim(CAMPO_USUARIO_PERMISSOES).asList(String.class);
        if (permissoesJwt != null) {
            for (String permJwt : permissoesJwt) {
                Permissao p = new Permissao();
                p.setChave(permJwt);
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Método que verifica se a expiração do token está próxima sem realizar a verificação de assintura do mesmo.
     * <br>
     * <b>Não</b> utilizar quando o token não vier de uma fonte confiável.
     *
     * @param encodedToken O token encodado vindo de uma fonte confiável.
     * @return Se está próximo de sua expiração.
     */
    public boolean verificarTokenProximoExpiracaoSemValidarAssinatura(String encodedToken) {
        DecodedJWT decodedJWT = JWT.decode(encodedToken);
        return expiracaoProximaCustomizado(decodedJWT);
    }

    /**
     * Povoa o objeto Usuario com os campos de isolamento de dados
     * (frota, rede, perfis, permissões, etc)
     *
     * @param usuario O usuario
     * @param jwt O token
     */
    private void povoarCamposIsolamentoDados(Usuario usuario, DecodedJWT jwt) {

        Long idFrota = jwt.getClaim(CAMPO_USUARIO_FROTA).asLong();
        if (idFrota != null) {
            Frota frota = new Frota();
            frota.setId(idFrota);
            usuario.setFrota(frota);
        }

        Long idRede = jwt.getClaim(CAMPO_USUARIO_REDE).asLong();
        if (idRede != null) {
            Rede rede = new Rede();
            rede.setId(idRede);
            usuario.setRede(rede);
        }

        Long idMotorista = jwt.getClaim(CAMPO_USUARIO_MOTORISTA).asLong();
        if (idMotorista != null) {
            Motorista motorista = new Motorista();
            motorista.setId(idMotorista);
            usuario.setMotorista(motorista);
        }

        Long[] idsPv = jwt.getClaim(CAMPO_USUARIO_PONTOS_VENDA).asArray(Long.class);
        if (idsPv != null) {
            usuario.setPontosDeVenda(new ArrayList<>());
            for (Long idPv : idsPv) {
                PontoDeVenda pv = new PontoDeVenda();
                pv.setId(idPv);
                usuario.getPontosDeVenda().add(pv);
            }
        }

        usuario.setTipoPerfil(TipoPerfilUsuario.valueOf(jwt.getClaim(CAMPO_USUARIO_TIPO).asString()).obterEntidade());
    }

    /**
     * Verifica se é um token de usuario (humano, autenticado via login e senha através do Portal)
     *
     * @param jwt O token
     * @return true se e um token de usuario humano
     */
    public boolean isTokenUsuarioBoleia(DecodedJWT jwt) {
        return isTipoTokenIgual(jwt, TipoTokenJwt.USUARIO_BOLEIA);
    }

    /**
     * Verifica se é um token de usuario do PDV (humano, autenticado via login e senha no Portal do PDV)
     *
     * @param jwt O token
     * @return true se e um token de usuario humano
     */
    public boolean isTokenUsuarioPDV(DecodedJWT jwt) {
        return isTipoTokenIgual(jwt, TipoTokenJwt.USUARIO_PDV);
    }

    /**
     * Verifica se e um token de comanda digital
     *
     * @param jwt O token
     * @return true se e um token de comanda digital
     */
    public boolean isTokenComandaDigital(DecodedJWT jwt) {
        return isTipoTokenIgual(jwt, TipoTokenJwt.COMANDA_DIGITAL);
    }

    /**
     * Verifica se é um token de usuario do Motorista (aplicativo motorista)
     *
     * @param jwt O token
     * @return true se e um token de usuario do aplicativo motorista
     */
    public boolean isTokenUsuarioMotorista(DecodedJWT jwt) {
        return isTipoTokenIgual(jwt, TipoTokenJwt.USUARIO_MOTORISTA);
    }

    /**
     * Verifica se é um token de usuario frotista do app do gestor.
     *
     * @param jwt O token
     * @return true se e um token de usuario frotista do app do gestor.
     */
    public boolean isTokenDispositivoFrotista(DecodedJWT jwt) {
        return isTipoTokenIgual(jwt, TipoTokenJwt.DISPOSITIVO_FROTISTA);
    }

    /**
     * Compara o tipo de um token JWT com outro específico.
     *
     * @param jwt Token utilizado na comparação
     * @param tipoTokenJwt Tipo de token a ser comparadao.
     * @return True, caso o tipo seja o mesmo.
     */
    private boolean isTipoTokenIgual(DecodedJWT jwt, TipoTokenJwt tipoTokenJwt) {
        return jwt.getClaim(CAMPO_TOKEN_TIPO).asString().equals(tipoTokenJwt.name());
    }

    /**
     * Obtem o campo de identificacao de usuario presente no token
     *
     * @param jwt  O  token
     * @return O valor do campo de identificacao do usuario
     */
    public Long getIdentificadorUsuario(DecodedJWT jwt) {
        return jwt.getClaim(CAMPO_USUARIO_ID).asLong();
    }

    /**
     * Obtem o campo de nome de usuario presente no token
     *
     * @param jwt  O  token
     * @return O valor do campo de nome do usuario
     */
    public String getNomeUsuario(DecodedJWT jwt) {
        return jwt.getClaim(UtilitarioJwt.CAMPO_USUARIO_NOME).asString();
    }

    /**
     * Usando random nao seguro para ganho de performance,
     * pois este metodo sera chamado a cada requisicao realizada
     * por um sistema externo. Alem disso, nao eh necessario
     * utilizar random seguro, pois o objetivo eh apenas diminuir
     * a chance de colisao.
     *
     * @return Um long negativo aleatorio
     */
    private long gerarIdAleatorio() {
        long id = new Random().nextLong();
        return id > 0 ? (-id) : id;
    }

    /**
     * Obtem o tipo do token informado
     *
     * @param token O token
     * @return O tipo do token
     */
    public TipoTokenJwt getTipoToken(DecodedJWT token) {
        return TipoTokenJwt.valueOf(token.getClaim(CAMPO_TOKEN_TIPO).asString());
    }

    /**
     * Obtem o tipo do perfil do usuario contido no token
     *
     * @param token O token
     * @return O tipo do perfil do usuario para o qual o token foi emitido
     */
    public TipoPerfilUsuario getTipoPerfilUsuario(DecodedJWT token) {
        return TipoPerfilUsuario.valueOf(token.getClaim(CAMPO_USUARIO_TIPO).asString());
    }

    /**
     * Obtem o id da frota do usuario, caso essa informacao exista no token
     *
     * @param token O token
     * @return O id da frota do usuario ou null caso o token nao contenha essa informacao
     */
    public Long getFrota(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_FROTA).asLong();
    }

    /**
     * Obtem o id da rede do usuario, caso essa informacao exista no token
     *
     * @param token O token
     * @return O id da rede do usuario ou null caso o token nao contenha essa informacao
     */
    public Long getRede(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_REDE).asLong();
    }

    /**
     * Obtem o tipo do token informado
     *
     * @param token O token
     * @return O tipo do token
     */
    public Date getValidade(DecodedJWT token) {
        return token.getExpiresAt();
    }

    /**
     * Obtem a lista de pontos de venda do usuario armazenados no token
     *
     * @param token O token
     * @return Os pontos de venda do usuario armazenados no token
     */
    public Long[] getIdsPontosVenda(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_REDE).asArray(Long.class);
    }
    
    /**
     * Obtem o ponto de venda do usuario armazenado no token
     *
     * @param token O token
     * @return Identificador do ponto de venda.
     */
    public Long getIdPontosVenda(DecodedJWT token) {
    	Long[] idsPontoDeVenda = token.getClaim(CAMPO_USUARIO_PONTOS_VENDA).asArray(Long.class);
        return idsPontoDeVenda != null && idsPontoDeVenda.length > 0 ? idsPontoDeVenda[0] : null;
    }

    /**
     * Obtem a lista de codigos corporativos dos pontos de venda do usuario armazenados no token
     *
     * @param token O token
     * @return A lista de codigos corporativos dos pontos de venda
     */
    public Long[] getCodigosCorporativosPontosVenda(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_PONTOS_VENDA_CORP).asArray(Long.class);
    }

    /**
     * Obtem o id do motorista armazenado no token, caso exista
     *
     * @param token O token
     * @return O id do motorista
     */
    public Long getIdMotorista(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_MOTORISTA).asLong();
    }

    /**
     * Obtem o nome do motorista armazenado no token, caso exista
     *
     * @param token O token
     * @return O nome do motorista
     */
    public String getNomeMotorista(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_NOME).asString();
    }

    /**
     * Obtem o id do dispositivo de motorista armazenado no token, caso exista
     *
     * @param token O token
     * @return O id do dispositivo do motorista
     */
    public Long getIdDispositivoMotorista(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_ID).asLong();
    }

    /**
     * Obtem o id do dispositivo de motorista armazenado no token, caso exista
     *
     * @param tokenAutenticacao O token criptografado
     * @return O id do dispositivo do motorista
     */
    public Long getIdDispositivoMotorista(String tokenAutenticacao) {
        DecodedJWT jwt = JWT.decode(tokenAutenticacao);
        return getIdDispositivoMotorista(jwt);
    }

    /**
     * Obtem o id da comanda digital armazenado no token, caso exista
     *
     * @param token O token
     * @return O id da comanda digital
     */
    public Long getIdComandaDigital(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_ID).asLong();
    }

    /**
     * Obtem o contador de renovacoes do token, caso presente
     *
     * @param token O token
     * @return O contador de renovacoes
     */
    public Long getContadorRenovacoes(DecodedJWT token) {
        return token.getClaim(CAMPO_TOKEN_CONTADOR_RENOVACOES).asLong();
    }

    /**
     * Obtem o fingerprint do hardware do cliente, caso presenteno token
     *
     * @param token O token
     * @return O fingerprint
     */
    public String getFingerprint(DecodedJWT token) {
        return token.getClaim(CAMPO_USUARIO_FINGERPRINT).asString();
    }

    /**
     * Obtem a versao do token
     *
     * @param token O token
     * @return A versao do token
     */
    public String getVersao(DecodedJWT token) {
        Claim claim = token.getClaim(CAMPO_TOKEN_VERSAO);
        return claim.asString();
    }

    /**
     * Obtém o código de validação do token jwt.
     *
     * @param token Token decodificado.
     * @return Código de validação do token jwt.
     */
    public Long getCodigoValidacaoTokenJwt(DecodedJWT token) {
        Claim claim = token.getClaim(CAMPO_TOKEN_CODIGO_VALIDACAO);
        return claim.asLong();
    }

    /**
     * Verifica se o token esta prestes a expirar
     *
     * @param token O token
     * @return O tipo do token
     */
    public boolean expiracaoProximaCustomizado(DecodedJWT token) {
        return expiracaoProximaCustomizado(token, CAMPO_TOKEN_DATA_GERACAO);
    }

    /**
     * Verifica se o token esta prestes a expirar
     *
     * @param token O token
     * @param chave nome da chave de expiração no payload
     * @return O tipo do token
     */
    public boolean expiracaoProximaCustomizado(DecodedJWT token, String chave) {
        Claim claim = token.getClaim(chave);
        if (claim != null && claim.asLong() != null) {
            double geradoEm = claim.asDate().getTime();
            double expiraEm = token.getExpiresAt().getTime();
            double agora = new Date().getTime();
            return agora < expiraEm && ((agora - geradoEm)/(expiraEm - geradoEm) >= LIMIAR_EXPIRACAO_TOKEN);
        }
        return false;
    }

    /**
     * Verifica se o token esta prestes a expirar
     *
     * @param token o token
     * @return se está ou não expirado
     */
    public boolean expiracaoProxima(DecodedJWT token){
        double geradoEm = token.getIssuedAt().getTime();
        double expiraEm = token.getExpiresAt().getTime();
        double agora = new Date().getTime();
        return agora < expiraEm && ((agora - geradoEm) / (expiraEm - geradoEm) >= LIMIAR_EXPIRACAO_TOKEN);
    }

    /**
     * Verifica se o token está expirado.
     *
     * @param token O token
     * @return se está ou não expirado
     */
    public boolean isTokenExpirado(DecodedJWT token){
        return new Date().getTime() > token.getExpiresAt().getTime();
    }

    /**
     * Calcula o numero maximo de renovacoes para um token de PDV a partir da duracao
     * de um token e da duracao do turno de trabalho de um atendente.
     *
     * @return O numero maximo de renovacoes
     */
    public int calcularNumeroMaximoRenovacoesTokenPDV() {
        double duracaoTokenHoras = TipoTokenJwt.USUARIO_PDV.getDuracaoHoras();
        return (int) Math.ceil((DURACAO_TURNO_TRABALHO_HORAS / duracaoTokenHoras) * (1 / LIMIAR_EXPIRACAO_TOKEN)) + 1;
    }

    public void setSenhaAssinatura(String senhaAssinatura) {
        this.senhaAssinatura = senhaAssinatura;
    }

    /**
     * Montar resposta do token jwt
     * @param tokenJWT token jwt
     * @return resposta token
     */
    public byte[] montarRespostaTokenJWT(String tokenJWT) {
        String str  = "{\"tokenJWT\":\"" + tokenJWT + "\"}";
        return str.getBytes(StandardCharsets.UTF_8);
    }
    
    /**
     * Cria um token para o credenciamento de postos.
     *
     * @param cpf Numero do CPF do responsável pelo posto.
     * @param cnpj Numero do CNPJ do posto.
     * @return O token gerado.
     */
    public String criarTokenPreCredenciamentoPostos(Long cpf, Long cnpj) {
        return criarTokenJWT (
            TipoTokenJwt.PRE_CREDENCIAMENTO_POSTO,
            null,
            TipoTokenJwt.PRE_CREDENCIAMENTO_POSTO.getTipoPerfil(),
            cpf,
            "Site Pró-frotas",
            null,
            null,
            TipoTokenJwt.PRE_CREDENCIAMENTO_POSTO.getPermissoes(),
            null,
            null,
            null,
            new Long[]{cnpj},
            null);
    }
    
    /**
     * Cria um token para o credenciamento de postos.
     *
     * @param usuario O usuário.
     * @param pontoDeVenda O ponto de venda.
     * @return O token gerado.
     */
    public String criarTokenCredenciamentoPostos(Usuario usuario, PontoDeVenda pontoDeVenda) {
        return criarTokenJWT (
            TipoTokenJwt.CREDENCIAMENTO_POSTO,
            null,
            TipoTokenJwt.CREDENCIAMENTO_POSTO.getTipoPerfil(),
            usuario.getId(),
            usuario.getNome(),
            null,
            null,
            TipoTokenJwt.CREDENCIAMENTO_POSTO.getPermissoes(),
            null,
            null,
            null,
            new Long[]{pontoDeVenda.getId()},
            null);
    }
    
    /**
     * Cria um token para o pré credenciamento de postos.
     *
     * @param cnpj O CNPJ da possível frota.
     * @param cpf O CPF do possível usuário.
     * @return O token gerado.
     */
    public String criarTokenPreCredenciamentoFrota(Long cnpj, Long cpf) {
        return criarTokenJWT (
            TipoTokenJwt.PRE_CREDENCIAMENTO_FROTA,
            null,
            TipoTokenJwt.PRE_CREDENCIAMENTO_FROTA.getTipoPerfil(),
            cpf,
            "Site Pró-frotas",
            null,
            null,
            TipoTokenJwt.PRE_CREDENCIAMENTO_FROTA.getPermissoes(),
            cnpj,
            null,
            null,
            null,
            null);
    }
    
    /**
     * Cria um token para o pré credenciamento de postos.
     *
     * @param cnpj O CNPJ da possível frota.
     * @param cpf O CPF do possível usuário.
     * @return O token gerado.
     */
    public String criarTokenCredenciamentoFrota(Long cnpj, Long cpf) {
        return criarTokenJWT (
            TipoTokenJwt.CREDENCIAMENTO_FROTA,
            null,
            TipoTokenJwt.CREDENCIAMENTO_FROTA.getTipoPerfil(),
            cpf,
            "Site Pró-frotas",
            null,
            null,
            TipoTokenJwt.CREDENCIAMENTO_FROTA.getPermissoes(),
            cnpj,
            null,
            null,
            null,
            null);
    }
}
