package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRotaParametroFrotaDados;
import ipp.aci.boleia.dados.IRotaPostoDesconsideradoDados;
import ipp.aci.boleia.dominio.RotaPostoDesconsiderado;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OracleRotaPostoDesconsideradoDados extends OracleRepositorioBoleiaDados<RotaPostoDesconsiderado> implements IRotaPostoDesconsideradoDados {

    /**
     * Construtor
     */
    public OracleRotaPostoDesconsideradoDados() {
        super(RotaPostoDesconsiderado.class);
    }
}
