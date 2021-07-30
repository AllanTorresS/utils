package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.TipoBeneficio;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades de Tipo Benefício.
 */
@Repository
public class OracleTipoTipoBeneficioDados extends OracleRepositorioBoleiaDados<TipoBeneficio> implements ITipoBeneficioDados {

    /**
     * Instancia o repositório.
     */
    public OracleTipoTipoBeneficioDados() {
        super(TipoBeneficio.class);
    }
}
