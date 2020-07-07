package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHodometroHorimetro;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.servico.NotificacaoUsuarioSd;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Verifica se o valor do hodometro/horimetro do veiculo abastecido esta dentro dos parametros pre-estabelecidos
 */
@Component
public class LogicaParametroHodometroHorimetro implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IConfiguracaoSistemaDados configs;

    @Autowired
    private NotificacaoUsuarioSd notificacoes;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {

        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);

        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio() || veiculo.isAgregado()) {
            Integer capacidade = veiculo.getCapacidadeTanque();
            BigDecimal consumoEstimado = veiculo.getConsumoEstimadoLitro();
            BigDecimal tolerancia = new BigDecimal(configs.buscarConfiguracoes(ChaveConfiguracaoSistema.TOLERANCIA_HODOMETRO_HORIMETRO).getParametro());
            if (isParamAtivoVeiculo(frotaParam, veiculo)) {
                BigDecimal anterior;
                BigDecimal atual;
                BigDecimal variacaoEsperada;
                if (consumoEstimado != null) {
                    if (autorizacao.getHodometro() != null) {
                        anterior = new BigDecimal(veiculo.getHodometro() != null ? veiculo.getHodometro() : 0L);
                        atual = BigDecimal.valueOf(autorizacao.getHodometro());
                        variacaoEsperada = BigDecimal.valueOf(capacidade.longValue()).multiply(consumoEstimado);
                    } else {
                        anterior = veiculo.getHorimetro() != null ? veiculo.getHorimetro() : BigDecimal.ZERO;
                        atual = autorizacao.getHorimetro();
                        variacaoEsperada = BigDecimal.valueOf(capacidade.longValue()).divide(consumoEstimado, 3, BigDecimal.ROUND_HALF_UP);
                    }
                    if (anterior.compareTo(BigDecimal.ZERO) != 0) {
                        variacaoEsperada = variacaoEsperada.add(variacaoEsperada.multiply(tolerancia));
                        BigDecimal maxEsperado = anterior.add(variacaoEsperada);
                        if (atual.compareTo(maxEsperado) > 0) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.limite.hodometro.horimetro", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca())));
                        }
                    }
                } else {
                    notificacoes.enviarNotificacaoVeiculoSemConsumoEstimado(veiculo);
                }
            }
        }

        return resultado;
    }

    /**
     * Verifica se o parametro esta ativo para o veiculo informado
     *
     * @param frotaParam O parametro
     * @param veiculo O veiculo do abastecimento
     * @return True caso ativo
     */
    private boolean isParamAtivoVeiculo(FrotaParametroSistema frotaParam, Veiculo veiculo) {
        boolean verificar = frotaParam.getVerificarHodometroHorimetroTodosVeiculos() != null && frotaParam.getVerificarHodometroHorimetroTodosVeiculos();
        if(!verificar) {
            for(FrotaParametroSistemaHodometroHorimetro p : frotaParam.getHodometrosHorimetros()) {
                if(p.getVeiculo().getId().equals(veiculo.getId())) {
                    verificar = p.isAtivo();
                    break;
                }
            }
        }
        return verificar;
    }
}
