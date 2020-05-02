package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoMelhoriaDados;
import ipp.aci.boleia.dominio.TipoMelhoria;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Tipo melhoria.
 */
@Repository
public class OracleTipoMelhoriaDados extends OracleRepositorioBoleiaDados<TipoMelhoria> implements ITipoMelhoriaDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleTipoMelhoriaDados() {
		super(TipoMelhoria.class);
	}

	@Override
	public List<TipoMelhoria> obterPorIds(List<Long> idsTipoMelhoria) {
		return pesquisar((ParametroOrdenacaoColuna) null,new ParametroPesquisaIn("id",idsTipoMelhoria));
	}

}
