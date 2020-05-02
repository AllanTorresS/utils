package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITransacaoFrotaDados;
import ipp.aci.boleia.dominio.TransacaoFrota;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades TransacaoFrota
 */
@Repository
public class OracleTransacaoFrotaDados extends OracleRepositorioBoleiaDados<TransacaoFrota> implements ITransacaoFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleTransacaoFrotaDados() {
        super(TransacaoFrota.class);
    }
}
