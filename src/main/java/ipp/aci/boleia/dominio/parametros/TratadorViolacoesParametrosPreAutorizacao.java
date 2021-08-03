package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.excecao.ExcecaoViolacaoRegraVersatil;
import org.springframework.stereotype.Component;

/**
 *  Trata excecoes lancadas pelo sistema na execução e aplicação dos parametros de abastecimento na pré-autorização
 */
@Component
public class TratadorViolacoesParametrosPreAutorizacao implements ITratadorViolacoesParametros<PreAutorizacaoPedidoVo> {

    @Override
    public ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> tratarViolacaoRegraRestritiva(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto) throws ExcecaoValidacao {
        ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacaoRestritiva();

        Erro codigoErro = Erro.ERRO_VALIDACAO;
        if(violacao.getCodigoErro() != null) {
            codigoErro = violacao.getCodigoErro();
        }
        throw new ExcecaoValidacao(codigoErro, violacao.getMensagemErro());
    }

    @Override
    public ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> tratarViolacaoRegraVersatil(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto) throws ExcecaoViolacaoRegraVersatil {
        ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacaoRestritiva();

        Erro codigoErro = Erro.ERRO_VALIDACAO;
        if(violacao.getCodigoErro() != null) {
            codigoErro = violacao.getCodigoErro();
        }
        throw new ExcecaoViolacaoRegraVersatil(codigoErro, violacao.getMensagemErro());
    }

    @Override
    public ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> tratarViolacoesInformativas(ContextoExecucaoParametroSistemaVo<PreAutorizacaoPedidoVo> contexto) {
        return contexto;
    }
}
