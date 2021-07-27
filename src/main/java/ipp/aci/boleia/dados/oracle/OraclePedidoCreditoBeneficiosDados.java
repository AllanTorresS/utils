package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.servicos.IPedidoCreditoBeneficiosDados;
import ipp.aci.boleia.dominio.beneficios.PedidoCreditoBeneficios;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades de PedidoCreditoBeneficios.
 */
@Repository
public class OraclePedidoCreditoBeneficiosDados extends OracleRepositorioBoleiaDados<PedidoCreditoBeneficios> implements IPedidoCreditoBeneficiosDados {

    /**
     * Instancia o repositório.
     */
    public OraclePedidoCreditoBeneficiosDados() {
        super(PedidoCreditoBeneficios.class);
    }
}
