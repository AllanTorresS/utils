package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ModuloInterno;

/**
 * Contrato para implementacao de repositorios de entidades ModuloInterno
 */
public interface IModuloInternoDados extends IRepositorioBoleiaDados<ModuloInterno> {

    /**
     * Obtém o módulo interno
     * @param client credencial de id do sistema de integração
     * @param secret secret id do módulo interno
     * @return Módulo Interno
     */
    ModuloInterno obterPorClientESecret(String client, String secret);
}
