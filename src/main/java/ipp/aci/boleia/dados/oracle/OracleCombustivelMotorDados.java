package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICombustivelMotorDados;
import ipp.aci.boleia.dominio.CombustivelMotor;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Respositorio da entidade CombustivelMotor.
 */
@Repository
public class OracleCombustivelMotorDados extends OracleRepositorioBoleiaDados<CombustivelMotor> implements ICombustivelMotorDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleCombustivelMotorDados() {
		super(CombustivelMotor.class);
	}

	@Override
	public CombustivelMotor obterPorDescricao(String descricao) {
		return pesquisarUnico(new ParametroPesquisaIgual("descricao",descricao));
	}

	@Override
	public List<CombustivelMotor> pesquisarPorTipoCombustivelMtec(Collection<String> combustivelMtec) {
		if (CollectionUtils.isNotEmpty(combustivelMtec)) {
			return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIn("tipoCombustivelMtec", combustivelMtec));
		}
		return new ArrayList<>();
	}
}
