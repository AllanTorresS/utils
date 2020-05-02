package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ModeloVeiculo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades das Modelo dos veiculos.
 */
public interface IModeloVeiculoDados extends IRepositorioBoleiaDados<ModeloVeiculo> {

    /**
     * Pesquisa modelos por id da marca e tipo veiculo mtec
     * @param idMarca id da marca
     * @param tipoVeiculoMtec tipo veiculo
     * @return Modelos
     */
    List<ModeloVeiculo> pesquisarPorSubTipoMtecMarca(Long tipoVeiculoMtec, Long idMarca);

     /**
     * Pesquisa modelos por tipo veiculo mtec
     * @param tipoVeiculoMtec tipo veiculo
     * @return Modelos
     */
    List<ModeloVeiculo> pesquisarPorTipoVeiculoMtec(Long tipoVeiculoMtec);
}
