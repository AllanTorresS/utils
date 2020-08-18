package ipp.aci.boleia.util.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Classe de configuracao do CORS
 */
@Configuration
public class ConfiguracoesCors {

    @Value("${cors.allowed.origins}")
    private String[] allowedOrigins;

    public static final String[] HTTP_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT"};

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(HTTP_METHODS)
                        .allowedHeaders("authorization", "content-type")
                        .allowCredentials(true).maxAge(3600);

                registry
                        .addMapping("/api/frotista/**")
                        .allowedOrigins("*")
                        .allowedMethods(HTTP_METHODS)
                        .allowCredentials(false).maxAge(3600);

                registry
                        .addMapping("/api/externo/**")
                        .allowedOrigins("*")
                        .allowedMethods(HTTP_METHODS)
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }
}