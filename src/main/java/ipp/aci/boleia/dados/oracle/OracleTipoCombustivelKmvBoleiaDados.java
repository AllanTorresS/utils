package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoCombustivelKmvBoleiaDados;
import ipp.aci.boleia.dominio.TipoCombustivelKmvBoleia;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Tipo combustivel Kmv.
 */
@Repository
public class OracleTipoCombustivelKmvBoleiaDados extends OracleRepositorioBoleiaDados<TipoCombustivelKmvBoleia> implements ITipoCombustivelKmvBoleiaDados {

    /**
     * Instancia o repositorio
     */
    public OracleTipoCombustivelKmvBoleiaDados() {
        super(TipoCombustivelKmvBoleia.class);
    }

}