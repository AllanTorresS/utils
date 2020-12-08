package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IEmpresaUnidadeDados;
import ipp.aci.boleia.dominio.EmpresaUnidade;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<EmpresaUnidade> obterPorFrota(Long idFrota) {
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("frota.id", idFrota));
    }
}
