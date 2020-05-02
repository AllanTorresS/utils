package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IEmpresaUnidadeDados;
import ipp.aci.boleia.dominio.EmpresaUnidade;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade Empresa/Unidade.
 */
@Repository
public class OracleEmpresaUnidadeDados extends OracleRepositorioBoleiaDados<EmpresaUnidade> implements IEmpresaUnidadeDados {

    /**
     * Construtor default da classe.
     */
    public OracleEmpresaUnidadeDados() {
        super(EmpresaUnidade.class);
    }
}
