package ipp.aci.boleia.util.seguranca;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ipp.aci.boleia.util.anotacoes.IgnoreSanitizing;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Filtro para requisicoes
 */
@Component
public class FiltroRequisicoes {

    private static final Logger LOGGER = LoggerFactory.getLogger(FiltroRequisicoes.class);

    private LoadingCache<String, Long> mapaRequisicoes;

    private static final Lock LOCK = new ReentrantLock();
    private static final PolicyFactory SANITIZADOR = new HtmlPolicyBuilder().toFactory();
    private final Map<String, Map<String, Method>> getters = new HashMap<>();
    private final Map<String, Map<String, Method>> setters = new HashMap<>();

    @Value("${request.rate.limit}")
    private long intervaloMinimoEntreRequisicoesMilissegundos;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    /**
     * Cria um cache LRU para memorizar a data do ultimo disparo de uma determinada requisicao
     */
    @PostConstruct
    public void init() {

        mapaRequisicoes = CacheBuilder.newBuilder()

            // Ate 200 mil entradas no cache.
            // Cada entrada contem uma chave (string hash) de aprox. 150 caracteres e um valor Long (12 + 8 bytes)
            // Memoria maxima consumida esperada: ~50 MB
            .maximumSize(200000)

            // Numero estimado de threads concorrendo na leitura e escrita no cache.
            // Variacoes de ate uma ordem de grandeza nao causam efeito colateral notavel,
            // segundo a documentacao (javadocs) da API.
            .concurrencyLevel(50)

            // Apos o periodo de restricao, a chave deve ser removida, para que outra requisicao possa ser atentida
            // e tambem para que o consumo de memoria se mantenha estavel
            .expireAfterWrite(intervaloMinimoEntreRequisicoesMilissegundos, TimeUnit.MILLISECONDS)

            // Instancia o cache com as configuracoes mencionadas acima.
            // A chave do cache eh uma concatenacao da URL requisitada e do hash do token do usuario
            // O valor correspondente a chave eh o timestamp da ultima requisicao daquele usuario a aquela URL.
            .build(new CacheLoader<String, Long>() {
                @Override
                public Long load(String chave) {
                   return 0L;
                }
            });
    }

    /**
     * Exige um limite de tempo minimo entre requisicoes identicas, para evitar ataques de forca bruta
     *
     * @param request A requisicao recebida
     * @param intervalo O intervalo em milisegundos entre as requisições
     * @param identificadorRequisicao O dado presente na requisicao que a diferencia de outras deitas à mesma URL
     */
    public void exigirIntervaloMinimoTempoEntreRequisicoes(HttpServletRequest request, long intervalo, String... identificadorRequisicao) {
        try {
            String chave = montarChaveRequisicao(request, identificadorRequisicao);
            if (chave != null) {
                Long momentoUltimaRequisicao = mapaRequisicoes.get(chave);
                Long agora = new Date().getTime();
                mapaRequisicoes.put(chave, agora);
                long tempoDecorrido = agora - momentoUltimaRequisicao;
                if (tempoDecorrido <= intervalo) {
                    throw new ExcecaoBoleiaRuntime(Erro.LIMITE_REQUISICOES_POR_SEGUNDO_EXCEDIDO);
                }
            }
        } catch (ExecutionException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    /**
     * Exige um limite de tempo minimo entre requisicoes identicas, para evitar ataques de forca bruta
     *
     * @param request A requisicao recebida
     * @param identificadorRequisicao O dado presente na requisicao que a diferencia de outras deitas à mesma URL
     */
    public void exigirIntervaloMinimoTempoEntreRequisicoes(HttpServletRequest request, String... identificadorRequisicao) {
        exigirIntervaloMinimoTempoEntreRequisicoes(request, intervaloMinimoEntreRequisicoesMilissegundos, identificadorRequisicao);
    }

    /**
     * Sanitiza, recursivamente e em profundidade, as propriedades String de um objeto e de suas classes ancestrais,
     * para evitar ataques de injecao de codigo.
     *
     * @param objeto O objeto alvo
     */
    public void sanitizarDadosEntrada(Object objeto) {
        if(objeto != null) {
            Map<String, Method> mapaGetters = obterMapaMetodos(objeto, getters, true);
            Map<String, Method> mapaSetters = obterMapaMetodos(objeto, setters, false);
            for(String campo : mapaGetters.keySet()) {
                Method getter = mapaGetters.get(campo);
                try {
                    Method setter = mapaSetters.get(campo);
                    if (setter != null) {
                        Object original = getter.invoke(objeto);
                        if(original != null) {
                            if (getter.getReturnType().equals(String.class)) {
                                setter.invoke(objeto, SANITIZADOR.sanitize((String) original));
                            } else {
                                sanitizarDadosEntrada(original);
                                setter.invoke(objeto, original);
                            }
                        }
                    }
                } catch (ReflectiveOperationException | IllegalArgumentException e) {
                    LOGGER.warn("Erro ao sanitizar a propriedade " + campo + " da classe " + objeto.getClass().getName(), e);
                }
            }
        }
    }

    /**
     * Monta uma chave para identificacao da URL sendo invocada pelo usuario corrente
     *
     * @param request A requisicao recebida
     * @param identificadorRequisicao O dado presente na requisicao que a diferencia de outras deitas à mesma URL
     * @return A chave para indexacao no map, ou null caso a requisicao nao possua um token JWT ou nao exista sessao ativa
     */
    private String montarChaveRequisicao(HttpServletRequest request, String... identificadorRequisicao) {
        String chave = null;
        String identificadorUsuario = identificadorRequisicao != null && identificadorRequisicao.length > 0 ? Arrays.toString(identificadorRequisicao) : null;
        if(identificadorUsuario == null) {
            identificadorUsuario= utilitarioJwt.extrairToken(request);
        }
        if(identificadorUsuario == null) {
            HttpSession sessao = request.getSession(false);
            if(sessao != null) {
                identificadorUsuario = sessao.getId();
            }
        }
        if(identificadorUsuario != null) {
            StringBuilder str = new StringBuilder();
            str.append(request.getRequestURL().toString());
            str.append("|");
            str.append(identificadorUsuario);
            chave = str.toString();
        }
        return chave;
    }

    /**
     * Obtem o mapa de getters ou setter de propriedades String da classe informada, para sanitizacao de inputs
     *
     * @param objeto O objeto alvo
     * @param mapaClasse O mapa dos metodos da classe
     * @param isGetter Se true obtem um mapa de getters, caso constrario, um mapa de setters
     * @return Um mapa de getters ou setter de propriedades String da classe informada, para sanitizacao de inputs
     */
    private Map<String, Method> obterMapaMetodos(Object objeto, Map<String, Map<String, Method>> mapaClasse, boolean isGetter) {
        String chaveClasse = objeto.getClass().getName();
        Map<String, Method> mapaMetodos = mapaClasse.get(chaveClasse);
        if(mapaMetodos == null) {
            LOCK.lock();
            try {
                mapaMetodos = mapaClasse.get(chaveClasse);
                if(mapaMetodos == null) {
                    mapaMetodos = obterMetodosClasse(objeto, isGetter);
                    mapaClasse.put(chaveClasse, mapaMetodos);
               }
            } finally {
                LOCK.unlock();
            }
        }

        return mapaMetodos;
    }

    /**
     * Obtem os getters ou setters da classe
     *
     * @param objeto O objeto alvo
     * @param isGetter Se true obtem os getters, caso contrario os setters
     * @return Os getters ou setters da classe
     */
    private Map<String, Method> obterMetodosClasse(Object objeto, boolean isGetter) {
        Map<String, Method> mapa = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objeto.getClass(), Object.class);
            PropertyDescriptor[] campos = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor campo : campos) {
                if(campo.getPropertyType().equals(String.class)
                    || (campo.getPropertyType().getPackage() != null
                        && campo.getPropertyType().getPackage().getName() != null
                        && campo.getPropertyType().getPackage().getName().contains("ipp.aci.boleia"))) {
                    if(!ignorarCampo(objeto, campo.getName())) {
                        mapa.put(campo.getName(), isGetter ? campo.getReadMethod() : campo.getWriteMethod());
                    }
                }
            }
        } catch (IntrospectionException e) {
            LOGGER.warn("Erro ao tentar sanitizar um objeto do tipo " + objeto.getClass().getName(), e);
        }
        return mapa;
    }

    /**
     * Setter para configuracao durante os testes de unidade
     *
     * @param intervaloMinimoEntreRequisicoesMilissegundos O intervalo minimo aceito entre requisicoes identicas
     */
    public void setIntervaloMinimoEntreRequisicoesMilissegundos(long intervaloMinimoEntreRequisicoesMilissegundos) {
        this.intervaloMinimoEntreRequisicoesMilissegundos = intervaloMinimoEntreRequisicoesMilissegundos;
    }

    /**
     * Verifica se um campo deve ser ignorado na sanitizacao
     * @param objeto O objeto alvo
     * @param nome O campo a verificar
     * @return True caso o campo deva ser ignorado na sanitizacao
     */
    private boolean ignorarCampo(Object objeto, String nome) {
        boolean ignorar = false;
        try {
            Field field = objeto.getClass().getDeclaredField(nome);
            ignorar = field.getAnnotation(IgnoreSanitizing.class) != null;
        } catch (NoSuchFieldException e) {
            LOGGER.debug(e.getMessage(), e);
            // nada a fazer
        }
        return ignorar;
    }
}
