package ipp.aci.boleia.util.seguranca;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Classe que implementa condição de utilização da classe FiltroInterceptacaoForwardJwt
 */
class CondicaoDiferenteDevLocal implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String ambienteDevLocal = (conditionContext.getEnvironment().getProperty("internal.loadbalancer.enabled"));
        return !"dev-local".equalsIgnoreCase(ambienteDevLocal);
    }
}

/**
 * Classe responsável por encaminhar requisições para outros módulos da aplicação
 */
@Component
@Conditional(CondicaoDiferenteDevLocal.class)
public class FiltroInterceptacaoForwardJwt implements IFiltroInterceptacaoForwardJwt {

    @Override
    public boolean encaminharRequisicao(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return false;
    }
}
