package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;

/**
 * Repositório de dados da entidade ContaBeneficiario.
 *
 * @author pedro.silva
 */
public interface IContaBeneficiarioDados extends IRepositorioBoleiaDados<ContaBeneficiario> {

    /**
     * Retorna a conta ativa de um beneficiário.
     *
     * @param idBeneficiario Identificador do beneficiário.
     * @return A conta encontrada.
     */
    ContaBeneficiario obterAtivaPorBeneficiario(Long idBeneficiario);
}
