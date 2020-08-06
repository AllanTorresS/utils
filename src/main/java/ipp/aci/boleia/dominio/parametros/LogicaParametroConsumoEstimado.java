package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Verifica se o consumo estimado do veiculo esta dentro do intervalo parametrizado
 */
@Component
public class LogicaParametroConsumoEstimado implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizadaoDados;

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacaoAtual = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacaoAtual);
        Veiculo veiculo = autorizacaoAtual.getVeiculo();
        if (veiculo.isProprio()) {
            final AutorizacaoPagamento autorizacaoAnterior = repositorioAutorizadaoDados.obterUltimoAbastecimentoVeiculo(veiculo.getId());
            final BigDecimal consumoAnterior = autorizacaoAnterior == null ? null : autorizacaoAnterior.obterConsumo();
            final BigDecimal consumoAtual = autorizacaoAtual.obterConsumo();
            if (consumoAnterior != null && consumoAtual != null) {
                final BigDecimal consumoEstimadoMinimo = consumoAnterior.multiply(BigDecimal.ONE.subtract(frotaParam.getConsumo().getPorcentagemLimiteMinimo()));
                if (consumoAtual.compareTo(consumoEstimadoMinimo) < 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.minimo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMinimo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoAtual.setScale(2, RoundingMode.HALF_UP))));
                }
                final BigDecimal consumoEstimadoMaximo = consumoAnterior.multiply(BigDecimal.ONE.add(frotaParam.getConsumo().getPorcentagemLimiteMaximo()));
                if (consumoAtual.compareTo(consumoEstimadoMaximo) > 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.maximo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMaximo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoAtual.setScale(2, RoundingMode.HALF_UP))));
                }
            }
        }
        return resultado;
    }

}
