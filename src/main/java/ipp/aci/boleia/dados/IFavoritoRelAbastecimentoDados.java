package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.FavoritoRelAbastecimento;

/**
 * Contrato para implementacao de repositorios de entidades dos Campos favoritos do relatório de abastecimento
 */
public interface IFavoritoRelAbastecimentoDados extends IRepositorioBoleiaDados<FavoritoRelAbastecimento> {

    /**
     * Armazena a lista de campos favoritos do relatório de abastecimento
     *
     * @param favorito campos favoritos a serem armazenados
     * @return campos favoritos do relatório de abastecimento
     */
    FavoritoRelAbastecimento armazenar(FavoritoRelAbastecimento favorito);

    /**
     * Pesquisa por usuario os campos favoritos do relatório de abastecimento
     *
     * @param idUsuario id do usuario
     * @return campos favoritos do relatório de abastecimento
     */
    FavoritoRelAbastecimento pesquisarPorUsuario(Long idUsuario);
}
