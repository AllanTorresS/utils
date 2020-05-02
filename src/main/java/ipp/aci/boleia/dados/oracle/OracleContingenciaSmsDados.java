package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IContingenciaSmsDados;
import ipp.aci.boleia.dominio.ContingenciaSms;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades ContingenciaSms
 */
@Repository
public class OracleContingenciaSmsDados extends OracleRepositorioBoleiaDados<ContingenciaSms> implements IContingenciaSmsDados {

    /**
     * Instancia o repositorio
     */
    public OracleContingenciaSmsDados() {
        super(ContingenciaSms.class);
    }

}