package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.EmpresaUnidade;

import java.util.List;

/**
 * Repositório de dados da entidade Empresa/Unidade.
 */
public interface IEmpresaUnidadeDados extends IRepositorioBoleiaDados<EmpresaUnidade> {

    /**
     * Obtém as empresas/unidades de uma frota
     * @param idFrota O identificador da frota
     * @return As empresas/unidades encontradas
     */
    List<EmpresaUnidade> obterPorFrota(Long idFrota);
}
