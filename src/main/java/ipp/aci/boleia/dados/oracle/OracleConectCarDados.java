package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IConectCarDados;
import ipp.aci.boleia.dominio.ConectCar;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades de
 * ConectCar
 */
@Repository
public class OracleConectCarDados extends OracleRepositorioBoleiaDados<ConectCar> implements IConectCarDados {

    /**
     * Instancia o repositorio
     */
    public OracleConectCarDados() {
        super(ConectCar.class);
    }
}
