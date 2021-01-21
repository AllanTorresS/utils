package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo;
import ipp.aci.boleia.dominio.vo.TransacaoPendenteVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.dominio.vo.apco.VolumeVendasClienteProFrotaVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades AutorizacaoPagamento
 */
public interface IAutorizacaoPagamentoDados extends IRepositorioBoleiaDados<AutorizacaoPagamento> {

    /**
     * Obtem as ultimas 30 autorizacoes para dada frota
     * @param idFrota id da frota
     * @return Lista de Autorizacoes
     */
    List<AutorizacaoPagamento> obterUltimos30AbastecimentosFrota(Long idFrota);

    /**
     * Obtem todas transacoes autorizadas para dada frota apos a data parametrizada
     * @param idFrota id da frota
     * @param dataMinima data minima
     * @return Autorizacoes
     */
    List<AutorizacaoPagamento> obterAutorizadasPorFrotaDataMinima(Long idFrota, Date dataMinima);

    /**
     * Pesquisa AutorizacaoPagamento autorizada a partir do codigo de pagamento
     *
     * @param codigoPagamento codigo de Pagamento da transacao
     * @return Uma entidade localizada
     */
    AutorizacaoPagamento obterPorCodigoPagamentoAutorizado(String codigoPagamento);

    /**
     * Obtem transacoes similares dentro da data limite informada
     * @param idMotorista id do motorista
     * @param idVeiculo id do veiculo
     * @param idPv id do PV
     * @param idFrota id do Frota
     * @param valorTotal Valor total da autorizacao de pagamento
     * @param dataLimite Data limite para pesquisa
     *
     * @return AutorizacaoPagamento similar encontrada
     */
    AutorizacaoPagamento obterSimilarAutorizada(Long idMotorista, Long idVeiculo, Long idPv, Long idFrota, BigDecimal valorTotal, Date dataLimite);


    /**
     * Obtem transacoes similares dentro da data limite informada
     * @param cpf informado no abastecimento
     * @param idVeiculo id do veiculo
     * @param idFrota id do Frota
     * @param litragemTotal litragem total da autorizacao de pagamento
     * @param dataLimite Data limite para pesquisa
     *
     * @return AutorizacaoPagamento similar encontrada
     */
    AutorizacaoPagamento obterAbastecimentoSimilarAutorizada(Long cpf, Long idVeiculo, Long idFrota, BigDecimal litragemTotal, Date dataLimite);

    /**
     * Recupera uma lista de notas fiscais (AutorizacaoPagamento) vinculadas a um motorista (e à sua frota).<br>
     *
     * @param idFrota identificador da frota
     * @param idMotorista identificador do motorista
     * @return lista de notas encontradas
     */
    List<AutorizacaoPagamento> obterNotasPorFrotaMotorista(Long idFrota, Long idMotorista);

    /**
     * Obtem o ultimo abastecimento ou estorno autorizado do veiculo
     * @param idVeiculo O id do veiculo
     * @return O ultimo abastecimento
     */
    AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo);

    
    /**
     * Obtem o ultimo abastecimento ou estorno autorizado do veiculo antes de uma certa data
     * @param idVeiculo O id do veiculo
     * @param dataMaxima data cujo o abastecimento precisa ser anterior
     * @return O ultimo abastecimento anterior a data estipulada
     */
    AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo, Date dataMaxima);

    /**
     * Obtem o abastecimento posterior autorizado do veiculo
     * @param autorizacaoPagamento A autorizacaoPagamento
     * @return O abastecimento posterior
     */
    AutorizacaoPagamento obterAutorizacaoPagamentoPosterior(AutorizacaoPagamento autorizacaoPagamento);

    /**
     * Obtem o ultimo abastecimento autorizado do veiculo antes de uma certa data
     * @param idVeiculo O id do veiculo
     * @param dataMaxima data cujo o abastecimento precisa ser anterior
     * @param filtrarPorEstorno determina se estornos autorizados serão incluídos na consulta
     * @return O ultimo abastecimento anterior a data estipulada
     */
    AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo, Date dataMaxima, Boolean filtrarPorEstorno);

    /**
     * Obtem a autorizacao de pagamento anterior
     * @param autorizacaoPagamento A autorizacaoPagamento
     * @return O ultimo abastecimento anterior a data estipulada
     */
    AutorizacaoPagamento obterAutorizacaoPagamentoAnterior(AutorizacaoPagamento autorizacaoPagamento);


    /**
     * Recupera uma AutorizacaoPagamento (nota fiscal) de acordo com os dados do motorista que enviou a solicitação.<br>
     * Apenas o identificador da transacao seria suficiente para a consulta, porém utilizamos Frota e Motorista
     * para verificar se o registro realmente pertence ao motorista.
     *
     * @param idAutorizacaoPagamento identificador da AutorizacaoPagamento
     * @param idFrota identificador da frota
     * @param idMotorista identificador do motorista
     * @return nota fiscal encontrada, ou nulo
     */
    AutorizacaoPagamento obterNotaParaSolicitacaoAjudaMotorista(Long idAutorizacaoPagamento, Long idFrota, Long idMotorista);

    /**
     * Pesquisa AutorizacaoPagamento paginado a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginada(FiltroPesquisaAbastecimentoVo filtro);

    /**
     * Pesquisa AutorizacaoPagamento paginado a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @param fetchCamposExportacao deve incluir dados para exportação
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginada(FiltroPesquisaAbastecimentoVo filtro, Boolean fetchCamposExportacao);

    /**
     * Obtem todas autorizações aguardando saldo da frota para Autorizar
     * @return Autorizações aguardando saldo
     */
    List<Long> obterAguardandoSaldo();

    /**
     * Altera o estado da entidade para desanexado
     *
     * @param entidade entidade transiente
     * @return A entidade desanexada
     */
    AutorizacaoPagamento desanexar(AutorizacaoPagamento entidade);

    /**
     * Recupera uma lista de notas fiscais (AutorizacaoPagamento) vinculadas a um veículo até determinado abastecimento<br>
     *
     * @param idVeiculo identificador do Veiculo
     * @param dataRequisicao data do abastecimento
     * @return lista de notas encontradas
     */
    List<AutorizacaoPagamento> obterUltimasTresNotasPorVeiculoData(Long idVeiculo, Date dataRequisicao);

    /**
     * Obtém todas as cotas dos veículos vinculadas as autorizações da frota
     *
     * @param idFrota identificador da frota do abastecimento
     * @param dataInicial data inicial
     * @param dataFinal data final
     * @return lista de cotas encontradas
     */
    List<QuantidadeAbastecidaVeiculoVo> obterAbastecimentosNoPeriodo(Long idFrota, Date dataInicial, Date dataFinal);

    /**
     * Obtém todas as autorizacoes pendentes de aprovação pela frota, em um determinado periodo de tempo.
     * @param dataExpiracao data da autorização
     * @return lista de autorização
     */
    List<AutorizacaoPagamento> obterAbastecimentosPendenteAutorizacao(Date dataExpiracao);

    /**
     * Obtem os abastecimentos de acordo com o codigo da frota e codigo do ponto de venda,
     * dentro do periodo informado para a consolidacao de notas fiscais.
     *
     * @param codigoFrota Identificador da Frota
     * @param codigoPV Identificador do Ponto de Venda
     * @param empresaAgregada Empresa Agregada
     * @param unidade Unidade
     * @param dataInicioPeriodo Data inicial do periodo
     * @param dataFimPeriodo Data final do periodo
     * @param prePago Se true, busca os abastecimentos que utilizaram credito pre pago. Se false, busca
     *                os que utilizaram o limite de credito da frota. Se nulo, busca todos.
     * @return Lista de itens dos abastecimentos localizados pelos parametros informados
     */
    List<AutorizacaoPagamento> obterAbastecimentosParaNotaFiscal(Long codigoFrota, Long codigoPV, EmpresaAgregada empresaAgregada, Unidade unidade, Date dataInicioPeriodo, Date dataFimPeriodo, Boolean prePago);

    /**
     * Obtem autorização pagamento com o mesmo uuidAbastecimento
     * @param uuidAbastecimento O udid do dispositivo
     * @return AutorizacaoPagamento
     */
    AutorizacaoPagamento obterAutorizacaoPagamentoComMesmoAbastecimento(String uuidAbastecimento);

    /**
     * Método que implementa a pesquisa de autorização pagamento para o contexto da
     * API de frotista.
     *
     * @param filtro Filtro a ser aplicado
     * @return Lista com autorização pagamento.
     */
    ResultadoPaginadoFrtVo<AutorizacaoPagamento> pesquisar(FiltroPesquisaAbastecimentoFrtVo filtro);

    /**
     * Obtem uma lista de autorizacoes pelos seus IDs
     * @param idsAutorizacaoPagamento Os ids
     * @return A lista de autorizacoes encontradas
     */
    List<AutorizacaoPagamento> obterPorIds(List<Long> idsAutorizacaoPagamento);

    /**
     * Obtem todas as autorizacoes de pagamento de uma rede para as quais nao existem notas fiscais associadas
     * @param filtro o filtro de pesquisa informado
     * @return Uma lista de autorizacoes
     */
    List<AutorizacaoPagamento> pesquisarAutorizacoesSemNota(FiltroPesquisaAbastecimentoVo filtro);


    /**
     * Obtém todas as autorizacoes pagamento por uma cobranca
     * @param idCobranca identificador da cobranca
     * @return Uma lista de autorizacoes
     */
    List<AutorizacaoPagamento> obterAutorizacoesPorCobranca(Long idCobranca);

    /**
     * Obtem uma autorizacao com valor negativo referente a uma operação de estorno
     * @param idAutorizacaoPagamentoCancelada O código identificador da autorizacao cancelada
     * @return A autorizacao de estorno
     */
    AutorizacaoPagamento obterEstornoPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada);

    /**
     * Obtem as autorizacoes referente a uma operação de estorno, sejam as negativas e as geradas para correcao
     *
     * @param idAutorizacaoPagamentoCancelada O código identificador da autorizacao cancelada
     * @return As autorizacoes de estorno
     */
    List<AutorizacaoPagamento> obterEstornosPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada);

    /**
     * Obtêm uma lista de abastecimentos cancelados em um determinado período
     *
     * @param codigoFrota o código da frota
     * @param codigoPV o código do ponto de venda
     * @param dataInicioPeriodo data de início do período de processamento
     * @param dataFimPeriodo data de fim do período de processamento
     * @return A lista de abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentosCanceladosNoPeriodo(Long codigoFrota, Long codigoPV, Date dataInicioPeriodo, Date dataFimPeriodo);

    /**
     * Obtêm uma lista de abastecimentos pendentes de autorização similares ao abastecimento atual.
     *
     * @param idAutorizacaoAtual O código da autorização atual.
     * @param idMotorista O código do motorista.
     * @param idVeiculo O código do veículo.
     * @param idPontoVenda O código do ponto de venda.
     * @return Uma lista com as autorizações encontradas, exceto a autorização atual.
     */
    List<AutorizacaoPagamento> obterAbastecimentosPendentesSimilares(Long idAutorizacaoAtual, Long idMotorista, Long idVeiculo, Long idPontoVenda);

    /**
     * Busca uma autorizacao Pagamento baseda no codigo de abastecimento do connect CTA
     * @param cdCTA codigo de abastecimento vindo do CTA
     * @return uma autorizacao pagamento caso exista
     */
    AutorizacaoPagamento buscaPorCdCTA(Long cdCTA);

    /**
     * Obtem um abastecimento pelo numero do pedido
     * @param idPedido id do pedido
     * @return abastecimento pertencente ao pedido fornecido
     */
	AutorizacaoPagamento obterAbastecimentoPorCdPedido(Long idPedido);

    /**
     * Obtem uma lista de abastecimentos que estao autorizados porem com transacao pendente de confirmacao
     * @return abastecimentos encontrados
     */
    List<TransacaoPendenteVo> obterAbastecimentosAutorizadosPendentesDeConciliacao();

    /**
     * Obtem a litragem do abastecimento anterior ao abastecimento parametrizado
     * @param idAbastecimento O id do abastecimento de referência
     * @param dataAbastecimento A data do abastecimento de referência
     * @param placaVeiculo A placa do veiculo do abastecimento de referência
     * @param cnpjFrota O cnpj da frota do veiculo
     * @return litros do abastecimento anterior, caso não encontrado retorna null
     */
    BigDecimal obterLitragemDoAbastecimentoAnterior(Long idAbastecimento, Date dataAbastecimento, String placaVeiculo, Long cnpjFrota);

    /**
     * Busca o estorno negativo associado a uma autorização de pagamento.
     * @param idAutorizacaoPagamento identificador da autorização de pagamento que deve ter o estorno localizado.
     * @return a autorização de pagamento referente a um estorno.
     */
    AutorizacaoPagamento buscarEstornoNegativoAbastecimento(Long idAutorizacaoPagamento);

    /**
     * Obtém abastecimentos com o qual a nota pode ser consolidada
     *
     * @param cnpjDest CPNJ do destinatário da nota
     * @param cnpjEmit CNPJ do emitente da nota
     * @param dataEmissao Data de emissão da nota
     * @param valorTotalNota O valor total da nota
     * @return Os abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentoPorNota(Long cnpjDest, Long cnpjEmit, Date dataEmissao, BigDecimal valorTotalNota);

    /**
     * Busca os objetos que representam as vendas consolidadas de combustiveis dentro de
     * um período de integração entre Profrotas e APCO e realiza o DE-PARA entre os combustiveis.
     *
     * @param dataInicial data inicial do período de exportação.
     * @param  dataFinal data final do período de exportação
     * @return a lista de abastecimentos agrupados do período.
     */
    List<VolumeVendasClienteProFrotaVo> obterVendasProfrotasAPCO(Date dataInicial, Date dataFinal);

    /**
     * Retorna uma lista com os abastecimentos de um ciclo que tem justificativa associada
     *
     * @param idsAutorizacoes Lista que contem os identificadores dos abastecimentos de um determinado ciclo.
     * @return Lista de abastecimentos de um ciclo que tem justificativa associada
     */
    List<AutorizacaoPagamento> obterAbastecimentosComJustificativaAssociadaPorAbastecimentos(List<Long> idsAutorizacoes);

    /**
     * Retorna a lista de abastecimentos de um consolidado que possui exigência de emissão de nota fiscal, sejam eles postergados ou não.
     * @param transacaoConsolidada o consolidado que se deseja obter os abastecimentos.
     * @return a lista de abastecimentos do consolidado.
     */
    List<AutorizacaoPagamento> obterAbastecimentosCicloParaNotaFiscal(TransacaoConsolidada transacaoConsolidada);

    /** Obtém o número abastecimentos pertencentes ao ciclo que foram postergados
     *
     * @param filtro O filtro com as informações que devem ser consideradas na busca.
     * @return Número de abastecimentos postergados.
     */
    Integer obterNumeroAbastecimentosPostergados(FiltroPesquisaAbastecimentoVo filtro);

    /**
     * Obtém o total de abastecimentos realizados por um motorista, dado seu CPF
     *
     * @param cpfMotorista CPF do motorista
     * @return Total de abastecimentos realizados pelo motorista
     */
    Long obterTotalAbastecimentosAutorizadosPorMotorista(Long cpfMotorista);

    /**
     * Obtém a data do último abastecimento realizado por um motorista, dado seu CPF
     *
     * @param cpfMotorista CPF do motorista
     * @return Data do último abastecimento realizado pelo motorista
     */
    Date obterDataUltimoAbastecimentoAutorizadoMotorista(Long cpfMotorista);

    /**
     * Retorna a transacao positiva ajustada oriunda do estorno da transacao original
     * @param transacaoEstornada transacao original, que foi estornada
     * @return transacao positiva ajustada
     */
    AutorizacaoPagamento obterTransacaoAjustadaOriundaDeEstorno(AutorizacaoPagamento transacaoEstornada);

    /**
     * Retorna a transacao negativa oriunda do estorno da transacao original
     * @param transacaoEstornada transacao original, que foi estornada
     * @return transacao negativa
     */
    AutorizacaoPagamento obterTransacaoNegativaOriundaDeEstorno(AutorizacaoPagamento transacaoEstornada);

    /**
     * Busca as autorizações pertencentes a um ciclo
     *
     * @param filtro O filtro da busca
     * @return Uma lista de AutorizacaoPagamento
     */
    List<AutorizacaoPagamento> obterAutorizacoesDoCiclo(FiltroPesquisaAbastecimentoVo filtro);
}
