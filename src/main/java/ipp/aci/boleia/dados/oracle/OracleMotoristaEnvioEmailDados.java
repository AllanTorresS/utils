package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotoristaEnvioEmailDados;
import ipp.aci.boleia.dominio.rotinas.MotoristaEnvioEmail;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Motorista
 */
@Repository
public class OracleMotoristaEnvioEmailDados extends OracleRepositorioBoleiaDados<MotoristaEnvioEmail> implements IMotoristaEnvioEmailDados {

    /**
     * Instancia o repositorio
     */
    public OracleMotoristaEnvioEmailDados() {
        super(MotoristaEnvioEmail.class);
    }

}
