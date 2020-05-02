package ipp.aci.boleia.util;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;

/**
 * Utilitário para consumo de API de Integração.
 */
public class UtilitarioIntegracao {

    /**
     * Método que consome método da api e trata exceção.
     * @param requisicao Requisição a ser enviada.
     * @param nomeServico Nome do serviço a ser chamado
     * @param consumidor Consumidor do serviço
     * @param <E> O tipo de entrada
     * @param <S> O tipo de saida
     * @return Reposta da integração
     */
    public static <E, S> S invocarIntegracao(E requisicao, String nomeServico, ConsumidorIntegracao<E, S> consumidor)  {
        S resp;
        try {
            resp = consumidor.consumir(requisicao);
        } catch (Exception ex) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ex, nomeServico);
        }
        return resp;
    }

    /**
     * Interface funcional para o consumo da API
     *
     * @param <E> O tipo de entrada
     * @param <S> O tipo de saida
     */
    @FunctionalInterface
    public interface ConsumidorIntegracao<E, S> {

        /**
         * Consome a requisicao e retorna a resposta
         * @param req A requisicao
         * @return A resposta
         */
        S consumir(E req);
    }
}
    