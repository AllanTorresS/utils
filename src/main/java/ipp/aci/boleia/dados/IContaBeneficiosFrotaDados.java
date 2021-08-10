package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;

/**
 * Contrato para implementação de repositórios de entidades {@link ContaBeneficiosFrota}.
 */
public interface IContaBeneficiosFrotaDados extends IRepositorioBoleiaDados<ContaBeneficiosFrota>{

    /**
     * Obtém a conta de benefícios de uma frota.
     *
     * @param idFrota Identificador da frota.
     * @return A conta de benefícios da frota.
     */
    ContaBeneficiosFrota obterPorFrota(Long idFrota);
}
