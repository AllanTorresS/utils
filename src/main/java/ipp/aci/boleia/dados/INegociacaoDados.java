package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaNegociacaoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Negociacao
 */
public interface INegociacaoDados extends IRepositorioBoleiaDados<Negociacao> {

    /**
     * Pesquisa Negociacao paginada a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<Negociacao> pesquisaPaginada(FiltroPesquisaNegociacaoVo filtro);

    /**
     * Pesquisa Negociacao paginada a partir do filtro informado
     *
     * @param filtro O filtro da busca com usuario logado
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<Negociacao> pesquisaPaginadaValidacaoSegregacao(FiltroPesquisaNegociacaoVo filtro, Usuario usuarioLogado);

    /**
     *  Pesquisa negociacao pela entidade FrotaPontoVenda
     *
     * @param frotaPontoVenda FrotaPontoVenda
     * @return Negociacao localizada
     */
    Negociacao obterPorFrotaPontoVenda(FrotaPontoVenda frotaPontoVenda);

    /**
     *  Pesquisa negociacoes da Frota
     *
     * @param idFrota id da Frota
     * @return Negociacões da frota
     */
    List<Negociacao> obterPorIdFrota(Long idFrota);
}
