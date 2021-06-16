package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IContaBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficiario;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ContaBeneficiario}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleContaBeneficiarioDados extends OracleRepositorioBoleiaDados<ContaBeneficiario> implements IContaBeneficiarioDados {

    /**
     * Instancia o repositorio
     */
    public OracleContaBeneficiarioDados() {
        super(ContaBeneficiario.class);
    }

    @Override
    public ContaBeneficiario obterAtivaPorBeneficiario(Long idBeneficiario) {
        return pesquisarUnico(
            new ParametroPesquisaIgual("beneficiario.id", idBeneficiario),
            new ParametroPesquisaDiferente("dataEncerramento", null)
        );
    }
}
