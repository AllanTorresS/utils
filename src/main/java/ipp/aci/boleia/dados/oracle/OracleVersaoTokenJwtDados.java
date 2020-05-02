package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IVersaoTokenJwtDados;
import ipp.aci.boleia.dominio.VersaoTokenJwt;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades VersaoTokenJwt
 */
@Repository
public class OracleVersaoTokenJwtDados extends OracleRepositorioBoleiaDados<VersaoTokenJwt> implements IVersaoTokenJwtDados {

	/**
	 * Instancia o repositório
	 */
	public OracleVersaoTokenJwtDados() {
		super(VersaoTokenJwt.class);
	}

	@Override
	public VersaoTokenJwt obterPorTipoToken(TipoTokenJwt tipoTokenJwt) {
		return pesquisarUnico(new ParametroPesquisaIgual("tipo", tipoTokenJwt.name()));
	}
}
