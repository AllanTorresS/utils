package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.GrupoEconomico;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades GrupoEconomico
 */
public interface IGrupoEconomicoDados extends IRepositorioBoleiaDados<GrupoEconomico> {

    /**
     * Pesquisar grupos economicos pelo termo dentro do nome
     *
     * @param filtro filtro contém o termo a ser procurado no nome do grupo econômico
     * @return lista de grupos econômicos localizados
     */
    List<GrupoEconomico> pesquisarPorNome(FiltroPesquisaParcialVo filtro);
}
