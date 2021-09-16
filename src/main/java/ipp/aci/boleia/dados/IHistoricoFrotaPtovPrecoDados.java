package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFrotaPtovPreco;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUltimosPrecosVo;

import java.util.Date;
import java.util.List;

/**
 * Repositório de dados da classe {@link ipp.aci.boleia.dominio.HistoricoPrecoBase}
 */
public interface IHistoricoFrotaPtovPrecoDados extends IRepositorioBoleiaDados<HistoricoFrotaPtovPreco> {


    /**
     * Obtém o preço vigente de um combustível entre uma frota e pv no dia selecionado.
     *
     * @param idFrota id da frota
     * @param idPv id do ponto de venda
     * @param idCombustivel código do combustível
     * @param dataAbastecimento Data em que ocorreu o abastecimento
     * @return Histórico do preço vigente na data ou null caso não encontre
     */
    HistoricoFrotaPtovPreco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPv, Long idCombustivel, Date dataAbastecimento);

    /**
     * Realiza uma pesquisa paginada sobre o histórico de preços acordados entre uma frota e pv
     * @param filtro Filtro de pesquisa
     * @param usuario Usuario usuário logado no sistema
     * @return Os registros de histórico de preço encontrados.
     */
    List<HistoricoFrotaPtovPreco> pesquisar(FiltroPesquisaUltimosPrecosVo filtro, Usuario usuario);

    /**
     * Busca o registro histórico mais recente do preço de um combustível negociado entre frota e pv.
     * @param idFrota id da frota
     * @param idPv id do Ponto de venda.
     * @param idCombustivel código do combustível.
     * @return O registro mais recente encontrado.
     */
    HistoricoFrotaPtovPreco obterUltimoRegistro(Long idFrota, Long idPv, Long idCombustivel);
}
