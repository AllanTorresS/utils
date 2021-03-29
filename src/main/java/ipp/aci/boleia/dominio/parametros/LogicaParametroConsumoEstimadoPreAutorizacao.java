package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Verifica se o consumo estimado do veiculo esta dentro do intervalo parametrizado na pre-autorizacao,
 * coparando valores fonecidos pelo usuario com a autorizacao pagamento anterior.
 */
@Component
public class LogicaParametroConsumoEstimadoPreAutorizacao implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizadaoDados;

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {
        PreAutorizacaoPedidoVo pedidoHodometroHorimetroLitragem = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedidoHodometroHorimetroLitragem);
        Veiculo veiculo = pedidoHodometroHorimetroLitragem.getVeiculo();
        if (veiculo.isProprio()) {
            final AutorizacaoPagamento autorizacaoAnterior = repositorioAutorizadaoDados.obterUltimoAbastecimentoVeiculo(veiculo.getId());
            final BigDecimal consumoAnterior = autorizacaoAnterior == null ? null : autorizacaoAnterior.obterConsumo();
            final BigDecimal consumoAtual = autorizacaoAnterior == null ? null : autorizacaoAnterior.obterConsumo(pedidoHodometroHorimetroLitragem.getLitragem(), pedidoHodometroHorimetroLitragem.getHodometro(), pedidoHodometroHorimetroLitragem.getHorimetro());
            if (consumoAnterior != null && consumoAtual != null) {
                final BigDecimal consumoEstimadoMinimo = consumoAnterior.multiply(BigDecimal.ONE.subtract(frotaParam.getConsumo().getPorcentagemLimiteMinimo()));
                if (consumoAtual.compareTo(consumoEstimadoMinimo) < 0) {
                    resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_CONSUMO_ESTIMADO_MIN);
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.minimo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMinimo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoAtual.setScale(2, RoundingMode.HALF_UP))));
                }
                final BigDecimal consumoEstimadoMaximo = consumoAnterior.multiply(BigDecimal.ONE.add(frotaParam.getConsumo().getPorcentagemLimiteMaximo()));
                if (consumoAtual.compareTo(consumoEstimadoMaximo) > 0) {
                    resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_CONSUMO_ESTIMADO_MAX);
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.maximo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMaximo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoAtual.setScale(2, RoundingMode.HALF_UP))));
                }
            }
        }
        return resultado;
    }

}
