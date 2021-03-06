package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica da restricao de intervalos de abastecimento na autirizacao
 */
@Component
public class LogicaParametroIntervaloAbastecimento extends LogicaParametroIntervaloBase implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacao;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        this.executarValidacao(veiculo, autorizacao.getDataRequisicao(),autorizacao.getHodometro(), frotaParam, resultado);
        return resultado;
    }

    @Override
    public String obterMensagemErro(String placa, long restante) {
        return mensagens.obterMensagem("parametro.sistema.erro.abastecimento.intervalo", placa);
    }

    @Override
    public String obterMensagemErroKm(String placa, long restante) {
        return mensagens.obterMensagem("parametro.sistema.erro.abastecimento.intervaloKm", placa);
    }
}
