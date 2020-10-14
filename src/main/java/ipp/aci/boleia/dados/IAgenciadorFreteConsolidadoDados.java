package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;

import java.util.List;

/**
 * Interface responsável pelo acesso aos dados do Consolidado do Agenciador de frete na base
 */
public interface IAgenciadorFreteConsolidadoDados extends IRepositorioBoleiaDados<Consolidado> {

    /**
     * Obtém o consolidado a partir de uma transação
      * @param transacao A transação
     * @return O consolidado obtido
     */
    Consolidado obterPorTransacao(Transacao transacao);

    /**
     * Obtém uma lista de consolidados para gerar uma cobrança
     * @return A lista de consolidados
     */
    List<Consolidado> obterAbertosParaCobranca();

    /**
     * Obtém uma lista de consolidados para gerar um reembolso
     * @return A lista de consolidados
     */
    List<Consolidado> obterAbertosParaReembolsos();
}