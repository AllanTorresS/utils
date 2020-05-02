package ipp.aci.boleia.dados.servicos.ensemble;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;

import javax.annotation.PostConstruct;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * Base para construcao de repositorios de acesso a servicos SOAP
 *
 * @param <T> O tipo do servico SOAP a ser invocado
 */
public abstract class ServicoSoapDados<T> {

    private static final String TIMEOUT_CONEXAO_PARAM = "com.sun.xml.internal.ws.connect.timeout";
    private static final String TIMEOUT_RESPOSTA_PARAM = "com.sun.xml.internal.ws.request.timeout";
    private static final int TIMEOUT_CONEXAO_VALOR = 30000;
    private static final int TIMEOUT_RESPOSTA_VALOR = 60000;

    private T servico;

    /**
     * Instancia o servico e injeta um tratador para os cabecalhos de autenticacao
     */
    @PostConstruct
    public void init() {
        WsSecurityUsernameTokenHandler headerHandler = instanciarHeadersHandler();
        try {
            servico = instanciarServico();
            if(headerHandler != null) {
                Handler[] handlerChain = {headerHandler};
                ((BindingProvider) servico).getBinding().setHandlerChain(Arrays.asList(handlerChain));
            }
            ((BindingProvider) servico).getRequestContext().put(TIMEOUT_CONEXAO_PARAM, TIMEOUT_CONEXAO_VALOR);
            ((BindingProvider) servico).getRequestContext().put(TIMEOUT_RESPOSTA_PARAM, TIMEOUT_RESPOSTA_VALOR);
        } catch (MalformedURLException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e, headerHandler.getNomeServico());
        }
    }

    /**
     * Instancia um tratador de cabecalhos para invocacao do web service
     *
     * @return O tratador de cabecalhos
     */
    protected abstract WsSecurityUsernameTokenHandler instanciarHeadersHandler();

    /**
     * Cria uma instancia do servico
     *
     * @return A instancia do servico
     * @throws MalformedURLException Quando a url de acesso nao esta coerente com a especificacao
     */
    protected abstract T instanciarServico() throws MalformedURLException;

    /**
     * Retorna a instancia do servico previamente construido e mantido em memoria para ganho de performance
     *
     * @return A instancia do servico
     */
    public T getServico() {
        return servico;
    }
}
