package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ModeloAnos;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades dos Anos dos Modelos dos Veiculos
 */
public interface IModeloAnosDados extends IRepositorioBoleiaDados<ModeloAnos> {

    /**
     * Pesquisa anos por  id do modelo
     * @param idModelo  id do modelo
     * @return Anos do modelo
     */
    List<ModeloAnos> pesquisarPorModelo(Long idModelo);
}
