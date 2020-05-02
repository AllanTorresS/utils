package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaEmpresaAgregadaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Empresa Agregada
 */
public interface IEmpresaAgregadaDados extends IRepositorioBoleiaDados<EmpresaAgregada> {

    /**
     * Pesquisa registros a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return lista de registros
     */
    ResultadoPaginado<EmpresaAgregada> pesquisar(FiltroPesquisaEmpresaAgregadaVo filtro);

    /**
     * Pesquisa empresa agregada por CNPJ
     *
     * @param cnpj cnpj da empresa agregada
     * @return lista de registros
     */
    EmpresaAgregada pesquisarPorCnpj(Long cnpj);

    /**
     * Pesquisa empresa agregada por CNPJ e frota
     *
     * @param cnpj    cnpj da empresa agregada
     * @param idFrota Identificador da frota
     * @return lista de registros
     */
    List<EmpresaAgregada> pesquisarPorCnpjEFrota(Long cnpj, Long idFrota);

    /**
     * Pesquisa empresas agregadas sem unidade
     * @return Empresas agregadas sem unidade
     */
    List<EmpresaAgregada> pesquisarSemUnidade();

    /**
     * Pesquisa empresas agregadas por frota sem a unidade
     * @param idFrota o ID da frota
     * @return Empresas agregadas da frota cadastrada
     */
    List<EmpresaAgregada> pesquisarPorFrotaSemUnidade(Long idFrota);

    /**
     * Pesquisa empresas agregadas por frota e unidade
     * @param idFrota o ID da frota
     * @param idUnidade o ID da unidade
     * @return Empresas agregadas da frota cadastrada por unidade selecionada
     */
    List<EmpresaAgregada> pesquisarPorFrotaComUnidade(Long idFrota, Long idUnidade);

    /**
     * Pesquisa empresas agregadas por razão social
     * @param razaoSocial a razão social da empresa
     * @return Empresas agregadas da frota cadastrada
     */
    List<EmpresaAgregada> pesquisarPorRazaoSocial(String razaoSocial);

    /**
     * Pesquisa empresas agregadas para autocomplete a partir do cnpj
     *
     * @param termo O termo a ser buscado
     * @param idFrota o id frota
     * @return empresas agregadas encontradas
     */
    List<EmpresaAgregada> pesquisarPorCnpjRazaoSocialFrota(String termo, Long idFrota);

    /**
     * Obtém uma lista de empresas agregadas que possuem vínculo com uma unidade.
     *
     * @param idUnidade Identificador da unidade
     * @return empresas agregadas vinculadas
     */
    List<EmpresaAgregada> obterPorUnidade(Long idUnidade);

    /**
     * Desvincula as empresas agregadas das unidades informadas.
     *
     * @param idUnidade os id da unidade em questão
     */
    void desvincularUnidades(Long idUnidade);
}
