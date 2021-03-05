package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades FluxoAbastecimentoMotoristaConfig
 */
public interface IFluxoAbastecimentoMotoristaDados extends IRepositorioBoleiaDados<FluxoAbastecimentoMotoristaConfig> {

    /**
     * Obtem a configuração do fluxo de abastecimento do motorista
     *
     * @param motorista motorista parametrizado
     * @return FluxoAbastecimentoFrotaConfig com a configuração do fluxo do motorista
     */
    FluxoAbastecimentoMotoristaConfig obterFluxoPorMotorista(Motorista motorista);

    /**
     * Obtém a configurações de Fluxo Abastecimento dos motoristas de deternminado usuário
     *
     * @param usuario para obtér as configurações de fluxo
     * @return registros das configurações do fluxo abastecimento dos motoristas de usuario
     */
    List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorUsuario(Usuario usuario);

    /**
     * Obtém registros de configuração de fluxo abastecimento de motorista associados a determinado veículo
     *
     * @param idVeiculo identificador do veiculo
     * @return registros de configuração de fluxo abastecimento de motorista
     */
    List<FluxoAbastecimentoMotoristaConfig> obterFluxosMotoristaPorVeiculo(Long idVeiculo);

    /**
     * Obtem as configurações do fluxo de abastecimento dos motoristas de uma frota
     *
     * @param idFrota identificador da frota
     * @return registros das configurações do fluxo abastecimento dos motoristas de determinada frota
     */
    List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorFrota(Long idFrota);

    /**
     * Pesquisa paginada das configurações de fluxo de abastecimento por motorista
     *
     * @param filtro objeto com as inforamções de filtro e paginação
     * @return resultados da pesquisa
     */
    ResultadoPaginado<FluxoAbastecimentoMotoristaConfig> pesquisarConfigMotorista(FiltroPesquisaMotoristaVo filtro);
}
