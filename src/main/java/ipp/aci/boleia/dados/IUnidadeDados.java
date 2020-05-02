package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUnidadeVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Unidade
 */
public interface IUnidadeDados extends IRepositorioBoleiaDados<Unidade> {

	/**
	 * Pesquisa unidades a partir do filtro informado
	 * 
	 * @param filtro O filtro da busca
	 * @return A lista de unidade escontradas
	 */
	ResultadoPaginado<Unidade> pesquisar(FiltroPesquisaUnidadeVo filtro);

	/**
	 * Pesquisa unidade por CNPJ
	 *
	 * @param cnpj cnpj da unidade
	 * @return A unidade encontrada
	 */
    Unidade pesquisarPorCnpj(Long cnpj);

    /**
     * Pesquisa unidade por CNPJ na solução
     *
     * @param cnpj cnpj da unidade
     * @return A unuidade encontrada
     */
    List<Unidade> pesquisarPorCnpjSemIsolamento(Long cnpj);

    /**
     * Pesquisa unidades pela razao social, CNPJ e frota.
     *
     * @param termo              O termo de busca
     * @param idFrota            O id da frota
     * @param possuiPostoInterno Informa se as unidades buscadas deverão possuir posto interno
     * @return A lista de unidades encontradas
     */
    List<Unidade> pesquisarPorCnpjRazaoSocial(String termo, Long idFrota, boolean possuiPostoInterno);

	/**
	 * Pesquisa de unidades pelo identificador da frota
	 *
	 * @param idFrota O identificador da frota
	 * @return A lista de unidade escontradas
	 */
	List<Unidade> pesquisarPorFrota(Long idFrota);

	/**
	 * Pesquisa de unidades por nome da unidade e pelo identificador da frota
	 *
	 * @param idFrota O identificador da frota
	 * @param nomeUnidade nome da unidade
	 * @return A lista de unidade escontradas
	 */
	List<Unidade> pesquisarPorNomeEFrota(Long idFrota, String nomeUnidade);

	/**
	 * Pesquisa unidades que possuam posto interno habilitado.
	 *
	 * @param idFrota A frota desejada
	 * @return A lista de unidades encontradas
	 */
	List<Unidade> pesquisarUnidadesComPostoInternoPorFrota(Long idFrota);
}
