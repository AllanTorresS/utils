package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotivoAlteracaoStatusFrotaDados;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;
import org.springframework.stereotype.Repository;

/**
 * Respositorio dos motivos de inativacao e ativacao da frota
 */
@Repository
public class OracleMotivoAlteracaoStatusFrotaDados extends OracleRepositorioBoleiaDados<MotivoAlteracaoStatusFrota> implements IMotivoAlteracaoStatusFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleMotivoAlteracaoStatusFrotaDados() {
        super(MotivoAlteracaoStatusFrota.class);
    }
}