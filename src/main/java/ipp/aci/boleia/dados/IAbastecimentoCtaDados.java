package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AbastecimentoCta;

/**
 * Contrato para implementacao de repositorios de entidades AbastecimentoCta
 */
public interface IAbastecimentoCtaDados extends IRepositorioBoleiaDados<AbastecimentoCta> {

    /**
     * Busca abastecimento CTA por seu Id no Ipirtanga Connect CTA
     *
     * @param idCTA Id CTA
     * @return Abastecimento com o ID informado
     */
    AbastecimentoCta buscarPorIdCTA(Long idCTA);
}
