package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PostoInternoTipoCombustivelPreco;

/**
 * Contrato para implementacao de repositorios da entidade de PostoInternoTipoCombustivelPreco
 */
public interface IPostoInternoTipoCombustivelPrecoDados extends IRepositorioBoleiaDados<PostoInternoTipoCombustivelPreco> {

    /**
     * Obtem entidade o preço de um tipo combustivel num posto interno pelo ID do posto interno e tipo combustivel
     * @param idFrota  ID do posto interno caso seja um posto interno de Frota
     * @param idUnidade ID do posto interno caso seja um posto interno de Unidade
     * @param idTipoCombustivel ID do tipo de combustivel
     * @return retorna a entidade PostoInternoTipoCombustivelPreco
     */
    PostoInternoTipoCombustivelPreco obterPorPostoInternoTipoCombustivel(Long idFrota, Long idUnidade, Long idTipoCombustivel);

}