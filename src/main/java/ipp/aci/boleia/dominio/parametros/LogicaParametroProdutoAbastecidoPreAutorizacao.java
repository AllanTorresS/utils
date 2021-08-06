package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProdutoAbastecimento;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Verifica no pedido da pre-autorização se o produto do abastecimento condiz com o tipo de motor/combustivel do veciulo em questao
 */
@Component
public class LogicaParametroProdutoAbastecidoPreAutorizacao extends LogicaParametroProdutoAbastecidoBase implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParametroSistema) {
        PreAutorizacaoPedidoVo pedido = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedido);
        Veiculo veiculo = pedido.getVeiculo();
        if(veiculo.isProprio() || veiculo.isAgregado()) {
            List<FrotaParametroSistemaProdutoAbastecimento> combustiveisPermitidos = obterCombustiveisPermitidos(veiculo);
            TipoCombustivel combustivel = pedido.getCombustivel();
            if (combustivel != null) {
                executarValidacao(veiculo, combustivel, combustivel.getDescricao(), combustiveisPermitidos, resultado);
            }
        }
        return resultado;
    }
}
