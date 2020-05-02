package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.SaldoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Extende a funcionalidade da regra de cotas para veículos agregados
 */
@Component
public class LogicaParametroCreditoVeiculoAgregado extends BaseLogicaParametroSaldoVeiculo {

    @Autowired
    private Mensagens mensagens;

    @Override
    protected String obterMensagemRegraViolada(Veiculo veiculo, SaldoVeiculo saldoVeiculo, boolean cotaEmLitros, boolean cotaMensal) {
        return mensagens.obterMensagem("parametro.sistema.erro.abastecimento.credito.veiculo",
            veiculo.getPlaca(),
            UtilitarioFormatacao.formatarDecimalMoedaReal(saldoVeiculo.getSaldoValor(), 2));
    }

    @Override
    protected boolean aplicarRegraAoVeiculo(Veiculo veiculo) {
        return !veiculo.isProprio();
    }
}

