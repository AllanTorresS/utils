package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoUnidadeDados;
import ipp.aci.boleia.dominio.historico.HistoricoUnidade;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoUnidade}.
 */
@Repository
public class OracleHistoricoUnidadeDados extends OracleRepositorioBoleiaDados<HistoricoUnidade> implements IHistoricoUnidadeDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoUnidadeDados() {
        super(HistoricoUnidade.class);
    }
}
