package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IConfiguracaoTipoBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.ConfiguracaoTipoBeneficio;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades ConfiguracaoTipoBeneficio.
 */
@Repository
public class OracleConfiguracaoTipoBeneficioDados extends OracleRepositorioBoleiaDados<ConfiguracaoTipoBeneficio> implements IConfiguracaoTipoBeneficioDados {

    /**
     * Instancia o repositório.
     */
    public OracleConfiguracaoTipoBeneficioDados() {
        super(ConfiguracaoTipoBeneficio.class);
    }
}
