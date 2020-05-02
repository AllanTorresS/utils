package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;

/**
 * Base para a construcao de logicas de execucao de regras dinamicas do sistema
 *
 * @param <D> O tipo dos dados necessarios a execucao da logica do parametro
 */
public interface ILogicaParametroSistema<D> {

    /**
     * Executa a regra, retornando o resultado obtido
     * @param contexto O contexto de execucao
     * @param frotaParametroSistema A instancia do parametro para a frota em questao
     * @return O resultado da execucao da logica da regra
     */
    ResultadoExecucaoParametroSistemaVo<D> executar(ContextoExecucaoParametroSistemaVo<D> contexto, FrotaParametroSistema frotaParametroSistema);

}
