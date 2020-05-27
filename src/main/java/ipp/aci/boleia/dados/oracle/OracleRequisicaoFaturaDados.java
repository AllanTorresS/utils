package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRequisicaoFaturaDados;
import ipp.aci.boleia.dominio.RequisicaoFatura;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link RequisicaoFatura}.
 */
@Repository
public class OracleRequisicaoFaturaDados extends OracleRepositorioBoleiaDados<RequisicaoFatura> implements IRequisicaoFaturaDados {

    /**
     * Construtor do repositório.
     */
    public OracleRequisicaoFaturaDados() {
        super(RequisicaoFatura.class);
    }
}
