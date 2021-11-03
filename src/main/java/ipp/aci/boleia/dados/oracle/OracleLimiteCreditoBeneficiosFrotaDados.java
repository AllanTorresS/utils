package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ILimiteCreditoBeneficiosFrotaDados;
import ipp.aci.boleia.dominio.beneficios.LimiteCreditoBeneficiosFrota;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades de PedidoCreditoBeneficios.
 */
@Repository
public class OracleLimiteCreditoBeneficiosFrotaDados extends OracleRepositorioBoleiaDados<LimiteCreditoBeneficiosFrota> implements ILimiteCreditoBeneficiosFrotaDados {

    /**
     * Instancia o repositório.
     */
    public OracleLimiteCreditoBeneficiosFrotaDados() {
        super(LimiteCreditoBeneficiosFrota.class);
    }
}
