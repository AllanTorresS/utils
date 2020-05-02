package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.servico.NotificacaoUsuarioSd;
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
    private Mensagens mensagens;

    @Autowired
    private NotificacaoUsuarioSd notificacoes;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio()) {
            BigDecimal quantidadeAbastecida = autorizacao.getTotalLitrosAbastecimento();
            BigDecimal consumoEstimado = veiculo.getConsumoEstimadoLitro();
            BigDecimal consumoEstimadoMinimo;
            BigDecimal consumoEstimadoMaximo;
            BigDecimal consumoReal;
            if (consumoEstimado != null) {
                if (autorizacao.getHodometro() != null) {
                    BigDecimal distancia = new BigDecimal(autorizacao.getHodometro() - autorizacao.getHodometroAnterior());
                    consumoReal = distancia.divide(quantidadeAbastecida, 3, BigDecimal.ROUND_HALF_UP);
                } else {
                    BigDecimal tempo = autorizacao.getHorimetro().subtract(autorizacao.getHorimetroAnterior());
                    consumoReal = quantidadeAbastecida.divide(tempo, 3, BigDecimal.ROUND_HALF_UP);
                }
                consumoEstimadoMinimo = consumoEstimado.multiply(BigDecimal.ONE.subtract(frotaParam.getConsumo().getPorcentagemLimite()));
                consumoEstimadoMaximo = consumoEstimado.multiply(BigDecimal.ONE.add(frotaParam.getConsumo().getPorcentagemLimite()));
                if (consumoReal.compareTo(consumoEstimadoMinimo) < 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.minimo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMinimo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoReal.setScale(2, RoundingMode.HALF_UP))));
                }
                if(consumoReal.compareTo(consumoEstimadoMaximo) > 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.consumo.estimado.maximo", veiculo.getPlaca(), UtilitarioFormatacao.formatarDecimal(consumoEstimadoMaximo.setScale(2, RoundingMode.HALF_UP)), UtilitarioFormatacao.formatarDecimal(consumoReal.setScale(2, RoundingMode.HALF_UP))));
                }
            } else {
                notificacoes.enviarNotificacaoVeiculoSemConsumoEstimado(veiculo);
            }
        }
        return resultado;
    }

}
