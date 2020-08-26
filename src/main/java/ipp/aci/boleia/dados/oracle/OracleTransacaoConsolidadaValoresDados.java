package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITransacaoConsolidadaValoresDados;
import ipp.aci.boleia.dominio.TransacaoConsolidadaValores;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades Conta transacao consolidada valores
 */
@Repository
public class OracleTransacaoConsolidadaValoresDados extends OracleRepositorioBoleiaDados<TransacaoConsolidadaValores> implements ITransacaoConsolidadaValoresDados {

    /**
     * Instancia o repositorio
     */
    public OracleTransacaoConsolidadaValoresDados() {
        super(TransacaoConsolidadaValores.class);
    }
}
