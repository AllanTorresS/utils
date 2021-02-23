package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FluxoAbastecimentoFrotaConfig;

/**
 * Contrato para implementacao de repositorios de entidades FluxoAbastecimentoGlobalConfig
 */
public interface IFluxoAbastecimentoFrotaDados extends IRepositorioBoleiaDados<FluxoAbastecimentoFrotaConfig> {

    /**
     * Obtem a configuração do fluxo de abastecimento da frota
     * @param idFrota id da frota
     * @return FluxoAbastecimentoGlobalConfig com a configuração do fluxo da frota
     */
    FluxoAbastecimentoFrotaConfig obterFluxoPorFrota(Long idFrota);
}
