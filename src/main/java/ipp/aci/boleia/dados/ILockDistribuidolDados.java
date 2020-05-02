package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.LockDistribuido;

/**
 * Contrato para implementacao de repositorios de lock distribuido
 */
public interface ILockDistribuidolDados extends IRepositorioBoleiaDados<LockDistribuido> {

    /**
     * Obtem um registro de lock distribuido pelo seu nome
     *
     * @param nome O nome do lock
     * @return O lock
     */
    LockDistribuido buscarPorNome(String nome);
}