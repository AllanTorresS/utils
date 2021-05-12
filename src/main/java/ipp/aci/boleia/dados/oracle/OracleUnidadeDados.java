package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroAutoCompletePostoRotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUnidadeVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de entidades Unidade
 */
@Repository
public class OracleUnidadeDados extends OracleRepositorioBoleiaDados<Unidade> implements IUnidadeDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleUnidadeDados() {
		super(Unidade.class);
	}

	@Override
	public ResultadoPaginado<Unidade> pesquisar(FiltroPesquisaUnidadeVo filtro) {
		if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
			ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
			if (parametro.getNome().contentEquals("telefone")) {
				filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("dddTelefone", parametro.getSentidoOrdenacao()));
			}
		}
		List<ParametroPesquisa> parametros = montarParametrosPesquisa(filtro);
		return pesquisar(filtro.getPaginacao(),
			parametros.toArray(new ParametroPesquisa[parametros.size()])
		);
	}

	/**
     * Monta uma lista com os parametros de pesquisa de unidade.
	 *
	 * @param filtro Filtro de pesquisa de unidade.
     * @return A lista com os parametros a serem aplicados na consulta.
	 */
	private List<ParametroPesquisa> montarParametrosPesquisa(FiltroPesquisaUnidadeVo filtro) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		if(filtro.getNome() != null){
			parametros.add(new ParametroPesquisaLike("nome", filtro.getNome()));
		}
		if(filtro.getFrota()!=null && filtro.getFrota().getId()!=null){
			parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
		}
		return parametros;
	}

	@Override
	public Unidade pesquisarPorCnpj(Long cnpj) {
		return pesquisarUnico(new ParametroPesquisaIgual("cnpj", cnpj));
	}

	@Override
    public List<Unidade> pesquisarPorCnpjSemIsolamento(Long cnpj) {
		return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("cnpj", cnpj));
	}

	@Override
	public List<Unidade> pesquisarPorCnpjRazaoSocial(String termo, Long idFrota, boolean possuiPostoInterno){
		List<ParametroPesquisa> parametros = new ArrayList<>();
		String termoCnpj = preparaTermoCnpj(termo);
		parametros.add(new ParametroPesquisaOr(
				new ParametroPesquisaLike("cnpj", termoCnpj),
				new ParametroPesquisaLike("nome", termo)));
		if (possuiPostoInterno) {
            parametros.add(new ParametroPesquisaIgual("postoInterno", possuiPostoInterno));
		}
		if(idFrota != null){
			parametros.add(new ParametroPesquisaIgual("frota.id", idFrota));
		}
		return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public List<Unidade> pesquisarPorFrota(Long idFrota) {
		return pesquisar(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIgual("frota.id", idFrota));
	}

	@Override
	public List<Unidade> pesquisarPorNomeEFrota(Long idFrota, String nomeUnidade) {
		return pesquisar(new ParametroOrdenacaoColuna("id"),
                new ParametroPesquisaIgual("frota.id", idFrota),
				new ParametroPesquisaIgualIgnoreCase("nome", nomeUnidade));
	}

	@Override
	public List<Unidade> pesquisarUnidadesComPostoInternoPorFrota(Long idFrota){
		return pesquisar(new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaIgual("frota.id", idFrota),
				new ParametroPesquisaIgual("postoInterno", 1));
	}

	@Override
	public List<Unidade> pesquisarParaAutocompleteRota(FiltroAutoCompletePostoRotaVo filtro){
		List<ParametroPesquisa> parametros = new ArrayList<>();

		parametros.add(new ParametroPesquisaIgual("postoInterno",true));
		parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getIdFrota()));
		parametros.add(new ParametroPesquisaLike("nome", filtro.getTermo()));

		return pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}
}