package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que implementa validação de lógica de Horários de Abastecimentos permitidos na pré-autorização.
 */
@Component
public class LogicaParametroHorariosAbastecimentoPreAutorizacao extends LogicaParametroHorariosAbastecimentoBase implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParametroSistema) {
        PreAutorizacaoPedidoVo pedidoPlaca = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoPlaca);
        Veiculo veiculo = pedidoPlaca.getVeiculo();
        executarValidacao(veiculo, pedidoPlaca.getDataCriacao(),frotaParametroSistema,resultado);
        return resultado;
    }
}
