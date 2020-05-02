package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IModeloAnosDados;
import ipp.aci.boleia.dominio.ModeloAnos;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades
 * dos Anos de Modelos de veiculo
 */
@Repository
public class OracleModeloAnosDados extends OracleRepositorioBoleiaDados<ModeloAnos> implements IModeloAnosDados {

	/**
	 * Instancia o repositorio
	 * OracleModeloAnosDados
	 */
	public OracleModeloAnosDados() {
		super(ModeloAnos.class);
	}

	@Override
	public List<ModeloAnos> pesquisarPorModelo(Long idModelo) {
		return pesquisar(new ParametroOrdenacaoColuna("anoFabricacao"), new ParametroPesquisaIgual("modelo",idModelo));
	}
}
