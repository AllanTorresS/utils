package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementa a logica da restricao de intervalos de abastecimento na pre-autorizacao
 */
@Component
public class LogicaParametroPostosAutorizadosAbastecimentoPreAutorizacao implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {

        PreAutorizacaoPedidoVo pedido = contexto.getDados();
        List<PontoDeVenda> postosDisponiveis = pedido.getPostosDisponiveis();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedido);

        if (pedido.getLatitude() == null && pedido.getLongitude() == null) {
            return resultado;
        }

        if (postosDisponiveis != null) {
            for (PontoDeVenda pvProximo : postosDisponiveis) {
                for (FrotaParametroSistemaPostoAutorizadoAbastecimento parametroPv : frotaParam.getPostosAutorizadosAbastecimento()
                                                                                                .stream().filter(FrotaParametroSistemaPostoAutorizadoAbastecimento::getAutorizado).collect(Collectors.toList())) {
                    if (parametroPv.isAutorizado() && Objects.equals(parametroPv.getPontoVenda().getId(), pvProximo.getId())) {
                        return resultado;
                    }
                }
            }
        }

        resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
        resultado.setCodigoErro(Erro.SEM_PV_HABILITADO_PROXIMO);
        resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.posto.nao.autorizado.preautorizacao"));
        return resultado;
    }
}
