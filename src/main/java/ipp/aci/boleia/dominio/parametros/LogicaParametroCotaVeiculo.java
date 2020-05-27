package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica de verificaca das cotas de abastecimento dos veiculos
 */
@Component
public class LogicaParametroCotaVeiculo extends BaseLogicaParametroSaldoVeiculo {

    @Autowired
    private Mensagens mensagens;

    @Override
    protected String obterMensagemRegraViolada(Veiculo veiculo, boolean cotaEmLitros, BigDecimal montante) {
        return mensagens.obterMensagem("parametro.sistema.erro.abastecimento.cota.veiculo", veiculo.getPlaca(),
            cotaEmLitros ? UtilitarioFormatacao.formatarLitros(montante) : UtilitarioFormatacao.formatarDecimalMoedaReal(montante, 2));
    }

    @Override
    protected boolean aplicarRegraAoVeiculo(Veiculo veiculo) {
        return veiculo.isProprio();
    }
}
