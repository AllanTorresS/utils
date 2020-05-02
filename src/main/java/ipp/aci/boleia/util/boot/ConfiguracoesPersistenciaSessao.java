package ipp.aci.boleia.util.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.SessionRepositoryFilter;

import java.util.Map;


/**
 * Classe de configuracao de persistencia da sessao
 */
@Configuration
@EnableRedisHttpSession
@ConditionalOnProperty(name = "spring.session.store-type", havingValue = "redis")
public class ConfiguracoesPersistenciaSessao extends RedisHttpSessionConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Bean
    @Override
    public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> springSessionRepositoryFilter(SessionRepository<S> sessionRepository) {
        RepositorioSessoesRedis<S> repo = new RepositorioSessoesRedis<>(sessionRepository, redisTemplate);
        SessionRepositoryFilter<? extends ExpiringSession> filter = super.springSessionRepositoryFilter(repo);
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        beanFactory.registerSingleton(repo.getClass().getCanonicalName(), repo);
        return filter;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }

    /**
     * Encapsula um repositório de sessões Redis para tratamento de erros de desserialização. Esses erros podem
     * aconteder já que o mecanismo de serialização default é o da própria JVM. Com isso, quando um novo deployment
     * altera a estrutura estática de uma classe, suas instâncias salvas no Redis (dentro da sessão de um usuário)
     * não serão mais compatíveis. Nessa situação, eliminamos as sessões de usuário que sofreram com o problema,
     * forçando novo login.
     *
     * @param <S> O tipo da sessão
     */
    public static class RepositorioSessoesRedis<S extends ExpiringSession> implements FindByIndexNameSessionRepository<S> {

        private static final Logger LOG = LoggerFactory.getLogger(RepositorioSessoesRedis.class);
        public static final String BOUNDED_HASH_KEY_PREFIX = "spring:session:sessions:";

        private final SessionRepository<S> repository;
        private final RedisOperations<Object, Object> redisOperations;

        /**
         * Constrói o encapsulamento
         * @param repository O repositório de sessões
         * @param redisOperations O toolkit para interação com o Redis
         */
        public RepositorioSessoesRedis(SessionRepository<S> repository, RedisOperations<Object, Object> redisOperations) {
            this.repository = repository;
            this.redisOperations = redisOperations;
        }

        @Override
        public S getSession(String id) {
            try {
                return repository.getSession(id);
            } catch(SerializationException e) {
                LOG.warn("Apagando sessao nao desserializavel armazenada na chave {}", id);
                LOG.info(e.getMessage(), e);
                redisOperations.delete(BOUNDED_HASH_KEY_PREFIX + id);
                return null;
            }
        }

        @Override
        public void delete(String id) {
            try {
                repository.delete(id);
            } catch(SerializationException e) {
                LOG.warn("Apagando sessao nao desserializavel armazenada na chave {}", id);
                LOG.info(e.getMessage(), e);
                redisOperations.delete(BOUNDED_HASH_KEY_PREFIX + id);
            }
        }

        @Override
        public S createSession() {
            return repository.createSession();
        }

        @Override
        public void save(S session) {
            repository.save(session);
        }

        @Override
        public Map<String, S> findByIndexNameAndIndexValue(String indexName, String indexValue) {
            return ((FindByIndexNameSessionRepository<S>) repository).findByIndexNameAndIndexValue(indexName, indexValue);
        }
    }

}