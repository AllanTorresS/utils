package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMarcaVeiculoDados;
import ipp.aci.boleia.dados.IModeloVeiculoDados;
import ipp.aci.boleia.dominio.MarcaVeiculo;
import ipp.aci.boleia.dominio.ModeloVeiculo;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Respositorio de entidades MarcaVeiculo
 */
@Repository
public class OracleMarcaVeiculoDados extends OracleRepositorioBoleiaDados<MarcaVeiculo> implements IMarcaVeiculoDados {

	@Autowired
	private IModeloVeiculoDados repositorioModeloVeiculoDados;
	
	/**
	 * Instancia o repositorio
	 * OracleMarcaVeiculoDados
	 */
	public OracleMarcaVeiculoDados() {
		super(MarcaVeiculo.class);
	}

	@Override
	public List<MarcaVeiculo> pesquisarPorTipoVeiculoMtec(Long tipoVeiculoMtec) {
		List<ModeloVeiculo> modelos = repositorioModeloVeiculoDados.pesquisarPorTipoVeiculoMtec(tipoVeiculoMtec);
		if (CollectionUtils.isNotEmpty(modelos)) {
			Set<Long> marcasId = new HashSet<>();
			for (ModeloVeiculo modelo : modelos) {
				marcasId.add(modelo.getMarca().getId());
			}
			return pesquisar(new ParametroOrdenacaoColuna("descricao"), new ParametroPesquisaIn("id", marcasId));
		}
		return new ArrayList<>();
	}
}
