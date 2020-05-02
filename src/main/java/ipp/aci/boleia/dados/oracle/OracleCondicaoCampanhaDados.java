package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICondicaoCampanhaDados;
import ipp.aci.boleia.dominio.campanha.CondicaoCampanha;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades CondicaoCampanha
 */
@Repository
public class OracleCondicaoCampanhaDados extends OracleRepositorioBoleiaDados<CondicaoCampanha> implements ICondicaoCampanhaDados {
    /**
     * Instancia o repositorio
     */
    public OracleCondicaoCampanhaDados() {
        super(CondicaoCampanha.class);
    }
}
