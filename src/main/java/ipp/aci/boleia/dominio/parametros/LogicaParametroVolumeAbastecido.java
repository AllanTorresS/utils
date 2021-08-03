package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

/**
 * Verifica se o volume de produto do abastecimento condiz com tamanho do tanque do
 * veciulo em questao, no contexto de autorizacao.
 */
@Component
public class LogicaParametroVolumeAbastecido extends LogicaParametroVolumeAbastecidoBase implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.getCapacidadeTanque() != null) {
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                if (item.isAbastecimento()) {
                    boolean falhou = executarValidacao(veiculo, item.getQuantidade(), resultado);
                    if (falhou) {
                        break;
                    }
                }
            }
        }
        return resultado;
    }

}
