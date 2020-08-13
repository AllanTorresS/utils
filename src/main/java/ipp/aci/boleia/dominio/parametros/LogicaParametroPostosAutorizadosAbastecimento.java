package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.servico.ParametroSistemaSd;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica da restricao de intervalos de abastecimento
 */
@Component
public class LogicaParametroPostosAutorizadosAbastecimento implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private ParametroSistemaSd parametroSistemaSd;

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio()) {
            Long idPtov = autorizacao.getPontoVenda().getId();
            FrotaParametroSistemaPostoAutorizadoAbastecimento autorizacaoPosto = frotaParam.getAutorizacaoAbastecimentoPosto(idPtov);
            if (autorizacaoPosto == null || !autorizacaoPosto.isHabilitadoEAutorizado()) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.posto.nao.autorizado", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca())));
            } else if(parametroSistemaSd.violouLimiteAbastecimento(autorizacao, autorizacaoPosto, frotaParam.getEmLitros())) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.posto.maximo", UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca()), autorizacao.getNomePosto()));
            }
        }
        return resultado;
    }
}
