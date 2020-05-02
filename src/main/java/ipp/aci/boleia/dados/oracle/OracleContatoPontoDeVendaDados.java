package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IContatoPontoDeVendaDados;
import ipp.aci.boleia.dominio.ContatoPontoDeVenda;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * ContatoPontoDeVenda
 */
@Repository
public class OracleContatoPontoDeVendaDados extends OracleRepositorioBoleiaDados<ContatoPontoDeVenda> implements IContatoPontoDeVendaDados {

	private static final String CAMPO_PONTO_DE_VENDA = "pontoVenda";
	private static final String CAMPO_ADICIONAL = "adicional";

	/**
	 * Instancia o repositorio
	 */
	public OracleContatoPontoDeVendaDados() {
		super(ContatoPontoDeVenda.class);
	}

	@Override
	public ContatoPontoDeVenda obterAdicionalPorIdPontoDeVenda(Long idPontoDeVenda) {
		return pesquisarUnico(new ParametroPesquisaIgual(CAMPO_PONTO_DE_VENDA, idPontoDeVenda), new ParametroPesquisaIgual(CAMPO_ADICIONAL, Boolean.TRUE));
	}
}
