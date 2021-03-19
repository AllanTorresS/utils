package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica da restricao de intervalos de abastecimento na pre-autorizacao
 */
@Component
public class LogicaParametroIntervaloAbastecimentoPreAutorizacao extends LogicaParametroIntervaloBase implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {


    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {
        PreAutorizacaoPedidoVo pedidoHodometroHorimetro = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoHodometroHorimetro);
        Veiculo veiculo = pedidoHodometroHorimetro.getVeiculo();
        this.executarValidacao(veiculo, pedidoHodometroHorimetro.getDataCriacao(), pedidoHodometroHorimetro.getHodometro(), frotaParam, resultado);
        return resultado;
    }
}
