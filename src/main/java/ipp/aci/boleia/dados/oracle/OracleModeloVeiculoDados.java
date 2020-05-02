package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IModeloVeiculoDados;
import ipp.aci.boleia.dominio.ModeloVeiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades dos
 * Modelos de veiculo
 */
@Repository
public class OracleModeloVeiculoDados extends OracleRepositorioBoleiaDados<ModeloVeiculo> implements IModeloVeiculoDados {

	/**
	 * Instancia o repositorio
	 * OracleModeloVeiculoDados
	 */
	public OracleModeloVeiculoDados() {
		super(ModeloVeiculo.class);
	}

	@Override
	public List<ModeloVeiculo> pesquisarPorSubTipoMtecMarca(Long tipoVeiculoMtec, Long idMarca) {
		return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIgual("tipoVeiculoMtec", tipoVeiculoMtec), new ParametroPesquisaIgual("marca",idMarca));
	}

	@Override
	public List<ModeloVeiculo> pesquisarPorTipoVeiculoMtec(Long tipoVeiculoMtec) {
		return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIgual("tipoVeiculoMtec", tipoVeiculoMtec));
	}
}
