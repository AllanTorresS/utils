package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.AutorizacaoPagamentoOrfaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPostoCredenciadoVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades FrotaPontoVenda
 */
public interface IFrotaPontoVendaDados extends IRepositorioBoleiaDados<FrotaPontoVenda> {

	/**
	 * Pesquisa uma relação entre frota e ponto de venda.
	 *
	 * @param filtro O filtro da busca
	 * @return Uma lista de entidades localizadas
	 */
	ResultadoPaginado<FrotaPontoVenda> pesquisarPostosCredenciados(FiltroPesquisaPostoCredenciadoVo filtro);

	/**
	 * Lista as relacoes Frota x Ponto Venda nas quais o Ponto Venda possua relação com o
	 * micromercado informado
	 *
	 * @param idMicromercado O id do micromercado desejado
	 * @return Uma lista de FrotaPontoVenda
	 */
	List<FrotaPontoVenda> buscarPorMicromercado(Long idMicromercado);

	/**
	 * Obtem as entidades FrotaPontoVenda necessarias para insercao
	 * de novos registros
	 *
	 * @return Lista de FrotaPontoVenda sem registro de transacao consolidada
	 */
	List<AutorizacaoPagamentoOrfaVo> obterFrotaPVSemConsolidado();

	/**
	 * Obtem as entidades FrotaPontoVenda através dos códigos do ponto de venda e frota
	 * @param idFrota o ID da frota
	 * @param idPontoVenda o ID do Ponto de Venda
	 * @return Lista de FrotaPontoVenda
	 */
	FrotaPontoVenda obterFrotaPontoVenda(Long idFrota, Long idPontoVenda);

	/**
	 * Obtem todas frotaPontoVenda relacionadas a determinado ponto de venda
	 * @param idPontoVenda Ponto de venda
	 * @return FrotasPV do PV determinado
	 */
    List<FrotaPontoVenda> buscarPorPontoVenda(Long idPontoVenda);

	/**
	 * Armazena uma lista de FrotaPontoVenda
	 * @param listaFrotaPV A lista de frotaPvs a armazenar
	 * @return Uma lista contendo as entidaes armazenadas
	 */
	List<FrotaPontoVenda> armazenarListaFrotaPV(List<FrotaPontoVenda> listaFrotaPV);

	/**
	 * Obtem todas frotaPontoVenda mais atualizadas em relação a uma determinada data
	 * @param dataReferencia a data usada como ponto de referência
	 * @return uma lista contendo as entidades recuperadas pelo critério
	 */
	List<FrotaPontoVenda> buscarPorDataMaisRecente(Date dataReferencia);
}
