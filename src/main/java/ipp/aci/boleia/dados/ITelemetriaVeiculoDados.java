package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TelemetriaVeiculo;
import ipp.aci.boleia.dominio.enums.TipoRastreador;

import java.util.Optional;

/**
 * Contrato de implementação de telemetria veículo dados.
 *
 */
public interface ITelemetriaVeiculoDados extends IRepositorioBoleiaDados<TelemetriaVeiculo> {

    /**
     * Obtém registro de telemetria mais recente de uma placa.
     * @param placa Placa que será procurada sem o '-'
     * @return Telemetria obtida
     */
    Optional<TelemetriaVeiculo> obterMaisRecentePorPlaca(String placa);

    /**
     * Obtém registro de telemetria mais recente por tipo de rastreador e código de requisição.
     * @param tipoRastreador O tipo de rastreador utilizado
     * @return Telemetria obtida
     */
    Optional<TelemetriaVeiculo> obterMaisRecentePorTipoRastreadorERequisicao(TipoRastreador tipoRastreador);
}
