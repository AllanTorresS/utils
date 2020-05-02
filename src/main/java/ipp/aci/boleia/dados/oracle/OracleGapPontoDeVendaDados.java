package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IGapPontoDeVendaDados;
import ipp.aci.boleia.dominio.GapPontoDeVenda;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades GapPontoDeVenda
 */
@Repository
public class OracleGapPontoDeVendaDados extends OracleRepositorioBoleiaDados<GapPontoDeVenda> implements IGapPontoDeVendaDados {

    /**
     * Instancia o repositorio
     */
    public OracleGapPontoDeVendaDados() {
        super(GapPontoDeVenda.class);
    }

}
