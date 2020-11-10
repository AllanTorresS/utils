package ipp.aci.boleia.util.seguranca;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ConditionalOnProperty(name="internal.loadbalancer.enabled", havingValue = "dev-local", matchIfMissing = false)
public class FiltroInterceptacaoForwardJwt implements IFiltroInterceptacaoForwardJwt {

    @Override
    public boolean encaminharRequisicao(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return false;
    }
}
