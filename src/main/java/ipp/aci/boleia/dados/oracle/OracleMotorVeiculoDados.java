package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotorVeiculoDados;
import ipp.aci.boleia.dominio.MotorVeiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * Motor de veiculo
 */
@Repository
public class OracleMotorVeiculoDados extends OracleRepositorioBoleiaDados<MotorVeiculo> implements IMotorVeiculoDados {

	/**
	 * Instancia o repositorio
	 * OracleMotorVeiculoDados
	 */
	public OracleMotorVeiculoDados() {
		super(MotorVeiculo.class);
	}

	@Override
	public List<MotorVeiculo> pesquisarPorModelo(Long idModelo) {
		return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIgual("modelo",idModelo));
	}

	@Override
	public MotorVeiculo pesquisarPorModeloDescricao(Long idModelo, String descricao) {
		List<MotorVeiculo> motores = pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("modelo", idModelo), new ParametroPesquisaIgual("descricao", descricao));
		if(!CollectionUtils.isEmpty(motores)) {
			return motores.get(0);
		}
		return null;
	}
}
