package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroAbastecimentoAntecipavelVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheCobrancaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheReembolsoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaQtdTransacoesFrotaVo;
import ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo;
import ipp.aci.boleia.dominio.vo.TransacaoPendenteVo;
import ipp.aci.boleia.dominio.vo.ValorAntecipavelPorDataVo;
import ipp.aci.boleia.dominio.vo.apco.InformacoesVolumeVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

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
     * Pesquisa autoriza????o de pagamento autorizada a partir do c??digo de pagamento e placa.
     * @param codigoPagamento c??digo de pagamento da transa????o.
     * @param placa a placa do ve??culo a ser abastecido.
     * @return a autoriza????o de abastecimento localizada caso exista.
     */
    AutorizacaoPagamento obterPorCodigoPagamentoAutorizadoEPlaca(String codigoPagamento, String placa);


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
     * Recupera uma lista de notas fiscais (AutorizacaoPagamento) vinculadas a um motorista (e ?? sua frota).<br>
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
     * @param filtrarPorEstorno determina se estornos autorizados ser??o inclu??dos na consulta
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
     * Recupera uma AutorizacaoPagamento (nota fiscal) de acordo com os dados do motorista que enviou a solicita????o.<br>
     * Apenas o identificador da transacao seria suficiente para a consulta, por??m utilizamos Frota e Motorista
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
     * @param fetchCamposExportacao deve incluir dados para exporta????o
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginada(FiltroPesquisaAbastecimentoVo filtro, Boolean fetchCamposExportacao);

    /**
     * Obtem todas autoriza????es aguardando saldo da frota para Autorizar
     * @return Autoriza????es aguardando saldo
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
     * Recupera uma lista de notas fiscais (AutorizacaoPagamento) vinculadas a um ve??culo at?? determinado abastecimento<br>
     *
     * @param idVeiculo identificador do Veiculo
     * @param dataRequisicao data do abastecimento
     * @return lista de notas encontradas
     */
    List<AutorizacaoPagamento> obterUltimasTresNotasPorVeiculoData(Long idVeiculo, Date dataRequisicao);

    /**
     * Obt??m todas as cotas dos ve??culos vinculadas as autoriza????es da frota
     *
     * @param idFrota identificador da frota do abastecimento
     * @param dataInicial data inicial
     * @param dataFinal data final
     * @return lista de cotas encontradas
     */
    List<QuantidadeAbastecidaVeiculoVo> obterAbastecimentosNoPeriodo(Long idFrota, Date dataInicial, Date dataFinal);

    /**
     * Obt??m todas as autorizacoes pendentes de aprova????o pela frota, em um determinado periodo de tempo.
     * @param dataExpiracao data da autoriza????o
     * @return lista de autoriza????o
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
     * Obtem autoriza????o pagamento com o mesmo uuidAbastecimento
     * @param uuidAbastecimento O udid do dispositivo
     * @return AutorizacaoPagamento
     */
    AutorizacaoPagamento obterAutorizacaoPagamentoComMesmoAbastecimento(String uuidAbastecimento);

    /**
     * Busca UUIDs de Abastecimento a partir de uma lista de UUIDs
     * @param uuidsAbastecimento UUIDs que devem ser buscados
     * @return lista de UUIDs encontrados
     */
    List<String> obterUuidsExistentes(List<String> uuidsAbastecimento);

    /**
     * M??todo que implementa a pesquisa de autoriza????o pagamento para o contexto da
     * API de frotista.
     *
     * @param filtro Filtro a ser aplicado
     * @return Lista com autoriza????o pagamento.
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
     * Obt??m todas as autorizacoes pagamento por uma cobranca
     * @param idCobranca identificador da cobranca
     * @return Uma lista de autorizacoes
     */
    List<AutorizacaoPagamento> obterAutorizacoesPorCobranca(Long idCobranca);

    /**
     * Obtem uma autorizacao com valor negativo referente a uma opera????o de estorno
     * @param idAutorizacaoPagamentoCancelada O c??digo identificador da autorizacao cancelada
     * @return A autorizacao de estorno
     */
    AutorizacaoPagamento obterEstornoPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada);

    /**
     * Obtem as autorizacoes referente a uma opera????o de estorno, sejam as negativas e as geradas para correcao
     *
     * @param idAutorizacaoPagamentoCancelada O c??digo identificador da autorizacao cancelada
     * @return As autorizacoes de estorno
     */
    List<AutorizacaoPagamento> obterEstornosPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada);

    /**
     * Obt??m uma lista de abastecimentos cancelados em um determinado per??odo
     *
     * @param codigoFrota o c??digo da frota
     * @param codigoPV o c??digo do ponto de venda
     * @param dataInicioPeriodo data de in??cio do per??odo de processamento
     * @param dataFimPeriodo data de fim do per??odo de processamento
     * @return A lista de abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentosCanceladosNoPeriodo(Long codigoFrota, Long codigoPV, Date dataInicioPeriodo, Date dataFimPeriodo);

    /**
     * Obt??m uma lista de abastecimentos pendentes de autoriza????o similares ao abastecimento atual.
     *
     * @param idAutorizacaoAtual O c??digo da autoriza????o atual.
     * @param idMotorista O c??digo do motorista.
     * @param idVeiculo O c??digo do ve??culo.
     * @param idPontoVenda O c??digo do ponto de venda.
     * @return Uma lista com as autoriza????es encontradas, exceto a autoriza????o atual.
     */
    List<AutorizacaoPagamento> obterAbastecimentosPendentesSimilares(Long idAutorizacaoAtual, Long idMotorista, Long idVeiculo, Long idPontoVenda);

    /**
     * Busca uma autorizacao Pagamento baseda no codigo de abastecimento do connect CTA
     * @param cdCTA codigo de abastecimento vindo do CTA
     * @return uma autorizacao pagamento caso exista
     */
    AutorizacaoPagamento buscaPorCdCTA(Long cdCTA);

    /**
     * Obt??m um abastecimento POS pelo n??mero do pedido
     * @param idPedido id do pedido
     * @return abastecimento POS associado ao pedido fornecido
     */
	AutorizacaoPagamento obterAbastecimentoPosPorPedido(Long idPedido);

    /**
     * Obtem uma lista de abastecimentos que estao autorizados porem com transacao pendente de confirmacao
     * @return abastecimentos encontrados
     */
    List<TransacaoPendenteVo> obterAbastecimentosAutorizadosPendentesDeConciliacao();

    /**
     * Busca o estorno negativo associado a uma autoriza????o de pagamento.
     * @param idAutorizacaoPagamento identificador da autoriza????o de pagamento que deve ter o estorno localizado.
     * @return a autoriza????o de pagamento referente a um estorno.
     */
    AutorizacaoPagamento buscarEstornoNegativoAbastecimento(Long idAutorizacaoPagamento);

    /**
     * Obt??m abastecimentos com o qual a nota pode ser consolidada
     *
     * @param cnpjEmit CPNJ do emitente da nota
     * @param dataEmissao Data de emiss??o da nota
     * @param valorTotalNota O valor total da nota
     * @return Os abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentoPorNota(Long cnpjEmit, Date dataEmissao, BigDecimal valorTotalNota);

    /**
     * Busca os objetos que representam as informa????es necess??rias para obter
     * vendas consolidadas de combustiveis dentro de um per??odo de integra????o entre Profrotas e APCO,
     * realizando o DE-PARA entre os combustiveis.
     *
     * @param dataInicial data inicial do per??odo de exporta????o.
     * @param  dataFinal data final do per??odo de exporta????o
     * @return a lista de informa????es de volumes agrupados do per??odo.
     */
     List<InformacoesVolumeVo> obterInformacoesVendasProfrotasAPCO(Date dataInicial, Date dataFinal);

    /**
     * Retorna uma lista com os abastecimentos de um ciclo que tem justificativa associada
     *
     * @param idsAutorizacoes Lista que contem os identificadores dos abastecimentos de um determinado ciclo.
     * @return Lista de abastecimentos de um ciclo que tem justificativa associada
     */
    List<AutorizacaoPagamento> obterAbastecimentosComJustificativaAssociadaPorAbastecimentos(List<Long> idsAutorizacoes);

    /**
     * Retorna a lista de abastecimentos de um consolidado que possui exig??ncia de emiss??o de nota fiscal, sejam eles postergados ou n??o.
     * @param transacaoConsolidada o consolidado que se deseja obter os abastecimentos.
     * @return a lista de abastecimentos do consolidado.
     */
    List<AutorizacaoPagamento> obterAbastecimentosCicloParaNotaFiscal(TransacaoConsolidada transacaoConsolidada);

    /** Obt??m o n??mero abastecimentos pertencentes ao ciclo que foram postergados
     *
     * @param filtro O filtro com as informa????es que devem ser consideradas na busca.
     * @return N??mero de abastecimentos postergados.
     */
    Integer obterNumeroAbastecimentosPostergados(FiltroPesquisaAbastecimentoVo filtro);

    /**
     * Obt??m o total de abastecimentos realizados por um motorista, dado seu CPF
     *
     * @param cpfMotorista CPF do motorista
     * @return Total de abastecimentos realizados pelo motorista
     */
    Long obterTotalAbastecimentosAutorizadosPorMotorista(Long cpfMotorista);

    /**
     * Obt??m a data do ??ltimo abastecimento realizado por um motorista, dado seu CPF
     *
     * @param cpfMotorista CPF do motorista
     * @return Data do ??ltimo abastecimento realizado pelo motorista
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
     * Busca as autoriza????es pertencentes a um ciclo
     *
     * @param filtro O filtro da busca
     * @return Uma lista de AutorizacaoPagamento
     */
    List<AutorizacaoPagamento> obterAutorizacoesDoCiclo(FiltroPesquisaAbastecimentoVo filtro);

    /**
     * Retorna os abastecimentos associados ??s transa????es consolidadas
     * @param idsTransacoesConsolidadas IDs das transa????es consolidadas
     * @return abastecimentos associados ??s transa????es consolidadas
     */
    List<AutorizacaoPagamento> obterPorTransacoesConsolidadas(List<Long> idsTransacoesConsolidadas);

     /**
     * Pesquisa AutorizacaoPagamento paginado a partir do filtro informado para a tela de Detalhe de Cobran??a
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginadaDetalheCobranca(FiltroPesquisaDetalheCobrancaVo filtro);

    /**
     * Pesquisa uma lista de autoriza????es de pagamento para o detalhe de reembolso.
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginadaDetalheReembolso(FiltroPesquisaDetalheReembolsoVo filtro);

    /**
     * Obtem a quantidade de notas fiscais associadas a transa????es pertencentes a um ciclo
     * ou agrupamento de ciclos
     * @param filtro o filtro de pesquisa informado
     * @return A quantidade de notas fiscais
     */
    Long obterQuantidadeNotasAgrupamento(FiltroPesquisaQtdTransacoesFrotaVo filtro);

    /**
     * Realiza pesquisa sem pagina????o para obten????o de todos os abastecimentos do ciclo
     * filtrados de acordo com par??metros de pesquisa
     * @param filtro O filtro fornecido
     * @return Os registros obtidos
     */
    List<AutorizacaoPagamento> pesquisaDetalheCobrancaSemPaginacao(FiltroPesquisaDetalheCobrancaVo filtro);

    /**
     * Busca abastecimentos pelo valor de combust??vel da Autoriza????o de Pagamento
     * @param cnpjEmit CNPJ do emitente da nota a ser conciliada
     * @param dataEmissao Data de emiss??o da nota a ser conciliada
     * @param valorTotalNota O valor total da nota
     * @return Os abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentoParaConciliacaoPorValorDeCombustivel(Long cnpjEmit, Date dataEmissao, BigDecimal valorTotalNota);

    /**
     * Busca abastecimentos pelo valor de produtos da Autoriza????o de Pagamento
     * @param cnpjEmit CNPJ do emitente da nota a ser conciliada
     * @param dataEmissao Data de emiss??o da nota a ser conciliada
     * @param valorTotalNota O valor total da nota
     * @return Os abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterAbastecimentoParaConciliacaoPorValorDeProduto(Long cnpjEmit, Date dataEmissao, BigDecimal valorTotalNota);

    /**
     * Obt??m abastecimentos de um motorista
     *
     * @param motorista o motorista
     * @return Os abastecimentos encontrados
     */
    List<AutorizacaoPagamento> obterPorMotorista(Motorista motorista);

    /**
     * Obtem o ultimo abastecimento ou estorno autorizado do veiculo com valor de Hodometro diferente de nulo
     * @param idVeiculo O id do veiculo
     * @return O ultimo abastecimento
     */
    AutorizacaoPagamento obterUltimoAbastecimentoVeiculoHodometroValido(Long idVeiculo);

    /**
     * Obtem o ultimo abastecimento ou estorno autorizado do veiculo com valor de Horimetro diferente de nulo
     * @param idVeiculo O id do veiculo
     * @return O ultimo abastecimento
     */
    AutorizacaoPagamento obterUltimoAbastecimentoVeiculoHorimetroValido(Long idVeiculo);

    /**
     * Busca abastecimentos que podem ser reembolsados antecipadamente
     * @param filtro o filtro a ser utilizado na busca
     * @return lista de abastecimentos que podem ser antecipados
     */
    ResultadoPaginado<AutorizacaoPagamento> obterAbastecimentosAntecipaveis(FiltroAbastecimentoAntecipavelVo filtro);

    /**
     * Obt??m o ??ltimo abastecimento do motorista com um ve??culo ativo e n??o exclu??do
     * @param idMotorista O identificador do motorista
     * @return O abastecimento encontrado
     */
    AutorizacaoPagamento obterUltimoAbastecimentoMotorista(Long idMotorista);

    /**
     * Obt??m os valores dispon??veis para antecipa????o para o usu??rio informado
     * @param idUsuario o id do usu??rio
     * @return lista de valores antecip??veis por data de vencimento
     */
    List<ValorAntecipavelPorDataVo> obterValoresDisponiveisAntecipacao(Long idUsuario);
}
