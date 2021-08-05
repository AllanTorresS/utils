package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICobrancaXPDados;
import ipp.aci.boleia.dominio.CobrancaXP;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades Cobranca XP
 */
@Repository
public class OracleCobrancaXPDados extends OracleRepositorioBoleiaDados<CobrancaXP> implements ICobrancaXPDados {

	/**
	 * Instancia o repositório
	 */
	public OracleCobrancaXPDados() {
		super(CobrancaXP.class);
	}
}