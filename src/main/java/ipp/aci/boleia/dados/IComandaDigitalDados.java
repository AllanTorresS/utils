package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ComandaDigital;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaComandaVo;

/**
 * Contrato para implementacao de repositorios de entidades Comanda Digital
 */
public interface IComandaDigitalDados extends IRepositorioBoleiaDados<ComandaDigital> {

    /**
     * Pesquisa Comandas a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<ComandaDigital> pesquisar(FiltroPesquisaComandaVo filtro);

    /**
     * Obtem uma comanda atraves do token
     * @param token token gerado
     * @return comanda do token
     */
    ComandaDigital obterPorToken(String token);

    /**
     * Obtem uma comanda atraves do token de autenticacao (JWT)
     * @param tokenJwt token JWT
     * @return comanda do token
     */
    ComandaDigital obterPorTokenAutenticacao(String tokenJwt);

    /**
     * Obtem uma comanda para dado nome e ponto de venda
     * @param nome Nome da comanda
     * @param idPontoVenda id do PV da comanda
     * @return Comanda encontrada
     */
    ComandaDigital obterPorNomePv(String nome, Long idPontoVenda);


    /**
     * Obtem uma comanda para dado nome e unidade
     * @param nome Nome da comanda
     * @param idUnidade id da Unidade da comanda
     * @return Comanda encontrada
     */
    ComandaDigital obterPorNomeUnidade(String nome, Long idUnidade);

    /**
     * Obtem uma comanda para dado nome e frota
     * @param nome Nome da comanda
     * @param idFrota id da Unidade da comanda
     * @return Comanda encontrada
     */
    ComandaDigital obterPorNomeFrota(String nome, Long idFrota);

    /**
     * Exclui todas comandas daquelas unidades
     * @param idsUnidade ids da unidades da comanda
     */
    void excluirPorUnidade(Long... idsUnidade);

    /**
     * Exclui todas comandas daquelas frota
     * @param idsFrota ids da frota
     */
    void excluirPorFrota(Long... idsFrota);

}
