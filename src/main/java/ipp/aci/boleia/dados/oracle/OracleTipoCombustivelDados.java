package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoCombustivelDados;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.enums.ParametroSistema;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Tipo combustivel.
 */
@Repository
public class OracleTipoCombustivelDados extends OracleRepositorioBoleiaDados<TipoCombustivel> implements ITipoCombustivelDados {


	private static final String CONSULTA_COMBUSTIVEIS_SEM_PRECOS_PV =
			" SELECT c " +
			" FROM TipoCombustivel c " +
			" WHERE NOT EXISTS (" +
			" 	SELECT 1 " +
			" 	FROM PrecoBase p " +
			" 	JOIN p.precoMicromercado pm " +
			" 	WHERE p.pontoVenda.id = :idPontoVenda" +
			" 	AND pm.tipoCombustivel.id = c.id " +
			" ) " +
			" ORDER BY c.descricao ";

	private static final String CONSULTA_COMBUSTIVEIS_SEM_PRECOS_INATIVOS_PV =
			" SELECT c " +
					" FROM TipoCombustivel c " +
					" WHERE NOT EXISTS (" +
					" 	SELECT 1 " +
					" 	FROM PrecoBase p " +
					" 	JOIN p.precoMicromercado pm " +
					" 	WHERE p.pontoVenda.id = :idPontoVenda" +
					" 	AND pm.tipoCombustivel.id = c.id " +
					" 	AND p.status NOT IN ( " + StatusAlteracaoPrecoPosto.EXPIRADO.getValue() + " , " + StatusAlteracaoPrecoPosto.RECUSADO.getValue() + " ) " +
					" ) " +
					" ORDER BY c.descricao ";

	private static final String CONSULTA_COMBUSTIVEIS_PERMITIDOS_PARA_O_VEICULO =
			"SELECT DISTINCT tipoCombustivel " +
			" FROM FrotaParametroSistema frotaParametroSistema  " +
			" INNER JOIN frotaParametroSistema.combustiveisPermitidos frotaParamSisProdAbast " +
			" INNER JOIN frotaParamSisProdAbast.tipoCombustivel tipoCombustivel " +
			" INNER JOIN frotaParamSisProdAbast.veiculo veiculo " +
			" WHERE ( " +
			"           veiculo.id = :idVeiculo " +
			"           AND frotaParametroSistema.frota.id = :idFrota " +
			"           AND frotaParametroSistema.ativo = " + Boolean.TRUE +
			"           AND frotaParametroSistema.parametroSistema = " + ParametroSistema.PRODUTO_ABASTECIMENTO.getCodigo() +
			"           AND frotaParamSisProdAbast.permitido = " + Boolean.TRUE +
			"       ) ";



	/**
	 * Instancia o repositorio
	 */
	public OracleTipoCombustivelDados() {
		super(TipoCombustivel.class);
	}

	@Override
	public List<TipoCombustivel> buscarCombustiveisSemPrecoPV(Long idPontoVenda) {
		return pesquisar(null,CONSULTA_COMBUSTIVEIS_SEM_PRECOS_PV, new ParametroPesquisaIgual("idPontoVenda",idPontoVenda)).getRegistros();
	}

	@Override
	public List<TipoCombustivel> buscarCombustiveisSemPrecoNegociacoesAtivasPV(Long idPontoVenda) {
		return pesquisar(null,CONSULTA_COMBUSTIVEIS_SEM_PRECOS_INATIVOS_PV, new ParametroPesquisaIgual("idPontoVenda",idPontoVenda)).getRegistros();
	}

	@Override
	public List<TipoCombustivel> buscarPorTipoCombustivelMtec(String tipoCombustivelMtec) {
        return pesquisar(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("tipoCombustivelMtec", tipoCombustivelMtec));
    }

	@Override
	public TipoCombustivel buscarPorTipoCombustivelConnect(Long tipoCombustivelConnect) {
		return pesquisarUnico(new ParametroPesquisaIgual("codigoConnectCTA", tipoCombustivelConnect));
	}

	@Override
	public List<TipoCombustivel> buscarPorCodigoNcm(Long codigoNcm) {
		return pesquisar(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("codigosNcm.codigoNcm", codigoNcm));
	}

	@Override
	public List<TipoCombustivel> buscarCombustivelPermitidoParaVeiculo(Long idVeiculo, Long idFrota){
		return pesquisar(null, CONSULTA_COMBUSTIVEIS_PERMITIDOS_PARA_O_VEICULO, new ParametroPesquisaIgual("idVeiculo",idVeiculo ), new ParametroPesquisaIgual("idFrota",idFrota )).getRegistros();
	}
}
