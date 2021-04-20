package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Veiculo;

import java.util.Objects;

/**
 * Interface comum para as configurações de fluxo de abastecimento para frota, motorista e históricos
 */
public interface IFluxoAbastecimentoConfig {

    /**
     * Indica se no fluxo de abastecimento o horimeto/hodometro deve ser exigido
     *
     * @return true para exigir o horimeto/hodometro
     */
    Boolean getExigirTelaHodometroHorimetro();

    /**
     * Indica se no fluxo de abastecimento a foto do horimeto/hodometro deve ser exigida
     *
     * @return true para exigir a foto horimeto/hodometro
     */
    Boolean getExigirFotoHodometroHorimetro();

    /**
     * Indica se no fluxo de abastecimento o leitura do horimeto/hodometro deve ser exigido
     *
     * @return true para exigir o horimeto/hodometro
     */
    Boolean getExigirLeituraAutoHodometroHorimetro();

    /**
     * Indica se no fluxo de abastecimento o posto deve ser exigido
     *
     * @return true para exigir o posto
     */
    Boolean getExigirPosto();

    /**
     * Indica se no fluxo de abastecimento o Combustivel deve ser exigido
     *
     * @return true para exigir a Combustivel
     */
    Boolean getExigirCombustivel();

    /**
     * Indica se no fluxo de abastecimento a litragem deve ser exigida
     *
     * @return true para exigir a litragem
     */
    Boolean getExigirLitragem();

    /**
     * Motorista associado a configuração de fluxo
     *
     * @return motorista da configuração de fluxo
     */
    default Motorista getMotorista() { return null; }

    /**
     * Veiculo associado a configuração de fluxo
     *
     * @return veiculo da configuração de fluxo
     */
    default Veiculo getVeiculo() { return null; }

    /**
     * Verifica se a configuração de fluxo tem a mesma placa do veículo parametrizado.
     *
     * @return true se a cofiguração não tem veiculo associado ou tem a mesma placa que o veículo parametrizado.
     */
    default boolean possuiMesmaPlacaDoFluxo(Veiculo veiculo) {
        return this.getVeiculo() == null || Objects.equals(veiculo.getPlaca(), this.getVeiculo().getPlaca());
    }
}
