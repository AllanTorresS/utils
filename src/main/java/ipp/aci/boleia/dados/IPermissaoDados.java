package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Permissao;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Permissao
 */
public interface IPermissaoDados extends IRepositorioBoleiaDados<Permissao> {

    /**
     * Obtem as permissoes existentes para os portais informados
     * @param portalFrotista true caso portal da frota
     * @param portalRevendedor true caso portal do revendedor
     * @param portalSolucao true caso portal solucao
     * @param portalPrecos true caso portal de precos
     * @param sistemaExterno true caso sistema externo
     * @param moduloInterno true caso modulo interno
     * @return uma lista de permissoes encontradas
     */
    List<Permissao> obterPermissoesPortais(Boolean portalFrotista, Boolean portalRevendedor, Boolean portalSolucao, Boolean portalPrecos, Boolean sistemaExterno, Boolean moduloInterno);

    /**
     * Obtem as permissoes existentes para a frota
     * @return uma lista de permissoes de frota
     */
    List<Permissao> obterPermissoesFrota();

}
