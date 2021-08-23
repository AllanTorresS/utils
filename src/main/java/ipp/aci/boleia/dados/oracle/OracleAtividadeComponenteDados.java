package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAtividadeComponenteDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidade de {@link AtividadeComponente}
 */
@Repository
public class OracleAtividadeComponenteDados extends OracleRepositorioBoleiaDados<AtividadeComponente> implements IAtividadeComponenteDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleAtividadeComponenteDados() {
		super(AtividadeComponente.class);
	}
}
