package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que implementa validação de lógica de Horários de Abastecimentos permitidos na autorização.
 */
@Component
public class LogicaParametroHorariosAbastecimento extends LogicaParametroHorariosAbastecimentoBase implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParametroSistema) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        executarValidacao(veiculo,autorizacao.getDataProcessamento(),frotaParametroSistema,resultado);
        return resultado;
    }
}
