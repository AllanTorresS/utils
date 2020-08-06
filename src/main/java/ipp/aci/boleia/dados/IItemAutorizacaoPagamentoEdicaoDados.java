package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamentoEdicao;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ItemAutorizacaoPagamentoEdicao
 */
public interface IItemAutorizacaoPagamentoEdicaoDados extends IRepositorioBoleiaDados<ItemAutorizacaoPagamentoEdicao> {

    /**
     * Obtem os itens da transacao indicada pelo codigo fornecido
     *
     * @param autorizacaoPagamentoEdicao A AutorizacaoPagamento Edicao
     * @return Lista de itens (Produtos e Servicos) da autorizacao de pagamento Edicao
     */
    List<ItemAutorizacaoPagamentoEdicao> obterItensEdicaoPorAutorizacaoEdicao(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao);

}
