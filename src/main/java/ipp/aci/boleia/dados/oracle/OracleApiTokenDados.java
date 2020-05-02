package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IApiTokenDados;
import ipp.aci.boleia.dominio.ApiToken;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * ApiToken
 */
@Repository
public class OracleApiTokenDados extends OracleRepositorioBoleiaDados<ApiToken> implements IApiTokenDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleApiTokenDados() {
		super(ApiToken.class);
	}

	@Override
	public List<ApiToken> obterPorFrota(Long idFrota) {
		return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("contingencia"), new ParametroPesquisaIgual("frota.id", idFrota));
	}
}
