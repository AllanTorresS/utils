package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica de verificaca das cotas de abastecimento dos veiculos para a pre-autorizacao.
 */
@Component
public class LogicaParametroCotaVeiculoPreAutorizacao implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {

        PreAutorizacaoPedidoVo pedidoCota = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoCota);
        Veiculo veiculo = pedidoCota.getVeiculo();

        if (aplicarRegraAoVeiculo(veiculo) && frotaParam.getCotaVeiculoVisivelMotorista() &&  veiculo.getSaldoVeiculo() != null) {
            boolean cotaMensal = frotaParam.getCotaVeiculoPorAbastecimento() == null || !frotaParam.getCotaVeiculoPorAbastecimento();
            boolean cotaEmLitros = frotaParam.getEmLitros() != null && frotaParam.getEmLitros();
            if (!veiculo.getSaldoVeiculo().isSaldoSuficienteParaPreAutorizar(cotaEmLitros, cotaMensal)) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setCodigoErro(Erro.VEICULO_SALDO_COTA_INSUFICIENTE);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.cota.veiculo.zero", veiculo.getPlaca()));
            }
        }

        return resultado;
    }

    protected boolean aplicarRegraAoVeiculo(Veiculo veiculo) {
        return veiculo.isProprio();
    }
}
