package ipp.aci.boleia.dominio.interfaces;

/**
 * Classe para controle de entidades que possuem exclusao logica
 */
public interface IExclusaoLogica {

    String NOME_CAMPO="excluido";

    /**
     * Retorna true caso a entidade tenha sido exlcuida logicamente
     * @return true caso a entidade tenha sido exlcuida logicamente
     */
    Boolean getExcluido();

    /**
     * Determina se a entidade foi excluida logicamente
     * @param excluido true caso a entidade tenha sido exlcuida logicamente
     */
    void setExcluido(Boolean excluido);

}
