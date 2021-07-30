package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFrotaPtovPreco;
import java.util.Date;

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
    public HistoricoFrotaPtovPreco obterPorDataFrotaPvCombustivel(Long idFrota, Long idPv, Long idCombustivel, Date dataAbastecimento);
}
