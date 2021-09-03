package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.stereotype.Component;

/**
 * Verifica se o valor do hodometro/horimetro do veiculo abastecido esta dentro dos parametros pre-estabelecidos
 */
@Component
public class LogicaParametroHodometroHorimetro extends LogicaParametroHodometroHorimetroBase implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);

        Veiculo veiculo = autorizacao.getVeiculo();
        executarValidacao(veiculo, autorizacao.getHodometro(), autorizacao.getHorimetro(), true, frotaParam, resultado);

        return resultado;
    }
}
