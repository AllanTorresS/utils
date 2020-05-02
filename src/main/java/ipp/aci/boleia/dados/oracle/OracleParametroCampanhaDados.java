package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IParametroCampanhaDados;
import ipp.aci.boleia.dominio.campanha.parametros.ParametroCampanha;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades Parâmetro de campanha
 * */
@Repository
public class OracleParametroCampanhaDados extends OracleRepositorioBoleiaDados<ParametroCampanha> implements IParametroCampanhaDados {


	/**
	 * Instancia o repositorio
	 */
	public OracleParametroCampanhaDados() {
		super(ParametroCampanha.class);
	}

}
