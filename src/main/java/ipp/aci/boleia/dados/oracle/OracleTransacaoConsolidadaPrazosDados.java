package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRepositorioBoleiaDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaPrazosDados;
import ipp.aci.boleia.dominio.TransacaoConsolidadaPrazos;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade TransacaoConsolidadaPrazos.
 */
@Repository
public class OracleTransacaoConsolidadaPrazosDados extends OracleRepositorioBoleiaDados<TransacaoConsolidadaPrazos> implements ITransacaoConsolidadaPrazosDados {

    /**
     * Instancia o repositório
     */
    public OracleTransacaoConsolidadaPrazosDados() {
        super(TransacaoConsolidadaPrazos.class);
    }


}
