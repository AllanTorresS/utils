package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.GrupoOperacional;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaGrupoOperacionalVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades GrupoOperacional
 */
public interface IGrupoOperacionalDados extends IRepositorioBoleiaDados<GrupoOperacional> {

    /**
     * Pesquisa Grupo Operacional paginado a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<GrupoOperacional> pesquisaPaginada(FiltroPesquisaGrupoOperacionalVo filtro);

    /**
     * Pesquisa Grupo Operacional a partir do código de centro de custo
     * @param codigo codigo do grupo
     * @return O GrupoOperacional localizado
     */
    GrupoOperacional obterPorCodigo(String codigo);

    /**
     * Pesquisa grupo operacional sem unidade
     * @return Grupos operacionais sem unidade
     */
    List<GrupoOperacional> pesquisarSemUnidade();

    /**
     * Desvincula os grupos operacionais das unidades informadas
     * @param unidadeId os id da unidade em questão
     */
    void desvincularUnidades(Long unidadeId);

    /**
     * Pesquisa um grupo operacional por seu nome ou pela razão social da frota da qual faz parte
     * @param filtro o filtro
     * @return lista de grupos operacionais encontrados
     */
    List<GrupoOperacional> pesquisarPorNomeGrupoRazaoSocialFrota(FiltroPesquisaParcialVo filtro);
}
