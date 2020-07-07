package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoCondicaoCampanhaDados;
import ipp.aci.boleia.dominio.campanha.TipoCondicaoCampanha;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade {@link ipp.aci.boleia.dominio.campanha.TipoCondicaoCampanha}
 */
@Repository
public class OracleTipoCondicaoCampanhaDados extends OracleRepositorioBoleiaDados<TipoCondicaoCampanha> implements ITipoCondicaoCampanhaDados {

    /**
     * Construtor padrão
     */
    public OracleTipoCondicaoCampanhaDados() {
        super(TipoCondicaoCampanha.class);
    }
}
