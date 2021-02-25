package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoErroOperacaoAPCODados;
import ipp.aci.boleia.dominio.historico.HistoricoErrosOperacaoAPCO;
import ipp.aci.boleia.dominio.historico.HistoricoIntegracaoAPCO;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de registro de erros de um lote de uma etapa das integrações Profrotas-APCO.
 */
@Repository
public class OracleHistoricoErroOperacaoAPCODados extends OracleRepositorioBoleiaDados<HistoricoErrosOperacaoAPCO> implements IHistoricoErroOperacaoAPCODados {

	/**
	 * Instancia o repositorio
	 *
	 */
	public OracleHistoricoErroOperacaoAPCODados() {
		super(HistoricoErrosOperacaoAPCO.class);
	}
}
