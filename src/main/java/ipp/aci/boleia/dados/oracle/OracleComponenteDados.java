package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IComponenteDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import ipp.aci.boleia.dominio.Componente;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de Componente de PontoDeVenda
 */
@Repository
public class OracleComponenteDados extends OracleRepositorioBoleiaDados<Componente> implements IComponenteDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleComponenteDados() {
		super(Componente.class);
	}

	@Override
	public Componente buscarPorCnpjPv(Long cnpj, Long idPv) {
		return pesquisarUnico(
				new ParametroPesquisaIgual("codigoPessoa", cnpj),
				new ParametroPesquisaIgual("pontoDeVenda.id", idPv),
				new ParametroPesquisaIn("atividadeComponente.codigoCorporativo", AtividadeComponente.obterCodigosAreaAbastecimento())
		);
	}

    @Override
    public Componente buscarPorCnpj(Long cnpj) {
		InformacaoPaginacao informacaoPaginacao = new InformacaoPaginacao(1, 1);
		return pesquisar(informacaoPaginacao,
				new ParametroPesquisaIgual("codigoPessoa", cnpj),
				new ParametroPesquisaIn("atividadeComponente.codigoCorporativo", AtividadeComponente.obterCodigosAreaAbastecimento()))
				.getRegistros().stream().findFirst().orElse(null);
    }
}
