package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AjusteCobranca;

import java.util.List;

/**
 * Repositório de dados da entidade {@link AjusteCobranca}. *
 */
public interface IAjusteCobrancaDados extends IRepositorioBoleiaDados<AjusteCobranca> {

    /**
     * Obtém os ajustes de valor de uma cobrança
     * @param idCobranca O identificador de uma cobrança
     * @return Os ajustes de valor da cobrança
     */
    List<AjusteCobranca> obterAjustesDeValorPorCobranca(Long idCobranca);
}
