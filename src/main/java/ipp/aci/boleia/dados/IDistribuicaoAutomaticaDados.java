package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;

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
}
