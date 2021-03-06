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
     * Encapsula um reposit??rio de sess??es Redis para tratamento de erros de desserializa????o. Esses erros podem
     * aconteder j?? que o mecanismo de serializa????o default ?? o da pr??pria JVM. Com isso, quando um novo deployment
     * altera a estrutura est??tica de uma classe, suas inst??ncias salvas no Redis (dentro da sess??o de um usu??rio)
     * n??o ser??o mais compat??veis. Nessa situa????o, eliminamos as sess??es de usu??rio que sofreram com o problema,
     * for??ando novo login.
     *
     * @param <S> O tipo da sess??o
     */
    public static class RepositorioSessoesRedis<S extends ExpiringSession> implements FindByIndexNameSessionRepository<S> {

        private static final Logger LOG = LoggerFactory.getLogger(RepositorioSessoesRedis.class);
        public static final String BOUNDED_HASH_KEY_PREFIX = "spring:session:sessions:";

        private final SessionRepository<S> repository;
        private final RedisOperations<Object, Object> redisOperations;

        /**
         * Constr??i o encapsulamento
         * @param repository O reposit??rio de sess??es
         * @param redisOperations O toolkit para intera????o com o Redis
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