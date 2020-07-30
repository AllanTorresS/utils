package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Implementa a logica da restricao de intervalos de abastecimento
 */
@Component
public class LogicaParametroIntervaloAbastecimento implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacao;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio() || veiculo.isAgregado()) {
            boolean ativoParaTodos = frotaParam.getVerificarIntervaloAbastecimentoTodosVeiculos() != null && frotaParam.getVerificarIntervaloAbastecimentoTodosVeiculos();
            FrotaParametroSistemaIntervaloAbastecimento intervalo = getConfiguracaoVeiculo(frotaParam, veiculo);
            if (ativoParaTodos || (intervalo != null && intervalo.isAtivo())) {
                AutorizacaoPagamento ultimo = repositorioAutorizacao.obterUltimoAbastecimentoVeiculo(veiculo.getId());
                if (ultimo != null) {
                    if (veiculo.getHodometro() != null && (intervalo.getQuilometrosIntervaloAbastecimento() != null || frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos() != null)) {
                        Long minimoKm = ativoParaTodos ? frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos() : intervalo.getQuilometrosIntervaloAbastecimento();
                        Long ultimoHodometro = ultimo.getHodometro();
                        Long hodometroAtual = autorizacao.getHodometro();
                        Long diferenca = hodometroAtual - ultimoHodometro;
                        if (diferenca < minimoKm) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_INTERVALO_PERMITIDO);
                            resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.intervaloKm", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca())));
                        }
                    }
                    else {
                        int minimoMinutos = ativoParaTodos ? frotaParam.getMinutosIntervaloAbastecimentoTodosVeiculos() : intervalo.getMinutosIntervaloAbastecimento();
                        Date dataUltimo = ultimo.getDataRequisicao();
                        Date dataCorrente = autorizacao.getDataRequisicao();
                        long decorrido = (dataCorrente.getTime() - dataUltimo.getTime()) / 1000 / 60;
                        if (decorrido < minimoMinutos) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_INTERVALO_PERMITIDO);
                            resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.intervalo", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca())));
                        }
                    }
                }
            }
        }
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
