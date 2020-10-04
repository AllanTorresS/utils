package ipp.aci.boleia.dados;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ipp.aci.boleia.dominio.TransacaoConectcar;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUtilizacaoTagVo;

/**
 * Contrato para implementacao de repositorios de consolidações de Transacao Conectcar Consolidada
 */
public interface ITransacaoConectcarDados extends IRepositorioBoleiaDados<TransacaoConectcar> {
    
    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConectcar> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado);

    /**
     * Obtém a lista de transações consolidadas que não possuem nota fiscal emitida e o prazo para emissão da
     * nota está dentro do intervalo de datas fornecido. Ambas as datas são consideradas na consulta.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConectcar> obterConsolidacoesSemNotaFiscalEntreDatas(Date dataIntervaloMin, Date dataIntervaloMax);

    /**
     * Obtém a lista de transações consolidadas que tiveram seu ciclo de abastecimento encerrado
     * no intervalo de datas fornecido.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConectcar> obterConsolidacoesComCicloAbastecimentoEncerrado(Date dataIntervaloMin, Date dataIntervaloMax);

	/**
	 * Obtem uma transacao por um código de transação da conectcar
	 * 
	 * @param codigoTransacaoConectcar Código de transação da conectCar
	 * @return
	 */
	TransacaoConectcar obterTransacoesPorIdConectcar(Long codigoTransacaoConectcar);
	
	/**
	 * Obtem as transações de uma cobrança
	 * 
	 * @param idCobranca
	 * @return
	 */
	List<TransacaoConectcar> obterTransacoesPorCobranca(Long idCobranca);

    /**
     * Obtem as transações consolidadas com ciclo fechado que ainda não geraram cobrança
     *
     * @return Uma lista de transações consolidadas
     */
    List<TransacaoConectcar> obterTransacoesSemCobranca();

    /**
     * Obtem as transacoes consolidadas com ciclo fechado que ainda nao possuem reembolso
     *
     * @return Uma lista de transacoes consolidadas
     */
    List<TransacaoConectcar> obterTransacoesSemReembolso();

    /**
     * Obtem somatorio do valor utilizado da frota no ciclo
     * @param idFrota código identificador da frota
     * @return valor total utilizado
     */
    BigDecimal obterValorUtilizadoCiclo(Long idFrota);
    
    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConectcar> pesquisarUtilizacaoTag(FiltroPesquisaUtilizacaoTagVo filtro);

}