package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;

import java.util.List;

/**
 * Repositório de dados da entidade DistribuicaoAutomatica.
 */
public interface IDistribuicaoAutomaticaDados extends IRepositorioBoleiaDados<DistribuicaoAutomatica> {

    /**
     * Obtem a distribuição automática associada a um beneficiário
     * @param idBeneficiario O id do beneficiário
     * @return A distribuição automática
     */
    DistribuicaoAutomatica obterPorIdBeneficiario(Long idBeneficiario);

    /**
     * Obtém todas as distribuições automáticas ativas de um certo dia.
     * @param dia Dia da distribuição.
     * @return Distribuições automáticas do dia.
     */
    List<DistribuicaoAutomatica> obterDistribuicoesPorDia(Integer dia);
}
