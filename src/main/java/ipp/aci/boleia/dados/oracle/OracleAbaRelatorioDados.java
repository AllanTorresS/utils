package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAbaRelatorioDados;
import ipp.aci.boleia.dominio.AbaRelatorio;
import org.springframework.stereotype.Repository;

@Repository
public class OracleAbaRelatorioDados extends OracleRepositorioBoleiaDados<AbaRelatorio> implements IAbaRelatorioDados {
    /**
     * Instancia o repositorio
     */
    public OracleAbaRelatorioDados() {
        super(AbaRelatorio.class);
    }
}
