package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.EmpresaUnidade;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.vo.EmpresaUnidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaEmpresaUnidadeFinanceiroVo;

import java.util.List;

/**
 * Repositório de dados da entidade Empresa/Unidade.
 */
public interface IEmpresaUnidadeDados extends IRepositorioBoleiaDados<EmpresaUnidade> {

    /**
     * Busca uma lista de Empresa/Unidade para ser utilizado na pesquisa do Financeiro.
     *
     * @param filtro Objeto com os filtros da busca.
     * @param usuarioLogado Usuário logado no sistema.
     * @return As Empresas/Unidades encontradas
     */
    List<EmpresaUnidadeVo> obterEmpresasUnidadesParaFinanceiro(FiltroPesquisaEmpresaUnidadeFinanceiroVo filtro, Usuario usuarioLogado);

    /**
     * Busca uma lista de Empresa/Unidade para ser utilizado no detalhamento de ciclo.
     *
     * @param filtro Objeto com os filtros da busca.
     * @param usuarioLogado Usuário logado no sistema.
     * @return As Empresas/Unidades encontradas
     */
    List<EmpresaUnidadeVo> obterEmpresasUnidadesParaDetalhamentoCiclo(FiltroPesquisaEmpresaUnidadeFinanceiroVo filtro, Usuario usuarioLogado);
}
