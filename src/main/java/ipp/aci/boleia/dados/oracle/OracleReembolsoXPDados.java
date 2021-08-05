package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoXPDados;
import ipp.aci.boleia.dominio.ReembolsoXP;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades Reembolso XP
 */
@Repository
public class OracleReembolsoXPDados extends OracleRepositorioBoleiaDados<ReembolsoXP> implements IReembolsoXPDados {

	/**
	 * Instancia o repositório
	 */
	public OracleReembolsoXPDados() {
		super(ReembolsoXP.class);
	}
}