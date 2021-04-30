package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoAbastecimento;
import ipp.aci.boleia.dominio.TipoCombustivel;
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
import java.util.Objects;

/**
 * Verifica se a quantidade do produto abastecido está abaixo do limite cadastrado para o produto
 */
@Component
public class LogicaParametroPrecoMaximoPreAutorizacao implements ILogicaParametroSistema<PreAutorizacaoPedidoVo> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> executar(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto, FrotaParametroSistema frotaParam) {
        PreAutorizacaoPedidoVo pedido = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> resultado = new ResultadoExecucaoParametroSistemaVo<>(pedido);
        Veiculo veiculo = pedido.getVeiculo();
        TipoCombustivel combustivel = pedido.getCombustivel();
        if (veiculo.isProprio()) {
            BigDecimal quantidadeMaximaPermitidaAbastecimento = frotaParam.getQuantidadeMaximaPorCombustivel().stream()
                    .filter(pCombustivel -> Objects.equals(combustivel.getId(), pCombustivel.getCombustivel().getId())
                        && pCombustivel.getQuantidadePermitida() != null)
                    .map(FrotaParametroSistemaPrecoMaximoAbastecimento::getQuantidadePermitida).findFirst().orElse(null);

            if (quantidadeMaximaPermitidaAbastecimento != null && pedido.getLitragem().compareTo((quantidadeMaximaPermitidaAbastecimento)) > 0) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_QUANTIDADE_MAX_PRODUTO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.quantidade.maxima.produto.preAutorizacao",
                        UtilitarioFormatacao.formatarInteiro(quantidadeMaximaPermitidaAbastecimento), combustivel.getDescricao()));
            }
        }
        return resultado;
    }
}
