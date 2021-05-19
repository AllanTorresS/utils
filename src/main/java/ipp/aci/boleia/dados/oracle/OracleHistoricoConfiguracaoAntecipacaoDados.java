package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoConfiguracaoAntecipacaoDados;
import ipp.aci.boleia.dominio.HistoricoConfiguracaoAntecipacao;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade {@link HistoricoConfiguracaoAntecipacao}
 */
@Repository
public class OracleHistoricoConfiguracaoAntecipacaoDados extends OracleRepositorioBoleiaDados<HistoricoConfiguracaoAntecipacao> implements IHistoricoConfiguracaoAntecipacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoConfiguracaoAntecipacaoDados() {
        super(HistoricoConfiguracaoAntecipacao.class);
    }
}
