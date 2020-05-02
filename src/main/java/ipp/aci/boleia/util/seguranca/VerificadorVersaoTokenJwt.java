package ipp.aci.boleia.util.seguranca;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ipp.aci.boleia.dados.IVersaoTokenJwtDados;
import ipp.aci.boleia.dominio.VersaoTokenJwt;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Trata a versao dos tokens jwt recebidos e gerados
 */
@Component
public class VerificadorVersaoTokenJwt {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificadorVersaoTokenJwt.class);

    private static final Long FINGERPRINT_OBRIGATORIO_DESDE_VERSAO = 2L;

    @Autowired
    private IVersaoTokenJwtDados versaoTokenJwtDados;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Value("${jwt.accepted.token.type}")
    private String tipoAceito;

    private LoadingCache<TipoTokenJwt, Optional<VersaoTokenJwt>> cacheVersoesTokens;

    /**
     * Cria um cache para evitar acessos desnecessarios ao banco de dados
     * no momento de obtencao da versao corrente dos tokens
     */
    @PostConstruct
    public void init() {

        cacheVersoesTokens = CacheBuilder.newBuilder()

            // Numero estimado de threads concorrendo na leitura e escrita no cache.
            // Variacoes de ate uma ordem de grandeza nao causam efeito colateral notavel,
            // segundo a documentacao (javadocs) da API.
            .concurrencyLevel(50)

            // Quando os dados de versao de um token sao obtidos do banco de dados,
            // eles são armazenados no cache por 1 minuto, para evitar que se
            // realize uma consulta ao banco de dados a cada requisicao recebida
            // pelas APIs do sistema.
            .expireAfterWrite(1, TimeUnit.MINUTES)

            // Instancia o cache com as configuracoes mencionadas acima.
            // A chave do cache eh um objeto que contem um tipo de token jwt e o valor
            // contem o registro de versao do token presente no banco de dados
            .build(new CacheLoader<TipoTokenJwt, Optional<VersaoTokenJwt>>() {
                @Override
                public Optional<VersaoTokenJwt> load(TipoTokenJwt tipoToken) {
                    return Optional.ofNullable(obterDadosVersaoCorrenteToken(tipoToken));
                }
            });
    }

    /**
     * Verifica se a versao do token recebido eh valida
     *
     * @param tipoTokenJwt O tipo do token a ser verificado
     * @param versao A versao do token recebido
     * @return True caso seja valida
     */
    public boolean isVersaoValida(TipoTokenJwt tipoTokenJwt, String versao) {
        boolean valido = true;
        try {
            Optional<VersaoTokenJwt> optional = cacheVersoesTokens.get(tipoTokenJwt);
            if(optional.isPresent()) {
                String[] ambienteVersao = obterAmbienteEVersao(versao);
                String ambiente = ambienteVersao[0];
                int sequencialRecebido = Integer.parseInt(ambienteVersao[1], 10);
                VersaoTokenJwt dadosVersaoCorrente = optional.get();
                long versaoEsperada = dadosVersaoCorrente.getVersaoCorrente();
                if(!ambiente.equals(tipoAceito)) {
                    valido = false;
                } else if(sequencialRecebido < versaoEsperada) {
                    if(getHoje().after(dadosVersaoCorrente.getVersaoAnteriorValidaAte())) {
                        valido = false;
                    }
                }
            }
        } catch (ExecutionException e) {
            LOGGER.error("Erro ao povoar cache de versoes de tokens", e);
            valido = false;
        }
        return valido;
    }

    /**
     * Retorna true caso o token informado seja de uma versao antiga, mas
     * essa versao ainda esta dentor do periodo de aceitacao configurado
     *
     * @param tipoTokenJwt O tipo do token recebido
     * @param versao A versao do token recebido
     * @return True caso o token informado seja de uma versao antiga
     *          mas que ainda esta dentro do periodo limite de aceitacao
     */
    public boolean isTokenVersaoAntigaValido(TipoTokenJwt tipoTokenJwt, String versao) {
        boolean isTokenVersaoAntigaValido = false;
        try {
            Optional<VersaoTokenJwt> optional = cacheVersoesTokens.get(tipoTokenJwt);
            if(optional.isPresent()) {
                VersaoTokenJwt dadosVersao = optional.get();
                String[] ambienteVersao = obterAmbienteEVersao(versao);
                long sequencialRecebido = Integer.parseInt(ambienteVersao[1], 10);
                long versaoEsperada = dadosVersao.getVersaoCorrente();
                if(sequencialRecebido < versaoEsperada) {
                    return !getHoje().after(dadosVersao.getVersaoAnteriorValidaAte());
                }
            }
        } catch (ExecutionException e) {
            LOGGER.error("Erro ao povoar cache de versoes de tokens", e);
        }
        return isTokenVersaoAntigaValido;
    }

    /**
     * Obtem a versao corrente dos tokens do tipo informado
     * @param tipoTokenJwt O tipo do token
     * @return A versao do tipo do token
     */
    public String getVersaoCorrente(TipoTokenJwt tipoTokenJwt) {
        try {
            Optional<VersaoTokenJwt> optional = cacheVersoesTokens.get(tipoTokenJwt);
            if(optional.isPresent()) {
                VersaoTokenJwt dadosVersao = optional.get();
                return montarNumeroVersao(tipoAceito, dadosVersao.getVersaoCorrente());
            }
        } catch (ExecutionException e) {
            LOGGER.error("Erro ao montar a string de versao para criacao de um token", e);
        }
        return null;
    }

    /**
     * Obtem a versao corrente dos tokens do tipo informado
     * @param tipoTokenJwt O tipo do token
     * @return A versao do tipo do token
     */
    public VersaoTokenJwt getDadosVersaoToken(TipoTokenJwt tipoTokenJwt) {
        try {
            Optional<VersaoTokenJwt> optional = cacheVersoesTokens.get(tipoTokenJwt);
            if(optional.isPresent()) {
                return optional.get();
            }
        } catch (ExecutionException e) {
            LOGGER.error("Erro ao montar a string de versao para criacao de um token", e);
        }
        return null;
    }

    /**
     * Monta o numero da versao no formato "X-9999"
     * @param tipoAmbiente O indicador do tipo do ambiente do token
     * @param versao A versao
     * @return Uma string no formato "X-9999"
     */
    public String montarNumeroVersao(String tipoAmbiente, Long versao) {
        return tipoAmbiente + "-" + StringUtils.leftPad("" + versao, 4, '0');
    }

    /**
     * Verifica se o fingerprint eh obrigatorio para o tipo de token e versao informados
     * @param fingerprintRequisicao O fingerprint enviado
     * @param tipoToken O tipo do token
     * @param versaoToken A versao do token
     * @return True caso o fingerprint seja obrigatorio no token
     */
    public boolean isFingerprintObrigatorio(String fingerprintRequisicao, TipoTokenJwt tipoToken, String versaoToken) {
        boolean obrigatorio = false;
        if(StringUtils.isNotBlank(fingerprintRequisicao) && tipoToken.isFingerprintObrigatorio()) {
            String[] ambienteVersao = obterAmbienteEVersao(versaoToken);
            int sequencialRecebido = Integer.parseInt(ambienteVersao[1], 10);
            if(sequencialRecebido >= FINGERPRINT_OBRIGATORIO_DESDE_VERSAO) {
                obrigatorio = true;
            } else {
                obrigatorio = !isVersaoValida(tipoToken, versaoToken);
            }
        }
        return obrigatorio;
    }

    /**
     * Obtem, do banco de dados, as informacoes sobre a versao corrente do token JWT
     *
     * @param tipoTokenJwt O tipo de token desejado
     * @return As informacoes da versao corrente do token
     */
    private VersaoTokenJwt obterDadosVersaoCorrenteToken(TipoTokenJwt tipoTokenJwt) {
        return versaoTokenJwtDados.obterPorTipoToken(tipoTokenJwt);
    }

    /**
     * Obtem a data de hoje, sem informacoes de hora, minutos e segundos
     *
     * @return A data de hoje
     */
    private Date getHoje() {
        Date agora = utilitarioAmbiente.buscarDataAmbiente();
        Calendar c = Calendar.getInstance();
        c.setTime(agora);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    private String[] obterAmbienteEVersao(String versao) {
        return StringUtils.split(versao, '-');
    }

    public void setTipoAceito(String tipoAceito) {
        this.tipoAceito = tipoAceito;
    }


}
