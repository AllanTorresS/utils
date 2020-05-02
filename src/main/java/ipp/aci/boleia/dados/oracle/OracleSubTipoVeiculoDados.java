package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ISubTipoVeiculoDados;
import ipp.aci.boleia.dominio.SubTipoVeiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Subtipo veículo.
 */
@Repository
public class OracleSubTipoVeiculoDados extends OracleRepositorioBoleiaDados<SubTipoVeiculo> implements ISubTipoVeiculoDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleSubTipoVeiculoDados() {
		super(SubTipoVeiculo.class);
	}

	@Override
	public List<SubTipoVeiculo> pesquisarPorTipo(Long idTipo) {
		return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIgual("tipoVeiculo", idTipo));
	}

	@Override
	public SubTipoVeiculo obterPorDescricao(String descricao) {
		return pesquisarUnico(new ParametroPesquisaIgual("descricao", descricao));
	}
}
