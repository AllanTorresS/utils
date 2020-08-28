package ipp.aci.boleia.dominio.interfaces;

import java.util.Date;

/**
 * Classe para controle de entidades que possuem exclusao logica
 */
public interface IExclusaoLogicaComData {

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

    /**
     * Retorna data de exclusão caso a entidade tenha sido exlcuida logicamente
     * @return data de exclusão caso a entidade tenha sido exlcuida logicamente
     */
    Date getDataExclusao();

    /**
     * Determina a data de exclusão se a entidade foi excluida logicamente
     * @param dataExclusao data de exclusão caso a entidade tenha sido exlcuida logicamente
     */
    void setDataExclusao(Date dataExclusao);

}
