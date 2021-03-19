package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
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

    /**
     * Obtem a configuracao para o veiculo em questao
     * @param param O parametro
     * @param veiculo O veiculo em questao
     * @return A configuracao para o veiculo em questao
     */
    private FrotaParametroSistemaIntervaloAbastecimento getConfiguracaoVeiculo(FrotaParametroSistema param, Veiculo veiculo) {
        for(FrotaParametroSistemaIntervaloAbastecimento p : param.getIntervalosAbastecimento()) {
            if(p.getVeiculo().getId().equals(veiculo.getId())) {
                return p;
            }
        }
        return null;
    }
}
