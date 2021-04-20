package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHodometroHorimetro;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.servico.NotificacaoUsuarioSd;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Verifica se o valor do hodometro/horimetro do veiculo abastecido esta dentro dos parametros pre-estabelecidos
 */
public abstract class LogicaParametroHodometroHorimetroBase  {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IConfiguracaoSistemaDados configs;

    @Autowired
    private NotificacaoUsuarioSd notificacoes;

    /**
     * Executa a validação do hodometro/horimetro do veiculo de um abastecimento.
     *
     * @param veiculo veiculo a ser validado
     * @param hodometro valor do hodometro
     * @param horimetro valor do horimetro
     * @param notificaGestorVeiculoSemValorConsumoCadastrado caso seja nescessario notificar o gestor da frota
     * @param frotaParam parametro da execução
     * @param resultado que será preenchido com a validação
     */
    public void executarValidacao(Veiculo veiculo, Long hodometro, BigDecimal horimetro, boolean notificaGestorVeiculoSemValorConsumoCadastrado, FrotaParametroSistema frotaParam, ResultadoExecucaoParametroSistemaVo<?> resultado){
        if((veiculo.isProprio() || veiculo.isAgregado()) &&  hodometro != null && horimetro != null) {
            Integer capacidade = veiculo.getCapacidadeTanque();
            BigDecimal consumoEstimado = veiculo.getConsumoEstimadoLitro();
            BigDecimal tolerancia = new BigDecimal(configs.buscarConfiguracoes(ChaveConfiguracaoSistema.TOLERANCIA_HODOMETRO_HORIMETRO).getParametro());
            Erro codigoErro;
            if (isParamAtivoVeiculo(frotaParam, veiculo)) {
                BigDecimal anterior;
                BigDecimal atual;
                BigDecimal variacaoEsperada;
                if (consumoEstimado != null) {
                    if (hodometro != null) {
                        anterior = new BigDecimal(veiculo.getHodometro() != null ? veiculo.getHodometro() : 0L);
                        atual = BigDecimal.valueOf(hodometro);
                        variacaoEsperada = BigDecimal.valueOf(capacidade.longValue()).multiply(consumoEstimado);
                        codigoErro = Erro.ERRO_AUTORIZACAO_HODOMETRO_LIMITE;
                    } else {
                        anterior = veiculo.getHorimetro() != null ? veiculo.getHorimetro() : BigDecimal.ZERO;
                        atual = horimetro;
                        variacaoEsperada = BigDecimal.valueOf(capacidade.longValue()).divide(consumoEstimado, 3, BigDecimal.ROUND_HALF_UP);
                        codigoErro = Erro.ERRO_AUTORIZACAO_HORIMETRO_LIMITE;
                    }
                    if (anterior.compareTo(BigDecimal.ZERO) != 0) {
                        variacaoEsperada = variacaoEsperada.add(variacaoEsperada.multiply(tolerancia));
                        BigDecimal maxEsperado = anterior.add(variacaoEsperada);
                        if (atual.compareTo(maxEsperado) > 0) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setCodigoErro(codigoErro);
                            resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.limite.hodometro.horimetro", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca())));
                        }
                    }
                } else if (notificaGestorVeiculoSemValorConsumoCadastrado){
                    notificacoes.enviarNotificacaoVeiculoSemConsumoEstimado(veiculo);
                }
            }
        }
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
