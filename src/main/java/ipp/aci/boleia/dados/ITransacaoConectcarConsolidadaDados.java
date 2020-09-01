package ipp.aci.boleia.dados;

import java.util.Date;
import java.util.List;

import ipp.aci.boleia.dominio.TransacaoConectcarConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;

/**
 * Contrato para implementacao de repositorios de consolidações de Transacao Conectcar Consolidada
 */
public interface ITransacaoConectcarConsolidadaDados extends IRepositorioBoleiaDados<TransacaoConectcarConsolidada> {
    
    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConectcarConsolidada> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado);

    /**
     * Obtém a lista de transações consolidadas que não possuem nota fiscal emitida e o prazo para emissão da
     * nota está dentro do intervalo de datas fornecido. Ambas as datas são consideradas na consulta.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConectcarConsolidada> obterConsolidacoesSemNotaFiscalEntreDatas(Date dataIntervaloMin, Date dataIntervaloMax);

    /**
     * Obtém a lista de transações consolidadas que tiveram seu ciclo de abastecimento encerrado
     * no intervalo de datas fornecido.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConectcarConsolidada> obterConsolidacoesComCicloAbastecimentoEncerrado(Date dataIntervaloMin, Date dataIntervaloMax);

	/**
	 * Obtem as transações de uma cobrança
	 * 
	 * @param idCobranca
	 * @return
	 */
	List<TransacaoConectcarConsolidada> obterTransacoesPorCobranca(Long idCobranca);

    /**
     * Obtem as transações consolidadas com ciclo fechado que ainda não geraram cobrança
     *
     * @return Uma lista de transações consolidadas
     */
    List<TransacaoConectcarConsolidada> obterTransacoesSemCobranca();

    /**
     * Obtem as transações consolidadas com ciclo aberto com data de fim anteriores a hoje
     *
     * @return Uma lista de transações consolidadas
     */
    List<TransacaoConectcarConsolidada> obterTransacoesAbertasParaFechamento();

}
