package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoVeiculoDados;
import ipp.aci.boleia.dominio.TipoVeiculo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Tipo veículo.
 */
@Repository
public class OracleTipoVeiculoDados extends OracleRepositorioBoleiaDados<TipoVeiculo> implements ITipoVeiculoDados {

	private static final String CONSULTA_NUM_VEICULOS_POR_TIPO =
		" select t " +
		" from TipoVeiculo t" +
		" where exists ( " +
		" 	select 1 " +
		" 	from Veiculo v " +
		" 		join v.subtipoVeiculo s " +
		" 		join s.tipoVeiculo t2 " +
		" 	where " +
		"		t2.id = t.id " +
		"		and v.frota.id = :idFrota "	+
		"		and v.excluido = false " +
		" ) " +
		" order by t.descricao ";

	/**
	 * Instancia o repositorio
	 */
	public OracleTipoVeiculoDados() {
		super(TipoVeiculo.class);
	}

	@Override
	public List<TipoVeiculo> obterTodosPorFrota(Long idFrota) {
		return pesquisar(null, CONSULTA_NUM_VEICULOS_POR_TIPO, new ParametroPesquisaIgual("idFrota", idFrota)).getRegistros();
	}

}
