package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMelhoriaAvaliacaoDados;
import ipp.aci.boleia.dominio.MelhoriaAvaliacao;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades MelhoriaAvaliacao
 */
@Repository
public class OracleMelhoriaAvaliacaoDados extends OracleRepositorioBoleiaDados<MelhoriaAvaliacao> implements IMelhoriaAvaliacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleMelhoriaAvaliacaoDados() {
        super(MelhoriaAvaliacao.class);
    }

}