package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de consolidações de Transacao Consolidada
 */
public interface ITransacaoConsolidadaDados extends IRepositorioBoleiaDados<TransacaoConsolidada> {

    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConsolidada> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado);

    /**
     * Obtém todas as transacoes candidatas a consolidacao (aquelas que ainda estao em aberto)
     *
     * @return Transacoes candidatas ao processo de consolidacao
     */
    List<TransacaoConsolidada> obterTransacoesParaConsolidacao();

    /**
     * Obtém a lista de transações consolidadas que não possuem nota fiscal emitida e o prazo para emissão da
     * nota está dentro do intervalo de datas fornecido. Ambas as datas são consideradas na consulta.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConsolidada> obterConsolidacoesSemNotaFiscalEntreDatas(Date dataIntervaloMin, Date dataIntervaloMax);

    /**
     * Obtém a lista de transações consolidadas que tiveram seu ciclo de abastecimento encerrado
     * no intervalo de datas fornecido.
     *
     * @param dataIntervaloMin data inicial do intervalo
     * @param dataIntervaloMax data final do intervalo
     * @return lista de transações consolidadas
     */
    List<TransacaoConsolidada> obterConsolidacoesComCicloAbastecimentoEncerrado(Date dataIntervaloMin, Date dataIntervaloMax);

    /**
     * Obtem as ultimas consolidacoes pre e pos pagas encerrada para a frota, ptov em relacao a data informados
     * @param idFrota A frota
     * @param idPontoVenda O pv
     * @param data A data
     * @return Uma lista contendo de zero a duas consolidacoes
     */
    List<TransacaoConsolidada> obterUltimasTransacoesPorFrotaPtovData(Long idFrota, Long idPontoVenda, Date data);

    /**
     * Obtem as transações consolidadas com ciclo fechado que ainda não geraram cobrança
     *
     * @return Uma lista de transações consolidadas
     */
    List<TransacaoConsolidada> obterTransacoesSemCobranca();

    /**
     * Obtem as transações consolidadas com ciclo aberto com data de fim anteriores a hoje
     *
     * @return Uma lista de transações consolidadas
     */
    List<TransacaoConsolidada> obterTransacoesAbertasParaFechamento();

    /**
     * Obtem as transacoes consolidadas com ciclo fechado que ainda nao possuem reembolso
     *
     * @return Uma lista de transacoes consolidadas
     */
    List<TransacaoConsolidada> obterTransacoesSemReembolso();

    /**
     * Obtem as transacoes consolidadas referentes à reembolsos pendentes
     *
     * @param filtro O filtro de pesquisa
     * @return Uma lista de transacoes consolidadas com reembolso pendente
     */
    ResultadoPaginado<TransacaoConsolidada> pesquisarReembolsosPendentes(FiltroPesquisaReembolsoVo filtro);

    /**
     * Obtem o consolidado ao qual esta vinculado um abastecimento
     *
     * @param idAbastecimento O abastecimento
     * @return A transação consolidada
     */
    TransacaoConsolidada obterConsolidadoPorAbastecimento(Long idAbastecimento);

    /**
     * Método que realiza a pesquisa de transações consolidadas para Notas Fiscais no contexto da API
     * do Frotista.
     * 
     * @param filtro O filtro
     * @return O resultado paginado
     */
	ResultadoPaginadoFrtVo<TransacaoConsolidada> pesquisar(FiltroPesquisaNotaFiscalFrtVo filtro);

    /**
     * Obtêm a última transação consolidada fechada que preceda a data de processamento de um abastecimento órfão. <br>
     * Este método pode ser útil para identificar algum possível ciclo com datas que se sobreponham.
     *
     * @param idFrota O código da frota
     * @param idPtov O código do ponto de venda
     * @param idEmpresaAgregada O código da empresa agregada
     * @param idUnidade O código da unidade
     * @param dataInicio A data de inicio do ciclo
     * @param dataAbastecimentoSemCiclo A data do abastecimento que está sem ciclo
     * @return A transação consolidada
     */
    TransacaoConsolidada obterUltimoConsolidadoAnteriorAoAbastecimentoSemCiclo(Long idFrota, Long idPtov, Long idEmpresaAgregada, Long idUnidade, Date dataInicio, Date dataAbastecimentoSemCiclo);

    /**
     * Obtém os ciclos em aberto de uma frota.
     *
     * @param idFrota identificador da frota.
     * @return lista de transacções consolidadas em aberta da frota informada.
     */
    List<TransacaoConsolidada> buscarCiclosEmAberto(Long idFrota);
}
