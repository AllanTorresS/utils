package ipp.aci.boleia.util.boot;

import ipp.aci.boleia.util.i18n.Mensagens;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração das conexões com o Redis
 */
@Configuration
public class ConfiguracoesRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracoesRedis.class);

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPorta;

    /**
     * Cria configuração para o redis na aws
     *
     * @param mensagens Objeto de mensagens do projeto
     * @return Configuração do redis
     */
    @Bean
    public Config config(Mensagens mensagens) {
        Config config = new Config();
        String url = getUrlRedis();
        ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
        replicatedServersConfig.addNodeAddress(url);
        LOGGER.info(mensagens.obterMensagem("redis.host ", url));
        return config;
    }

    /**
     * Faz a configuração do cliente do Redisson
     *
     * @param config Objeto de configuração do redis
     * @return Cliente do redisson criado
     */
    @Bean
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    /**
     * Cria o endpoint com o protocolo do redis
     *
     * @return
     */
    private String getUrlRedis() {
        return String.format("redis://%s:%s", redisHost, redisPorta);
    }

}
