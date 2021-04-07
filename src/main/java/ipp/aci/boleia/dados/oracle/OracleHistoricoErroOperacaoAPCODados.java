package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoErroOperacaoAPCODados;
import ipp.aci.boleia.dominio.historico.HistoricoErrosOperacaoAPCO;
import org.springframework.stereotype.Repository;

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
