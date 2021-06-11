package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IOperacaoContaBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.OperacaoContaBeneficiario;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link OperacaoContaBeneficiario}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleOperacaoContaBeneficiarioDados extends OracleRepositorioBoleiaDados<OperacaoContaBeneficiario> implements IOperacaoContaBeneficiarioDados {

    /**
     * Instancia o repositorio
     */
    public OracleOperacaoContaBeneficiarioDados() {
        super(OperacaoContaBeneficiario.class);
    }
}
