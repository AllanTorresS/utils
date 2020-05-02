package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ConfiguracaoSistema;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.pdsiframework.dados.IRepositorioGenericoDados;

/**
 * Contrato para implementacao de repositorios de entidades ConfiguracaoSistema
 */
public interface IConfiguracaoSistemaDados extends IRepositorioGenericoDados<ConfiguracaoSistema, String> {

    /**
     * Busca uma configuracao do sistema pela sua chave
     * @param chaveConfiguracao A chave da configuracao
     * @return A configuracao do sistema
     */
    ConfiguracaoSistema buscarConfiguracoes(ChaveConfiguracaoSistema chaveConfiguracao);
}
