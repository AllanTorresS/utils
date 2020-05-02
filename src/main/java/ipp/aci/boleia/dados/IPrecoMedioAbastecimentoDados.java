package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PrecoMedioAbastecimento;

/**
 * Contrato para implementacao de repositorios de entidades PrecoMedioAbastecimento
 */
public interface IPrecoMedioAbastecimentoDados extends IRepositorioBoleiaDados<PrecoMedioAbastecimento> {

    /**
     * Obtem o ultimo preco medio consolidade por combustivel
     * @param idCombustivel consolidado
     * @return Preco medio
     */
    PrecoMedioAbastecimento pesquisarUltimoPorCombustivel(Long idCombustivel);
}
