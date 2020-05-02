package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.servico.NotificacaoUsuarioSd;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.excecao.ExcecaoViolacaoRegraVersatil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Trata excecoes lancadas pelo sistema na execução e aplicação dos parametros de abastecimento, escrevendo-as no log e enviando mensagens aos usuários interessados
 */
@Component
public class TratadorViolacoesParametrosAbastecimento implements ITratadorViolacoesParametros<AutorizacaoPagamento> {

    @Autowired
    private NotificacaoUsuarioSd notificacoes;

    @Override
    public ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> tratarViolacaoRegraRestritiva(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto) throws ExcecaoValidacao {
        ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacaoRestritiva();
        notificarViolacaoRegraRestritiva(contexto);
        throw new ExcecaoValidacao(violacao.getMensagemErro());
    }

    @Override
    public ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> tratarViolacaoRegraVersatil(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto) throws ExcecaoViolacaoRegraVersatil {
        ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacaoRestritiva();
        notificarViolacaoRegraRestritiva(contexto);
        throw new ExcecaoViolacaoRegraVersatil(violacao.getMensagemErro());
    }

    @Override
    public ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> tratarViolacoesInformativas(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto) {
        List<ResultadoExecucaoParametroSistemaVo> violacoes = contexto.getViolacoesInformativas();
        if(violacoes.size() > 1) {
            notificacoes.enviarNotificacaoViolacaoMultiplosParametroInformativos(contexto.getDados(), violacoes.size());
        } else {
            ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacoesInformativas().get(0);
            ParametroSistema parametro = violacao.getParametroSistema();
            notificacoes.enviarNotificacaoViolacaoParametroInformativo(contexto.getDados(), parametro);
        }
        return contexto;
    }

    /**
     * Notifica caso a regra restritiva seja violada
     * @param contexto contexto de execucao do parametro do sistema
     */
    private void notificarViolacaoRegraRestritiva(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto) {
        ResultadoExecucaoParametroSistemaVo violacao = contexto.getViolacaoRestritiva();
        ParametroSistema parametro = violacao.getParametroSistema();
        notificacoes.enviarNotificacaoViolacaoParametroRestritivo(contexto.getDados(), parametro);
    }
}
