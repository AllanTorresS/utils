package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IContaBancariaDados;
import ipp.aci.boleia.dominio.ContaBancaria;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Conta Bancaria
 */
@Repository
public class OracleContaBancariaDados extends OracleRepositorioBoleiaDados<ContaBancaria> implements IContaBancariaDados {

    /**
     * Instancia o repositorio
     */
    public OracleContaBancariaDados() {
        super(ContaBancaria.class);
    }
}
