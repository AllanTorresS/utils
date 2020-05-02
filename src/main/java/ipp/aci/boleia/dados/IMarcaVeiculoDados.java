package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MarcaVeiculo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades das Marcas dos veiculos.
 */
public interface IMarcaVeiculoDados extends IRepositorioBoleiaDados<MarcaVeiculo> {

     /**
     * Pesquisa marcas por tipo de veiculo mtec
     * @param tipoVeiculoMtec tipo veiculo mtec
     * @return marcas
     */
    List<MarcaVeiculo> pesquisarPorTipoVeiculoMtec(Long tipoVeiculoMtec);
}
