package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.boot.ConfiguracoesSwagger;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.MensagemErro;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.rotas.Rotas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static ipp.aci.boleia.util.UtilitarioFormatacao.obterString;

/**
 * Filtro de autorizacao da API Docs
 */
@Component
public class FiltroAutorizacaoApiDocs implements Filter {

    private static final String PERMISSAO_API_FROTISTA = "DOC_API_FROTA";
    private static final String PERMISSAO_API_EXTERNO = "DOC_API_EXTERNO";

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private ProvedorAutenticacao provedorAutenticacao;

    @Autowired
    private IServicosDeSistemaExterno servicosDeSistemaExterno;

    @Autowired
    private Mensagens mensagens;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (request.getRequestURL().toString().endsWith(Rotas.BASE_API_DOCS)) {
            String group = obterString(request.getParameter("group"));
            String client = obterString(request.getParameter("client"));
            String secret = obterString(request.getParameter("secret"));
            Usuario usuario;
            if(!client.trim().isEmpty() && !secret.trim().isEmpty()) {
                usuario = servicosDeSistemaExterno.obterUsuarioSistemaExterno(client, secret);
            } else {
                usuario = ambiente.getUsuarioLogado();
            }
            if(usuario == null || !usuarioPossuiPermissao(usuario,group)){
                enviarErroPermissaoNegada(response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * Verifica se um determinado usuário possui permissão para um grupo de documentação de API.
     *
     * @param usuario O usuário informado.
     * @param grupo O grupo de documentação.
     * @return Verdadeiro caso o usuário possua permissão para acesso ao grupo de documentação.
     */
    private boolean usuarioPossuiPermissao(Usuario usuario, String grupo) {
        if(grupo.trim().equalsIgnoreCase(ConfiguracoesSwagger.API_FROTISTA_GROUP_NAME)) {
            return provedorAutenticacao.possuiPermissao(usuario, PERMISSAO_API_FROTISTA);
        } else if (grupo.trim().equalsIgnoreCase(ConfiguracoesSwagger.API_EXTERNO_GROUP_NAME)) {
            return provedorAutenticacao.possuiPermissao(usuario, PERMISSAO_API_EXTERNO);
        }
        return false;
    }

    /**
     * Altera a response com o formato de permissao negada padrão
     *
     * @param response a ser alterada
     * @throws IOException devido a problema de escrita no output stream
     */
    private void enviarErroPermissaoNegada(HttpServletResponse response) throws IOException {
        Erro erro = Erro.PERMISSAO_NEGADA;
        String mensagem = mensagens.obterMensagem(erro.getChaveMensagem());
        MensagemErro retorno = new MensagemErro(erro.getCodigo(), Collections.singletonList(mensagem));
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        byte[] bytes = UtilitarioJson.toJSON(retorno).getBytes(StandardCharsets.UTF_8);
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
    }
    
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        // nada a fazer
	}

	@Override
	public void destroy() {
		// nada a fazer
	}
    
}