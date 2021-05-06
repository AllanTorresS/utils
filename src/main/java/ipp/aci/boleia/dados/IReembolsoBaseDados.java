package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ReembolsoBase;

/**
 * Interface base para o repositório de dados de um Reembolso.
 *
 * @param <T> Tipo de reembolso do repositório
 */
public interface IReembolsoBaseDados<T extends ReembolsoBase> extends IRepositorioBoleiaDados<T> {

}
