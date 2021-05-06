package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IErroIntegracaoTransacaoConectcarDados;
import ipp.aci.boleia.dominio.ErroIntegracaoTransacaoConectcar;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de registro de erros no processamento das Transações da Conectcar.
 */
@Repository
public class OracleErroIntegracaoTransacaoConectcarDados extends OracleRepositorioBoleiaDados<ErroIntegracaoTransacaoConectcar> implements IErroIntegracaoTransacaoConectcarDados {

	/**
	 * Instancia o repositorio
	 *
	 */
	public OracleErroIntegracaoTransacaoConectcarDados() {
		super(ErroIntegracaoTransacaoConectcar.class);
	}
}
