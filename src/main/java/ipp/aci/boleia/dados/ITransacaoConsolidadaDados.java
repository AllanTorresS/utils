package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.AgrupamentoTransacaoConsolidadaPvVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheCicloVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFinanceiroVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoGraficoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.PontosGraficoFinanceiroVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

import java.math.BigDecimal;
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
    TransacaoConsolidada obterConsolidadoParaAbastecimento(Long idAbastecimento);

    /**
     * Obtém um consolidado que corresponda as características de um abastecimento em uma data específica.
     *
     * @param abastecimento O abastecimento
     * @param dataReferencia A data de referência usada na busca.
     * @return O consolidado encontrado.
     */
    TransacaoConsolidada obterConsolidadoParaAbastecimentoEData(AutorizacaoPagamento abastecimento, Date dataReferencia);

    /**
     * Busca um consolidado que atenda um conjunto de características.
     *
     * @param frota Frota da transação consolidada.
     * @param pv Ponto de venda da transação consolidada.
     * @param modalidadePagamento Modalidade de pagamento.
     * @param empresaAgregada Empresa agregada vinculada a transação consolidada.
     * @param unidade Unidade vinculada a transação consolidada.
     * @param dataReferencia Data de referência da transação consolidada.
     * @return A transação consolidada encontrada.
     */
    TransacaoConsolidada obterConsolidado(Frota frota, PontoDeVenda pv, ModalidadePagamento modalidadePagamento, EmpresaAgregada empresaAgregada, Unidade unidade, Date dataReferencia);

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

    /**
     * Obtém a lista de transações abertas que tiveram seu período de abastecimento encerrado
     * e transações em ajuste que tiveram seu período de ajuste encerrado
     * @return lista de transacoes candidatas à atualização de status
     */
    List<TransacaoConsolidada> obterCiclosParaAtualizarStatus();

    /**
     * Pesquisa uma lista de transações consolidadas a partir um pv e um período de data.
     *
     * @param pv O identificador do pv.
     * @param de Data inicial do período.
     * @param ate Data final do período.
     * @return A lista de transações encontradas.
     */
    List<TransacaoConsolidada> pesquisarTransacoesPorPvEData(Long pv, Date de, Date ate);

    /**
     * Pesquisa o total de reembolso dos consolidados baseadd no filtro de pesquisa.
     * @param filtro parâmetros utilizados na consulta.
     * @param usuarioLogado usuario logado que solicita a pesquisa.
     * @return A soma dos reembolsos.
     */
    BigDecimal obterTotalReembolsoPeriodo(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado);

    /**
     * Pesquisa uma lista de transações consolidadas baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesFinanceiro(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado);

    /** Obtém os pontos de datas e valores de pagamento de reembolsos previstos e pagos para o gráfico do financeiro.
     *
     * Nota 1: Somente serão exibidos reembolsos com valores acima de zero.
     * Nota 2: Os reembolsos previstos existentes sao aqueles que tem status EM_ABERTO, ATRASADO ou AGUARDANDO_NF.
     * Nota 3: Há uma diferenciação entre reembolsos previstos existentes e não gerados que é importante para comportar diferentes fases do projeto,
     * onde o reembolso era gerado independente da emissão de notas como também de etapas em que tal emissão era crucial para a sua criação.
     * Neste caso, os reembolsos devem ter sua data de previsão de pagamento de reembolso calculada.
     * @param filtro filtro contendo as informações que devem ser consideradas na consulta.
     * @param usuarioLogado Usuario logado no sistema que fez a requisição.
     * @return um mapa com data e valores de reembolsos previstos.
     */
    List<PontosGraficoFinanceiroVo> obterPontosGraficoReembolsos(FiltroPesquisaReembolsoGraficoVo filtro, Usuario usuarioLogado);

    /**
     * Obtém o número de ciclos com reembolso atrasados para mostrar no banner
     *
     * @param filtro O filtro com as informações que devem ser consideradas na busca.
     * @param usuario Usuario logado que solicita a pesquisa.
     * @return Número de reembolsos atrasados.
     */
    Integer obterNumeroReembolsosAtrasados(FiltroPesquisaFinanceiroVo filtro, Usuario usuario);

    /**
     * Busca a lista dos ciclos atuais agrupados para um pv.
     *
     * @param idPv Identificador do ponto de venda.
     * @return Uma lista com as transações consolidadas agrupadas.
     */
    List<AgrupamentoTransacaoConsolidadaPvVo> pesquisarCiclosAtuaisPorPv(Long idPv);

    /**
     * Busca uma lista com transações consolidadas agrupadas para ponto de venda.
     * @param filtro o filtro com as informações que devem ser consideradas na busca.
     * @return Uma lista com as transações consolidadas agrupadas.
     */
    List<AgrupamentoTransacaoConsolidadaPvVo> pesquisarDetalheCicloParaPv(FiltroPesquisaDetalheCicloVo filtro);

    /**
     * Altera o estado da entidade para desanexado
     * @param transacaoConsolidada transacao consolidada a cobrança a ser desanexada
     * @return a transacao consolidada desanexada
     */
    TransacaoConsolidada desanexar(TransacaoConsolidada transacaoConsolidada);

    /**
     * Obtém a lista de ciclos fechados que nao passaram por postergacao
     *
     * @return lista de ciclos fechados que nao passaram por postergacao
     */
    List<TransacaoConsolidada> obterCiclosFechadosQueNaoPassaramPorPostergacao();

      /**
     * Pesquisa uma lista de transações consolidadas pertencentes a um agrupamento de ciclos.
     *
     * @param filtro o filtro com as informações que devem ser consideradas na busca.
     * @return A lista de transações encontradas.
     */
    List<TransacaoConsolidada> pesquisarTransacoesDeAgrupamento(FiltroPesquisaDetalheCicloVo filtro);

    /**
     * Pesquisa uma lista de transações consolidadas pertencentes a um agrupamento de ciclos ordenada
     *
     * @param filtro o filtro com as informações que devem ser consideradas na busca.
     * @return Lista de transações encontrada.
     */
    ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesDetalhamentoDeCiclo(FiltroPesquisaDetalheCicloVo filtro);

    /**
     * Pesquisa as cobranças ou estimativas de cobrança
     * @param filtro O filtro fornecido
     * @param usuarioLogado O usuário que solicitou a pesquisa
     * @return A lista de transações consolidadas
     */
    ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesFinanceiroFrota(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado);

    /**
     * Obtém o total cobrado de uma frota em um período
     * @param filtro O filtro fornecido
     * @param usuarioLogado O usuário que solicitou a pesquisa
     * @return O valor cobrado
     */
    BigDecimal obterTotalCobrancaPeriodo(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado);
}
