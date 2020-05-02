package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IParametroCicloDados;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de ParametroCiclo
 */
@Repository
public class OracleParametroCicloDados extends OracleRepositorioBoleiaDados<ParametroCiclo> implements IParametroCicloDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleParametroCicloDados() {
		super(ParametroCiclo.class);
	}

	@Override
	public ParametroCiclo obterParametroCicloDaFrota(Long idFrota) {
		return pesquisarUnico(new ParametroPesquisaIgual("frotas.id", idFrota));
	}

	@Override
	public ParametroCiclo obterPorPrazoCicloPrazoPagamento(Long prazoCiclo, Long prazoPagamento){
		return pesquisarUnico(new ParametroPesquisaIgual("prazoCiclo", prazoCiclo),
				new ParametroPesquisaIgual("prazoPagamento", prazoPagamento));
	}

	@Override
	public ParametroCiclo obterPorIdentificadorPadrao(String identificador) {
		String[] parametrosCiclo = identificador.split("\\+");
		Long prazoCiclo = Long.parseLong(parametrosCiclo[0]);
		Long prazoPagamento = Long.parseLong(parametrosCiclo[1]);

		return obterPorPrazoCicloPrazoPagamento(prazoCiclo, prazoPagamento);
	}
}
