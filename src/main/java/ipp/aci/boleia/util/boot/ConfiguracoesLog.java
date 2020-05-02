package ipp.aci.boleia.util.boot;
import ipp.aci.boleia.util.log.ClienteHttpDadosHandler;
import ipp.aci.boleia.util.log.LoggableDispatcherServlet;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Classe responsável pela configuração do log no sistema.
 *
 */
public class ConfiguracoesLog {
    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        return new ServletRegistrationBean(dispatcherServlet());
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new LoggableDispatcherServlet();
    }

    @Bean
    public ClienteHttpDadosHandler clienteHttpDadosHandler(){
        return new ClienteHttpDadosHandler();
    }
}