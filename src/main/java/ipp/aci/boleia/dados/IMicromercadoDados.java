package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Micromercado;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Micromercado
 */
public interface IMicromercadoDados extends IRepositorioBoleiaDados<Micromercado> {

    /**
     * Obtem o micromercado pela sua chave
     * @param chave A chave do micromercado
     * @return Um micromercado, caso localizado. Nulo caso contrário.
     */
    Micromercado obterPorChave(String chave);

    /**
     * Obtém os micromercados por chave.
     * @param chave Termo sobre chave de Micromercado
     * @return Lista de micromercados.
     */
    List<Micromercado> pesquisar(String chave);
}
