package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.vo.PrecoItemVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ItemAutorizacaoPagamento
 */
public interface IItemAutorizacaoPagamentoDados extends IRepositorioBoleiaDados<ItemAutorizacaoPagamento> {

    /**
     * Obtem os itens das transacoes de acordo com o codigo da frota e codigo do ponto de venda,
     * dentro do periodo informado para a consolidacao.
     *
     * @param codigoFrota Identificador da Frota
     * @param codigoPV Identificador do Ponto de Venda
     * @param codigoEmpresaAgregada Identificador da Empresa Agregada
     * @param dataInicioPeriodo Data inicial do periodo
     * @param dataFimPeriodo Data final do periodo
     * @param prePago Se TRUE, retorna os itens que foram pagos com creditos pre-pagos adquiridos pela frota.
     *                Se FALSE, retorna os itens que foram pagos com o limite de credito.
     * @return Lista de itens das transacoes localizadas pelos parametros informados
     */
    List<ItemAutorizacaoPagamento> obterItensParaConsolidacao(Long codigoFrota, Long codigoPV, Long codigoEmpresaAgregada, Date dataInicioPeriodo, Date dataFimPeriodo, boolean prePago);


    /**
     * Obtem os itens da transacao indicada pelo codigo fornecido
     *
     * @param idAutorizacao Identificador da transacao
     * @return Lista de itens (Produtos e Servicos) da autorizacao de pagamento
     */
    List<ItemAutorizacaoPagamento> obterItensPorIdAutorizacao(Long idAutorizacao);

    /**
     * Altera o estado da entidade para desanexado
     *
     * @param entidade entidade transiente
     * @return A entidade desanexada
     */
    ItemAutorizacaoPagamento desanexar(ItemAutorizacaoPagamento entidade);

    /**
     * Obtem os itens autorizados para uma data frota com o preco maximo apos a data parametrizada
     * @param idFrota id da frota
     * @param dataMinima data minima a obter itens
     * @return Itens com preco maximo
     */
    List<PrecoItemVo> obterItensPrecoMaximoAutorizadosPorFrotaData(Long idFrota, Date dataMinima);

    /**
     * Obtém os itens autorizados por uma data mínima
     * @param dataMinima data
     * @return itens autorizados
     */
    List<ItemAutorizacaoPagamento> obterItensAutorizadosPorDataMinima(Date dataMinima);
}
