package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dominio.ConfiguracaoSistema;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.pdsiframework.dados.oracle.OracleRepositorioGenericoDados;
import org.springframework.stereotype.Repository;

/**
 * Respositorio para obtencao de configurações do sistema
 */
@Repository
public class OracleConfiguracaoSistemaDados extends OracleRepositorioGenericoDados<ConfiguracaoSistema, String> implements IConfiguracaoSistemaDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleConfiguracaoSistemaDados() {
		super(ConfiguracaoSistema.class);
	}

	@Override
	public ConfiguracaoSistema buscarConfiguracoes(ChaveConfiguracaoSistema chaveconfiguracao) {
		return obterPorIdComRelacionamentos(chaveconfiguracao.name());
	}
}
