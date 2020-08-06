package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAutorizacaoChamadoDados;
import ipp.aci.boleia.dominio.AutorizacaoChamado;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * AutorizacaoChamado
 */
@Repository
public class OracleAutorizacaoChamadoDados extends OracleRepositorioBoleiaDados<AutorizacaoChamado> implements IAutorizacaoChamadoDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleAutorizacaoChamadoDados() {
		super(AutorizacaoChamado.class);
	}
}
