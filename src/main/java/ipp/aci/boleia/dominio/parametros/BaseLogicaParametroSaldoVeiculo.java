package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.excecao.Erro;

import java.math.BigDecimal;

/**
 * Base para implementacao de regras que validam saldo do veiculo no abastecimento
 */
public abstract class BaseLogicaParametroSaldoVeiculo implements ILogicaParametroSistema<AutorizacaoPagamento> {


    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {

        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();

        if(aplicarRegraAoVeiculo(veiculo)) {
            SaldoVeiculo saldoVeiculo = veiculo.getSaldoVeiculo();
            boolean cotaEmLitros = frotaParam.getEmLitros() != null && frotaParam.getEmLitros();
            boolean cotaMensal = frotaParam.getCotaVeiculoPorAbastecimento() == null || !frotaParam.getCotaVeiculoPorAbastecimento();
            BigDecimal montante = cotaEmLitros ? autorizacao.getTotalLitrosAbastecimento() : autorizacao.getValorTotalAbastecimento();
            if (saldoVeiculo != null && !saldoVeiculo.isSaldoSuficienteParaAutorizar(montante, cotaEmLitros, cotaMensal)) {
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setCodigoErro(Erro.VEICULO_SALDO_COTA_INSUFICIENTE);
                resultado.setMensagemErro(obterMensagemRegraViolada(veiculo, cotaEmLitros, montante));
            }
        }

        return resultado;
    }

    /**
     * Obtem a mensagem de erro em caso de violacao da regra
     * @param veiculo O veiculo
     * @param cotaEmLitros Se a cota esta sendo calculada em litros
     * @param montante O montante abastecido
     * @return A mensagem de erro
     */
    protected abstract String obterMensagemRegraViolada(Veiculo veiculo, boolean cotaEmLitros, BigDecimal montante);

    /**
     * Determina se a regra se aplica ao veiculo
     * @param veiculo O veiculo em questao
     * @return True caso a regra se aplique
     */
    protected abstract boolean aplicarRegraAoVeiculo(Veiculo veiculo);
}
