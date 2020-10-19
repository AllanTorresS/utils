package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Nota Fiscal
 */

@Repository
public class OracleNotaFiscalDados extends OracleRepositorioBoleiaDados<NotaFiscal> implements INotaFiscalDados {

	private static final String QUERY_NOTAS_VARIOS_ABASTECIMENTOS = "" +
		" SELECT distinct(n) FROM NotaFiscal n " +
		" JOIN n.autorizacoesPagamento a " +
		" where a.id in (:idsAutorizacoes) " +
		" and (:isJustificativa IS NULL OR n.isJustificativa = :isJustificativa)";

	/**
	 * Instancia o repositorio
	 */
	public OracleNotaFiscalDados() {
		super(NotaFiscal.class);
	}

	@Override
	public List<NotaFiscal> obterNotaDeVariosAbastecimentos(List<Long> idsAutorizacoes) {
		if(CollectionUtils.isNotEmpty(idsAutorizacoes)) {
			return pesquisar(null, QUERY_NOTAS_VARIOS_ABASTECIMENTOS,
					new ParametroPesquisaIgual("idsAutorizacoes", idsAutorizacoes),
					new ParametroPesquisaIgual("isJustificativa", false)).getRegistros();
		}
		return new ArrayList<>();
	}

	@Override
	public List<NotaFiscal> obterNotaPorNumero(String numero) {
		return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("numero", numero));
	}

	@Override
	public List<NotaFiscal> obterNotasEJustificativasPorAbastecimentos(List<Long> idsAutorizacoes) {
		if(CollectionUtils.isNotEmpty(idsAutorizacoes)) {
			return pesquisar(null, QUERY_NOTAS_VARIOS_ABASTECIMENTOS,
					new ParametroPesquisaIgual("idsAutorizacoes", idsAutorizacoes),
					new ParametroPesquisaIgual("isJustificativa", null)).getRegistros();
		}
		return new ArrayList<>();
	}
}
