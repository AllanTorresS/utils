package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.SubTipoVeiculo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades das Subtipo dos veiculos.
 */
public interface ISubTipoVeiculoDados extends IRepositorioBoleiaDados<SubTipoVeiculo> {

    /**
     * Pesquisa subtipos por tipo
     * @param idTipo id do tipo
     * @return subtipos do tipo
     */
    List<SubTipoVeiculo> pesquisarPorTipo(Long idTipo);

    /**
     * Obtem um subtipos pela descricao descricao
     * @param descricao descricao do subtipo
     * @return O subtipo encontrado
     */
    SubTipoVeiculo obterPorDescricao(String descricao);
}
