package ipp.aci.boleia.util.seguranca;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Contrato de implementação de interceptadores responsáveis por redirecionamento de requisições
 * para outros módulos da aplicação
 */
public interface IFiltroInterceptacaoForwardJwt {

    /**
     * Encaminha uma requisição para outro módulo da aplicação
     * @param req O request recebido
     * @param resp A response enviada
     * @return True caso a requisição tenha sido redirecionada, e false caso contrário
     * @throws IOException Em casos de erros de criação de conexão
     * @throws ServletException Em caso de erro no servlet
     */
    boolean encaminharRequisicao (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException;
}
