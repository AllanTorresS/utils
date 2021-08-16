package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

/**
 * Verifica se o volume de produto do abastecimento condiz com tamanho do tanque do
 * veciulo em questao, no contexto de pre-autorizacao.
 */
@Component
public class LogicaParametroVolumeAbastecidoPreAutorizacao extends LogicaParametroVolumeAbastecidoBase implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {
        PreAutorizacaoPedidoVo pedidoLitragem = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoLitragem);
        Veiculo veiculo = pedidoLitragem.getVeiculo();
        if(veiculo.getCapacidadeTanque() != null) {
            executarValidacao(veiculo, pedidoLitragem.getLitragem(), resultado);
        }
        return resultado;
    }

}
