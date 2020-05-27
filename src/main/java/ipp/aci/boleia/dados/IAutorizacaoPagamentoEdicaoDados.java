package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAutorizacaoPagamentoEdicaoVo;

/**
 * Contrato para implementacao de repositorios de entidades AutorizacaoPagamentoEdicao
 */
public interface IAutorizacaoPagamentoEdicaoDados extends IRepositorioBoleiaDados<AutorizacaoPagamentoEdicao> {

    /**
     * Obtem os registros que representam o historico de alterações
     * @param filtro filtro que representa as configurações para obter somente historicos de interesse.
     * @return ResultadoPaginado de AutorizacaoPagamentoEdicao
     */
    ResultadoPaginado<AutorizacaoPagamentoEdicao> pesquisar(FiltroPesquisaAutorizacaoPagamentoEdicaoVo filtro);

    /**
     * Obtem o a autorizacao de pagamento edicao
     * @param autorizacaoPagamento a autorizacaoPagamento atual
     * @return a AutorizacaoPagamentoEdicao
     */
    AutorizacaoPagamentoEdicao obterUltimaAutorizacaoPgtoEdicao(AutorizacaoPagamento autorizacaoPagamento);
    /**
     * Retorna a edição pendente de um abastecimento.
     * @param idAbastecimento identificador do abastecimento.
     * @return um objeto do tipo AutorizacaoPagamentoEdicao referente ao abastecimento informado.
     */
    AutorizacaoPagamentoEdicao obterEdicaoPendentePorAbastecimento(Long idAbastecimento);

    /**
     * Retorna a última edição com status REJEITADO para um determinado abastecimento
     * @param idAbastecimento Identificação do abastecimento
     * @return Objeto AutorizacaoPagamentoEdicao
     */
    AutorizacaoPagamentoEdicao obterUltimaAutorizacaoPgtoEdicaoRejeitada(Long idAbastecimento);

    /**
     * Verifica se um abastecimento possui uma edição aprovada.
     * @param id identificador do abastecimento.
     * @return true caso possua uma edição aprovada e false caso contrário.
     */
    boolean verificarSeAbastecimentoPossuiEdicaoEfetuada(Long id);
}
