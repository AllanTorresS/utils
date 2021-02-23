package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades FluxoAbastecimentoMotoristaConfig
 */
public interface IFluxoAbastecimentoMotoristaDados extends IRepositorioBoleiaDados<FluxoAbastecimentoMotoristaConfig> {


    /**
     * Obtem a configuração do fluxo de abastecimento do motorista
     * @param idMotorista id do motorista
     * @return FluxoAbastecimentoFrotaConfig com a configuração do fluxo do motorista
     */
    FluxoAbastecimentoMotoristaConfig obterFluxoPorMotorista(Long idMotorista);

    /**
     * Pesquisa configurações de fluxo abastecimento de motorista com nome similar ao parametrizado
     *
     * @param nomeMotorista nome do motorista para pesquisa
     * @return configurações de fluxo abastecimento de motorista com nome similar
     */
    List<FluxoAbastecimentoMotoristaConfig> pesquisarConfigMotoristaPorNome(String nomeMotorista);

    /**
     * Pesquisa paginada das configurações de fluxo de abastecimento por motorista
     *
     * @param filtro objeto com as inforamções de filtro e paginação
     * @return resultados da pesquisa
     */
    ResultadoPaginado<FluxoAbastecimentoMotoristaConfig> pesquisarConfigMotorista(FiltroPesquisaMotoristaVo filtro);
}
