package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.EmpresaUnidadeView;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUnidadeEmpresaAgregadaVo;

/**
 * Repositório de dados da view de Empresa/Unidade.
 */
public interface IEmpresaUnidadeViewDados extends IRepositorioBoleiaDados<EmpresaUnidadeView> {

    /**
     * Pesquisa registros a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return lista de registros
     */
    ResultadoPaginado<EmpresaUnidadeView> pesquisar(FiltroPesquisaUnidadeEmpresaAgregadaVo filtro);
}
