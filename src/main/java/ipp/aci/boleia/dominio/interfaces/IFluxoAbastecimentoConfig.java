package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
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

    default Veiculo getVeiculoClimatizador() { return null; }


    /**
     * Verifica se a configuração de fluxo tem a mesma placa do veículo parametrizado.
     *
     * @param veiculo parametrizado
     * @return true se a cofiguração não tem veiculo associado ou tem a mesma placa que o veículo parametrizado.
     */
    default boolean verificaRestricaoPlacaNoFluxo(Veiculo veiculo) {
        return this.getVeiculo() == null || Objects.equals(veiculo.getPlaca(), this.getVeiculo().getPlaca());
    }

    /**
     * Verifica se existe restrição de abastecimento para veículo não climatizador
     * e se a placa informada corresponde à placa esperada.
     * @param veiculo veículo que será abastecido.
     * @return flag indicando se a restrição foi satisfeita caso exista.
     */
    default boolean possuiMesmaPlacaDoFluxo(Veiculo veiculo) {
        return this.getVeiculo() != null && Objects.equals(veiculo.getPlaca(), this.getVeiculo().getPlaca());
    }

    /**
     * Verifica se existe restrição de abastecimento para veículo climatizador
     * e se a placa informada corresponde à placa esperada.
     * @param veiculo climatizador que será abastecido.
     * @return flag indicando se a restrição foi satisfeita caso exista.
     */
    default boolean possuiMesmoClimatizadorDoFluxo(Veiculo veiculo) {
        return this.getVeiculoClimatizador() != null && Objects.equals(veiculo.getPlaca(), this.getVeiculoClimatizador().getPlaca());
    }

    /**
     *  Verifica se é nescessario verificar hodometro/horimetro para o abastecimento em questão
     *
     * @param autorizacaoPagamento abastecimento validado
     * @return true se a configuração de fluxo exige hodometro/horimetro e se o abastecimento tem as informações de hodometro/horimetro
     */
    default boolean deveValidarHodometroHorimetro(AutorizacaoPagamento autorizacaoPagamento) {
        return this.getExigirTelaHodometroHorimetro() || autorizacaoPagamento.temHodometroOuHorimetro();
    }
}
