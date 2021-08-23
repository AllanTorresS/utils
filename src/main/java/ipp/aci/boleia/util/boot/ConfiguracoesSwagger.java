package ipp.aci.boleia.util.boot;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import ipp.aci.boleia.util.excecao.MensagemErro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Classe responsável pela subidas configurações da geracao da documentação dos arquivos das apis
 * em Swagger.
 * 
 */
@Configuration
@EnableSwagger2
public class ConfiguracoesSwagger {

    private static final String PACOTE_API_FROTISTA = "ipp.aci.boleia.visao.frotista";
    private static final String PACOTE_API_EXTERNO = "ipp.aci.boleia.visao.externo";
    public static final String API_FROTISTA_GROUP_NAME = "api-frotista";
    public static final String API_EXTERNO_GROUP_NAME = "api-externo";
    public static final String API_AGENCIADOR_FRETE_GROUP_NAME = "api-agenciadorfrete";
    private static final String PACOTE_API_AGENCIADOR_FRETE = "ipp.aci.boleia.visao.agenciadorfrete";


    @Value(value="${docs.api.frotista.titulo}")
    private String apiFrotistaTitulo;

    @Value(value="${docs.api.frotista.descricao}")
    private String apiFrotistaDescricao;

    @Value(value="${docs.api.frotista.versao}")
    private String apiFrotistaVersao;

    @Value(value="${docs.api.externo.titulo}")
    private String apiExternoTitulo;

    @Value(value="${docs.api.externo.descricao}")
    private String apiExternoDescricao;

    @Value(value="${docs.api.externo.versao}")
    private String apiExternoVersao;

    @Value(value="${docs.api.agenciadorfrete.titulo}")
    private String apiAgenciadorDeFreteTitulo;

    @Value(value="${docs.api.agenciadorfrete.descricao}")
    private String apiAgenciadorDeFreteDescricao;

    @Value(value="${docs.api.agenciadorfrete.versao}")
    private String apiAgenciadorDeFreteVersao;


    @Autowired
    private TypeResolver typeResolver;

    /**
     * Configuracao Swagger para a api do frotista
     * @return plugin docket
     */
    @Bean 
    public Docket apiFrotista() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(API_FROTISTA_GROUP_NAME)
                .protocols(new HashSet<>(Arrays.asList("http", "https")))
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACOTE_API_FROTISTA))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getGlobalResponseMessages())
                .apiInfo(getApiFrotistaInfo())
                .additionalModels(getAditionalModel());
    }

    /**
     * Configuracao Swagger para a api do frotista
     * @return plugin docket
     */
    @Bean
    public Docket apiExterno() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(API_EXTERNO_GROUP_NAME)
                .protocols(new HashSet<>(Arrays.asList("http", "https")))
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACOTE_API_EXTERNO))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getGlobalResponseMessages())
                .apiInfo(getApiExternoInfo())
                .additionalModels(getAditionalModel())
                .directModelSubstitute(Date.class, String.class);
    }

    /**
     * Configuracao Swagger para a api do frotista
     * @return plugin docket
     */
    @Bean
    public Docket apiAgenciadorDeFrete() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(API_AGENCIADOR_FRETE_GROUP_NAME)
                .protocols(new HashSet<>(Arrays.asList("http", "https")))
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACOTE_API_AGENCIADOR_FRETE))
                .paths(PathSelectors.any())
                .build()
                .globalResponseMessage(RequestMethod.GET, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.POST, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getGlobalResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getGlobalResponseMessages())
                .apiInfo(getApiAgenciadorDeFreteInfo())
                .additionalModels(getAditionalModel());
    }

    /**
     * Obtem a as informacoes da api do frotista
     * @return informacoes da api
     */
    private ApiInfo getApiFrotistaInfo() {
        return new ApiInfoBuilder()
                .title(apiFrotistaTitulo)
                .description(apiFrotistaDescricao)
                .version(apiFrotistaVersao)
                .build();
    }

    /**
     * Obtem a as informacoes da api dos sistemas externos
     * @return informacoes da api
     */
    private ApiInfo getApiExternoInfo() {
        return new ApiInfoBuilder()
                .title(apiExternoTitulo)
                .description(apiExternoDescricao)
                .version(apiExternoVersao)
                .build();
    }

    /**
     * Obtem as mensagens globais de resposta
     * @return Lista de mensagens de resposta
     */
    private List<ResponseMessage> getGlobalResponseMessages() {
        return Arrays.asList(
            new ResponseMessageBuilder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .responseModel(new ModelRef(MensagemErro.class.getSimpleName()))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .responseModel(new ModelRef(MensagemErro.class.getSimpleName()))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase())
                .responseModel(new ModelRef(MensagemErro.class.getSimpleName()))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.NO_CONTENT.value())
                .message(HttpStatus.NO_CONTENT.getReasonPhrase())
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                .responseModel(new ModelRef(MensagemErro.class.getSimpleName()))
                .build(),
            new ResponseMessageBuilder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .responseModel(new ModelRef(String.class.getSimpleName()))
                .build()
        );
    }

    /**
     * Obtem a as informacoes da api do agenciador de frete
     * @return informacoes da api
     */
    private ApiInfo getApiAgenciadorDeFreteInfo() {
        return new ApiInfoBuilder()
                .title(apiAgenciadorDeFreteTitulo)
                .description(apiAgenciadorDeFreteDescricao)
                .version(apiAgenciadorDeFreteVersao)
                .build();
    }

    /**
     * Obtem o model adicional de mensagem
     * @return mensagem de erro vo resolvido
     */
    private ResolvedType getAditionalModel() {
        return typeResolver.resolve(MensagemErro.class);
    }

}