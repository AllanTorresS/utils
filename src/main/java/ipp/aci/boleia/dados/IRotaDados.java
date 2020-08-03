package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Rota;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaVo;

/**
 * Contrato para implementacao de repositorios de entidades Rota
 */
public interface IRotaDados extends IRepositorioBoleiaDados<Rota> {

    /**
     * Pesquisa Rotas a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Rota> pesquisar(FiltroPesquisaRotaVo filtro);

    /**
     * Localiza uma rota pelo nome
     * @param nome da rota
     * @param idFrota identificador da frota corrente
     * @return A rota para o nome
     */
    Rota buscarPorNome(String nome, Long idFrota);

    /**
     * Obtem a quantidade de rotas de uma frota que contem um determinado ponto de venda.
     * 
     * @param idFrota o id da frota a qual a rota pertence
     * @param idPontoDeVenda o id do ponto de venda que deve estar contido nas rotas
     * @return a quantidade de rotas encontradas
     */
    Long obterQuantidadeDeRotasQueContemPontoDeVenda(long idFrota, long idPontoDeVenda);

}
