package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICredenciamentoDados;
import ipp.aci.boleia.dominio.Credenciamento;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio da entidade de {@link Credenciamento}.
 */
@Repository
public class OracleCredenciamentoDados extends OracleRepositorioBoleiaDados<Credenciamento> implements ICredenciamentoDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleCredenciamentoDados() {
		super(Credenciamento.class);
	}

	@Override
	public Credenciamento obterPorCnpj(Long cnpj) {
		if(cnpj == null) {
			return null;
		}
		return pesquisarUnico(new ParametroPesquisaIgual("cnpj", cnpj));
	}
}
