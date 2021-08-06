package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

/**
 * Verifica se o valor do hodometro/horimetro do veiculo abastecido esta dentro dos parametros pre-estabelecidos para a pre-autorização.
 */
@Component
public class LogicaParametroHodometroHorimetroPreAutorizacao extends LogicaParametroHodometroHorimetroBase implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {

        PreAutorizacaoPedidoVo pedidoHodometroLitragem = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoHodometroLitragem);

        Veiculo veiculo = pedidoHodometroLitragem.getVeiculo();
        executarValidacao(veiculo, pedidoHodometroLitragem.getHodometro(), pedidoHodometroLitragem.getHorimetro(), false, frotaParam, resultado);
        return resultado;
    }
}
