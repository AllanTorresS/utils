package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ApiToken;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades ApiToken
 */
public interface IApiTokenDados extends IRepositorioBoleiaDados<ApiToken> {

    /**
     * Método que obtém um api token para uma frota específica.
     *
     * WARNING!!! Este método é utilitzado no contexto de validação do token da
     * api de frotista e não possui isolamento de dados. Por favor, não coloque
     * o isolamento e não utitilize em outro contexto.
     *
     * 
     * @param idFrota Identificador da frota.
     * @return Lista de API token
     */
    List<ApiToken> obterPorFrota(Long idFrota);

}
