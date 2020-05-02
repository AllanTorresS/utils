package ipp.aci.boleia.util.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Intercepta todas as requisicoes HTTP do sistema, verificando se a sessao vinculada ainda esta valida.
 * Sao consideradas validas apenas as sessoes que tenham recebido requests desprovidos do parametro hsr=false
 * nos ultimos "sessao.getMaxInactiveInterval()" segundos.
 */
@Configuration
public class ConfiguracoesCacheRecursosEstaticos extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**", "/pdv/*.js", "/pdv/*.css")
                .addResourceLocations("classpath:/META-INF/resources/assets/", "classpath:/META-INF/resources/pdv/")
                .setCachePeriod(3600);
    }
}