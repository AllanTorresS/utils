package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TransacaoConectcar;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.DiaValePedagioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaExtratoValePedagioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUtilizacaoTagVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de consolidações de Transacao Conectcar Consolidada
 */
public interface ITransacaoConectcarDados extends IRepositorioBoleiaDados<TransacaoConectcar> {

    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro        parâmetros utilizados na consulta
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
     * @return Transação da ConnectCar
     */
    TransacaoConectcar obterTransacoesPorIdConectcar(Long codigoTransacaoConectcar);

    /**
     * Obtem as transações de uma cobrança
     *
     * @param idCobranca Identificador da cobrança para listar as transações
     * @return Lista das transações relacionadas à cobrança informada
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
     *
     * @param idFrota código identificador da frota
     * @return valor total utilizado
     */
    BigDecimal obterValorUtilizadoCiclo(Long idFrota);

    /**
     * Obtem o crédito Vale Pedágio da frota
     *
     * @param idFrota código identificador da frota
     * @return crédito Vale Pedágio da frota
     */
    BigDecimal obterCreditoValePedagio(Long idFrota);

    /**
     * Executa a consulta de registros baseado em um filtro de pesquisa
     *
     * @param filtro parâmetros utilizados na consulta
     * @return lista de registros encontrados
     */
    ResultadoPaginado<TransacaoConectcar> pesquisarUtilizacaoTag(FiltroPesquisaUtilizacaoTagVo filtro);

    /**
     * Obtém a última transação de uma frota para o cálculo do ciclo
     *
     * @param idFrota código identificador da frota
     * @return última transacao conectcar de uma frota
     */
    TransacaoConectcar obterUltimaTransacaoPorFrota(Long idFrota);

    /**
     * Obtém a primeira transação de uma frota
     *
     * @param idFrota código identificador da frota
     * @return primeira transacao conectcar de uma frota
     */
    TransacaoConectcar obterPrimeiraTransacaoPorFrota(Long idFrota);

    /**
     * Obtém os vales pedágio que estão ativos para uma frota
     *
     * @param idFrota código identificador da frota
     * @return lista de vales pedágio ativos para a frota
     */
    List<TransacaoConectcar> obterValesPedagioAtivos(Long idFrota);

    /**
     * Obtém os vales pedágio que estão ativos para uma frota
     *
     * @param data data para pesquisa de início de vale pedágio
     * @return lista de vales pedágio iniciando na data
     */
    List<TransacaoConectcar> obterValesPedagioTrocandoStatus();

    /**
     * Obtém os detalhes de um dia de vale pedágio
     *
     * @param filtro parâmetros utilizados na consulta
     * @return lista de transações de vale pedágio do dia
     */
    List<TransacaoConectcar> obterDetalheValePedagio(FiltroPesquisaExtratoValePedagioVo filtro);

    /**
     * Obtém o extrato do vale pedágio
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return extrato do vale pedágio
     */
    ResultadoPaginado<DiaValePedagioVo> obterExtratoValePedagio(FiltroPesquisaExtratoValePedagioVo filtro, Usuario usuarioLogado);

    /**
     * Obtem a primeira transação sem cobrança por frota
     *
     * @param idFrota identificador da frota
     * @return última transação sem cobrança por frota
     */
    TransacaoConectcar obterPrimeiraTransacaoSemCobrancaPorFrota(Long idFrota);
}