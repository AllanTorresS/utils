package ipp.aci.boleia.dados.oracle;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.amazonaws.util.CollectionUtils;

import ipp.aci.boleia.dados.ITaxaPerfilDados;
import ipp.aci.boleia.dominio.TaxaPerfil;
import ipp.aci.boleia.dominio.enums.PerfilPontoDeVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Respositorio de entidades de TaxaPerfil.
 */
@Repository
public class OracleTaxaPerfilDados extends OracleRepositorioBoleiaDados<TaxaPerfil> implements ITaxaPerfilDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleTaxaPerfilDados() {
		super(TaxaPerfil.class);
	}

	@Override
	public TaxaPerfil obterPorTipo(PerfilPontoDeVenda tipo) {
		List<TaxaPerfil> taxas = pesquisar(new ParametroOrdenacaoColuna("versao", Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("tipo", tipo.ordinal()));
		return !CollectionUtils.isNullOrEmpty(taxas) ? taxas.get(0) : null;
	}
}
