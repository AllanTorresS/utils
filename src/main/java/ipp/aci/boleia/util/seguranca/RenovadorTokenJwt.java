package ipp.aci.boleia.util.seguranca;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ipp.aci.boleia.dados.*;
import ipp.aci.boleia.dominio.*;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.excecao.ExcecaoTokenJwtExpirado;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Responsavel por renovar tokens JWT
 */
@Component
public class RenovadorTokenJwt {

    private static final Logger LOGGER = LoggerFactory.getLogger(FiltroRequisicoes.class);
    public static final String NOME_HEADER_NOVO_TOKEN = "Renovacao-Automatica-JWT";

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Autowired
    private IComandaDigitalDados comandaDados;

    @Autowired
    private IServicosDeApiToken servicosDeApiToken;

    @Autowired
    private IUsuarioDados usuarioDados;

    @Autowired
    private VerificadorVersaoTokenJwt verificadorVersaoTokenJwt;

    @Autowired
    private IDispositivoMotoristaDados dispositivoMotoristaDados;

    @Autowired
    private IModuloInternoDados moduloInternoDados;

    private LoadingCache<ChaveCache, Optional<String>> cacheRenovacaoTokens;

    /**
     * Cria um cache para armazenamento temporario de tokens JWT recem gerados.
     * Util para evitar que requisicoes que insistam em usar tokens antigos nao forcem
     * a computacao de novos tokens desnecessariamente
     */
    @PostConstruct
    public void init() {

        cacheRenovacaoTokens = CacheBuilder.newBuilder()

            // Ate 10 mil entradas no cache.
            // Cada entrada contem uma chave de aproximadamente 400 caracteres e um
            // valor de tamanho similar.
            // Memoria maxima consumida esperada: ~10 MB
            .maximumSize(10000)

            // Numero estimado de threads concorrendo na leitura e escrita no cache.
            // Variacoes de ate uma ordem de grandeza nao causam efeito colateral notavel,
            // segundo a documentacao (javadocs) da API.
            .concurrencyLevel(50)

            // Quando um novo token eh gerado, ele reside por 10 minutos no cache
            // para evitar que novos tokens sejam gerados a cada requisicao, quando
            // o cliente insiste em continuar utilizando o token anterior.
            .expireAfterWrite(10, TimeUnit.MINUTES)

            // Instancia o cache com as configuracoes mencionadas acima.
            // A chave do cache eh um objeto que contem um token jwt codificado como string.
            // O valor correspondente eh um novo token, gerado para substituir o anterior.
            .build(new CacheLoader<ChaveCache, Optional<String>>() {
                @Override
                public Optional<String> load(ChaveCache chaveCache) {
                    return Optional.ofNullable(renovarToken(chaveCache.strToken, chaveCache.fingerprint));
                }
            });
    }

    /**
     * Renova um token JWT caso esteja proximo de sua expiracao ou
     * caso sua versão seja antiga mas ainda não esteja expirado.
     *
     * @param strToken O token atual, representado como uma string
     * @param fingerprint O fingerprint do hardware do cliente, caso recebido
     * @param response A resposta para envio do novo token
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void enviarNovoTokenCasoNecessario(String strToken, String fingerprint, HttpServletResponse response) {
        try {
            Optional<String> novoToken = cacheRenovacaoTokens.get(new ChaveCache(strToken, fingerprint));
            if (novoToken.isPresent() && StringUtils.isEmpty(response.getHeader(NOME_HEADER_NOVO_TOKEN))) {
                response.addHeader(NOME_HEADER_NOVO_TOKEN, novoToken.get());
                response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, NOME_HEADER_NOVO_TOKEN);
            }
        } catch (ExecutionException e) {
            LOGGER.error("Erro ao renovar token JWT", e);
        }
    }

    /**
     * Dado um token previamente validado, cria um novo token para substitui-lo
     * @param strToken O token atual codificado como string
     * @param fingerprint O fingerprint do hardware do cliente, caso recebido
     * @return Um novo token
     */
    private String renovarToken(String strToken, String fingerprint) {
        try {
            DecodedJWT token = utilitarioJwt.validarTokenAutenticacao(strToken, fingerprint);
            TipoTokenJwt tipoToken = utilitarioJwt.getTipoToken(token);
            String versaoToken = utilitarioJwt.getVersao(token);

            if (token != null && (utilitarioJwt.expiracaoProximaCustomizado(token) || verificadorVersaoTokenJwt.isTokenVersaoAntigaValido(tipoToken, versaoToken))) {

                switch (tipoToken) {
                    case COMANDA_DIGITAL:       return renovarTokenComandaDigital(token);
                    case DISPOSITIVO_MOTORISTA: return renovarTokenDispositivoMotorista(token);
                    case SALES_FORCE:           return utilitarioJwt.criarTokenSalesForce();
                    case USUARIO_PDV:           return renovarTokenPDV(token, fingerprint);
                    case API_FROTISTA:          return renovarTokenApiFrotista(token, strToken);
                    case MODULO_INTERNO:        return renovarTokenModuloInterno(token);
                }
            }
        } catch (ExcecaoTokenJwtExpirado e) {
            // nada a fazer, o cache sera povoado com null
            LOGGER.trace(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Renova um token de comanda digital a partir do token atual previamente validado
     * @param token O token atual previamente validado
     */
    private String renovarTokenComandaDigital(DecodedJWT token) {
        String novoToken = null;
        Long id = utilitarioJwt.getIdComandaDigital(token);
        TipoPerfilUsuario tipoPerfil = utilitarioJwt.getTipoPerfilUsuario(token);
        ComandaDigital comandaDigital = comandaDados.obterPorId(id);
        if (comandaDigital != null) {
            if (TipoPerfilUsuario.REVENDA.equals(tipoPerfil)) {
                novoToken = utilitarioJwt.criarTokenComandaDigitalRevenda(comandaDigital);
            } else {
                novoToken = utilitarioJwt.criarTokenComandaDigitalFrota(comandaDigital);
            }
        }
        return novoToken;
    }

    /**
     * Renova um token de dispositivo de motorista a partir do token atual previamente validado
     * @param token O token atual previamente validado
     */
    private String renovarTokenDispositivoMotorista(DecodedJWT token) {
        String novoToken = null;
        Long id = utilitarioJwt.getIdDispositivoMotorista(token);
        DispositivoMotorista dispositivoMotorista = dispositivoMotoristaDados.obterPorId(id);
        if (dispositivoMotorista != null) {
            novoToken = utilitarioJwt.criarTokenDispositivoMotorista(dispositivoMotorista);
        }
        return novoToken;
    }

    /**
     * Renova um token do PDV a partir do token atual previamente validado
     * @param token O token atual previamente validado
     */
    private String renovarTokenPDV(DecodedJWT token, String fingerprint) {
        String novoToken = null;
        Long id = utilitarioJwt.getIdentificadorUsuario(token);
        Usuario usuario = usuarioDados.obterPorId(id);
        if (usuario != null) {
            Long contadorRenovacoes = utilitarioJwt.getContadorRenovacoes(token);
            contadorRenovacoes = contadorRenovacoes != null ? contadorRenovacoes : 0L;
            int maximoRenovacoes = utilitarioJwt.calcularNumeroMaximoRenovacoesTokenPDV();
            if (contadorRenovacoes < maximoRenovacoes) {
                novoToken = utilitarioJwt.criarTokenUsuarioPdv(usuario, contadorRenovacoes + 1, fingerprint);
            }
        }
        return novoToken;
    }

    /**
     * Renova um token para API frotista a partir do token atual previamente validado.
     * 
     * @param token Token decodificado utilizado na chamada da API
     * @param strToken Token utilizado na chamada da API
     * @return Novo token
     */
    private String renovarTokenApiFrotista(DecodedJWT token, String strToken) {
        return servicosDeApiToken.renovarTokenFrota(token, strToken);
    }

    /**
     * Renova um token de módulo interno a partir do token atual previamente validado
     * @param token O token atual previamente validado
     */
    private String renovarTokenModuloInterno(DecodedJWT token) {
        String novoToken = null;
        Long id = utilitarioJwt.getIdentificadorUsuario(token);
        TipoPerfilUsuario tipoPerfil = utilitarioJwt.getTipoPerfilUsuario(token);
        ModuloInterno moduloInterno = moduloInternoDados.obterPorId(id);
        if (moduloInterno != null) {
            novoToken = utilitarioJwt.criarTokenModuloInterno(moduloInterno);
        }
        return novoToken;
    }

    /**
     * Chave do cache de renovacao de tokens
     */
    private static final class ChaveCache {

        private String strToken;
        private String fingerprint;

        /**
         * Construtor privado
         * @param strToken O token
         * @param fingerprint O fingerprint do usuario
         */
        private ChaveCache(String strToken, String fingerprint) {
            this.strToken = strToken;
            this.fingerprint = fingerprint;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            return ((ChaveCache) obj).strToken.equals(this.strToken);
        }

        @Override
        public int hashCode() {
            return strToken.hashCode();
        }
    }
}
