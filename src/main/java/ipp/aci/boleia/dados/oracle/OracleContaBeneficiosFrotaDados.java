package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IContaBeneficiosFrotaDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiosFrota;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades de PedidoCreditoBeneficios.
 */
@Repository
public class OracleContaBeneficiosFrotaDados extends OracleRepositorioBoleiaDados<ContaBeneficiosFrota> implements IContaBeneficiosFrotaDados {

    /**
     * Instancia o repositório.
     */
    public OracleContaBeneficiosFrotaDados() {
        super(ContaBeneficiosFrota.class);
    }
}
