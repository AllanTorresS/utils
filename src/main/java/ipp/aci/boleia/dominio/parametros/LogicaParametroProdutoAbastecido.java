package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProdutoAbastecimento;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Verifica se o produto do abastecimento condiz com o tipo de motor/combustivel do veciulo em questao
 */
@Component
public class LogicaParametroProdutoAbastecido extends LogicaParametroProdutoAbastecidoBase implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio() || veiculo.isAgregado()) {
            List<FrotaParametroSistemaProdutoAbastecimento> combustiveisPermitidos = obterCombustiveisPermitidos(veiculo);
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                if (item.isAbastecimento()) {
                    boolean falhou = executarValidacao(veiculo, item.getCombustivel(), item.getNome(), combustiveisPermitidos, resultado);
                    if (falhou) {
                        break;
                    }
                }
            }
        }
        return resultado;
    }
}
