package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Municipio;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;

import java.util.List;

/**
 * Contrato para implementação de repositórios de entidade Municipio
 */
public interface IMunicipioDados extends IRepositorioBoleiaDados<Municipio> {

    /**
     * Pesquisa municípios pelo nome ou pela sigla da UF
     * @param filtro termo contendo nome ou UF
     * @return lista de municípios
     */
    List<Municipio> pesquisarPorNomeUf(FiltroPesquisaParcialVo filtro);
}
