package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoIntegracaoAPCODados;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoAPCO;
import ipp.aci.boleia.dominio.historico.HistoricoIntegracaoAPCO;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de registro de integrações Profrotas-APCO.
 */
@Repository
public class OracleHistoricoIntegracaoAPCODados extends OracleRepositorioBoleiaDados<HistoricoIntegracaoAPCO> implements IHistoricoIntegracaoAPCODados {


	/**
	 * Instancia o repositorio
	 */
	public OracleHistoricoIntegracaoAPCODados() {
		super(HistoricoIntegracaoAPCO.class);
	}

	@Override
	public HistoricoIntegracaoAPCO obterUltimaIntegracao(Date dataAtual) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		InformacaoPaginacao infoPag = new InformacaoPaginacao();
		infoPag.setTamanhoPagina(1);
		infoPag.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataOperacao", Ordenacao.DECRESCENTE));

		parametros.add(new ParametroPesquisaIgual("status", StatusIntegracaoAPCO.SUCESSO.getValue()));
		parametros.add(new ParametroPesquisaDataMenorOuIgual("dataOperacao", dataAtual));
		List<HistoricoIntegracaoAPCO> resultado = pesquisar(infoPag,parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();

		if(CollectionUtils.isNotEmpty(resultado)){
			return resultado.get(0);
		}
		return null;
	}
}
