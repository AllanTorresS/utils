package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFavoritoRelAbastecimentoDados;
import ipp.aci.boleia.dominio.FavoritoRelAbastecimento;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * Arquivo
 */
@Repository
public class OracleFavoritoRelAbastecimentoDados extends OracleRepositorioBoleiaDados<FavoritoRelAbastecimento> implements IFavoritoRelAbastecimentoDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleFavoritoRelAbastecimentoDados() {
		super(FavoritoRelAbastecimento.class);
	}

	@Override
	public FavoritoRelAbastecimento pesquisarPorUsuario(Long idUsuario) {
		return pesquisarUnico(new ParametroPesquisaIgual("usuario.id", idUsuario));
	}
}
