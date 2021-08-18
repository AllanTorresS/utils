package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.Veiculo;
import org.springframework.stereotype.Component;

/**
 * Implementa a logica de verificaca das cotas de abastecimento dos veiculos para a pre-autorizacao.
 */
@Component
public class LogicaParametroCotaVeiculoPreAutorizacao extends LogicaParametroCotaVeiculoPreAutorizacaoBase {

    protected boolean aplicarRegraAoVeiculo(Veiculo veiculo) {
        return veiculo.isProprio();
    }
}
