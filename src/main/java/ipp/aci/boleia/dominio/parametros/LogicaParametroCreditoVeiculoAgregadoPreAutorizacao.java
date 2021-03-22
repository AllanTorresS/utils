package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.Veiculo;
import org.springframework.stereotype.Component;

/**
 * Extende a funcionalidade da regra de cotas para veículos agregados na pre-autorizacao.
 */
@Component
public class LogicaParametroCreditoVeiculoAgregadoPreAutorizacao extends LogicaParametroCotaVeiculoPreAutorizacaoBase {

    @Override
    protected boolean aplicarRegraAoVeiculo(Veiculo veiculo) {
        return !veiculo.isProprio();
    }
}

