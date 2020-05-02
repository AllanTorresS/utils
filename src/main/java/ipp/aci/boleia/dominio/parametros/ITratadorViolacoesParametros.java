package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.excecao.ExcecaoViolacaoRegraVersatil;

/**
 * Define o contrato para implementacao de tratadores de violacao de parametros.
 * Util para casos em que as violacoes precisam de tratamento unificado ao final do looping de execucoes.
 *
 * @param <D> O tipo dos dados necessarios a execucao dos parametros
 */
public interface ITratadorViolacoesParametros<D> {

    /**
     * Trata a violacao de parametros restritivos
     *
     * @param contexto O contexto de execucao
     * @return O contexto de execucao
     * @throws ExcecaoValidacao Quando não há permissão para executar a função
     */
    ContextoExecucaoParametroSistemaVo<D> tratarViolacaoRegraRestritiva(ContextoExecucaoParametroSistemaVo<D> contexto) throws ExcecaoValidacao;

    /**
     * Trata a violacao de um parametro versatil marcado como restritivo
     *
     * @param contexto O contexto de execucao
     * @return O contexto de execucao
     * @throws  ExcecaoViolacaoRegraVersatil Erro lançado quando a regra é violada
     */
    ContextoExecucaoParametroSistemaVo<D> tratarViolacaoRegraVersatil(ContextoExecucaoParametroSistemaVo<D> contexto) throws ExcecaoViolacaoRegraVersatil;

    /**
     * Trata a violacao de parametros versateis marcados como informativos
     *
     * @param contexto O contexto de execucao
     * @return O contexto de execucao
     */
    ContextoExecucaoParametroSistemaVo<D> tratarViolacoesInformativas(ContextoExecucaoParametroSistemaVo<D> contexto);

}
