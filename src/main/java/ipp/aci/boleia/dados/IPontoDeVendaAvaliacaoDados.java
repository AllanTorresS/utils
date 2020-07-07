package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.PontoDeVendaAvaliacao;
import ipp.aci.boleia.dominio.vo.FiltroPontoDeVendaAvaliacaoVo;

/**
 * Contrato para implementacao de repositorios de entidades de Avaliação de Ponto de Venda
 */
public interface IPontoDeVendaAvaliacaoDados extends IRepositorioBoleiaDados<PontoDeVendaAvaliacao>{

    /**
     * Pesquisa uma avaliacao de um ponto de venda dados os parametros do filtro
     * @param filtro Os parametros de busca
     * @return A avaliacao do ponto de venda
     */
    PontoDeVendaAvaliacao pesquisarPorPontoDeVenda(FiltroPontoDeVendaAvaliacaoVo filtro);
}
