package ipp.aci.boleia.dados.oracle;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IAtividadeComponenteDados;
import ipp.aci.boleia.dominio.AtividadeComponente;

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
