package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCotaVeiculoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVeiculoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaVeiculoVo;
import ipp.aci.boleia.dominio.vo.externo.FiltroPesquisaVeiculoExtVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Veiculo
 */
public interface IVeiculoDados extends IRepositorioBoleiaDados<Veiculo> {

	/**
	 * Pesquisa Veiculo a partir do filtro informado
	 *
	 * @param filtro O filtro da busca
	 * @return Uma lista de entidades localizadas
	 */
	ResultadoPaginado<Veiculo> pesquisar(FiltroPesquisaVeiculoVo filtro);

	/**
	 * Pesquisa Veiculo a partir do filtro informado
	 *
	 * @param filtro O filtro da busca
	 * @return Uma lista de entidades localizadas com cota do veiculo
	 */
	ResultadoPaginado<Veiculo> pesquisarCotaVeiculo(FiltroPesquisaCotaVeiculoVo filtro);

	/**
	 * Pesquisa Veículos para a API do frotista a partir do filtro informado
	 * @param filtro informado pelo frotista
	 * @return Lista de veículos localizados
	 */
	ResultadoPaginadoFrtVo<Veiculo> pesquisar(FiltroPesquisaVeiculoFrtVo filtro);

	/**
	 * Pesquisa Veículos para a API do Sistema Externo a partir do filtro informado
	 * @param filtro informado pela api do sistema Externo
	 * @return Lista de veículos localizados
	 */
	ResultadoPaginadoFrtVo<Veiculo> pesquisar(FiltroPesquisaVeiculoExtVo filtro);

	/**
	 * Encontra um veiculo pela placa
	 *
	 * @param placa A placa
	 * @return O veiculo localizado ou nulo
	 */
	Veiculo buscarPorPlaca(String placa);

	/**
	 * Encontra um veiculo pelo identificadorInterno para uma frota
	 *
	 * @param idFrota O id da frota
	 * @param identificadorInterno código do identificador Interno
	 * @return O veiculo localizado ou nulo
	 */
	Veiculo buscarPorVeiculoIdentificadorInterno(Long idFrota,String identificadorInterno);

	/**
	 * Encontra veiculos pela placa em qualquer frota cadastrada.
	 *
	 * @param placa A placa
	 * @return lista com os veiculos localizados
	 */
	List<Veiculo> buscarVeiculosPorPlaca(String placa);

	/**
	 * Encontra veiculos com a mesma placa
	 *
	 * @param filtro o filtro utilizado
	 * @return O veiculo localizado ou nulo
	 */
	ResultadoPaginado<Veiculo> buscarVeiculoComMesmaPlaca(FiltroPesquisaVeiculoVo filtro);

	/**
	 * Encontra um veiculo pela placa e frota
	 *
	 * @param placa A placa
	 * @param idFrota id da frota
	 * @return O veiculo localizado ou nulo
	 */
	Veiculo buscarPorPlacaFrota(String placa, Long idFrota);

	/**
	 * Encontra um veiculo pela placa e frota sem isolamento de dados.
	 *
	 * @param placa A placa
	 * @param idFrota id da frota
	 * @return O veiculo localizado ou nulo
	 */
	Veiculo buscarPorPlacaFrotaSemIsolamento(String placa, Long idFrota);

    /**
     * Desvincula os veiculos das unidades informadas
     *
     * @param unidadeId os id da unidade em questão
     */
    void desvincularUnidades(Long unidadeId);

    /**
     * Obtem os veiculos sem o campo consumo estimado cadastrado
     *
	 * @param idFrota O id da frota
	 * @return A lista de veiculos
     */
    List<Veiculo> obterVeiculosSemConsumoEstimado(Long idFrota);

	/**
	 * Obtem os veículos próprios de uma frota
	 *
	 *  @param idFrota O identidficador da frota
	 *  @return A lista de veiculos
	 */
	List<Veiculo> obterVeiculosPropriosDaFrota(Long idFrota);

	/**
	 * Obtem os veículos agregados de uma frota
	 *
	 *  @param idFrota O identidficador da frota
	 *  @return A lista de veiculos
	 */
	List<Veiculo> obterVeiculosAgregadosDaFrota(Long idFrota);

	/**
	 * Busca veículos da frota juntamente com seu respectivo saldo, se existir
	 *
	 *  @param idFrota Frota da qual se deseja os veículos
	 *  @return A lista de veiculos com seus saldos
	 */
    List<Veiculo> obterVeiculosComSaldo(Long idFrota);

	/**
	 * Obtem todos os veiculos da frota
	 * @param id O identificador da frota
	 * @return Os veiculos encontrados
	 */
	List<Veiculo> obterTodosDaFrota(Long id);

	@Override
    Veiculo obterPorIdentificadorPadrao(String identificador);

	/**
	 * Obtem os veiculos da frota a partir do filtro informado
	 * @param filtro o filtro utilizado
	 * @return lista de veículos que correspondem a busca
	 */
	List<Veiculo> obterPorIdentificadorInterno(FiltroPesquisaParcialVeiculoVo filtro);
}
