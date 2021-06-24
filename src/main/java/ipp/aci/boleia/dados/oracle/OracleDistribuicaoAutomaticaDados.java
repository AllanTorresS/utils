package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDistribuicaoAutomaticaDados;
import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades DistribuicaoAutomatica
 */
@Repository
public class OracleDistribuicaoAutomaticaDados extends OracleRepositorioBoleiaDados<DistribuicaoAutomatica> implements IDistribuicaoAutomaticaDados {

    private static final String OBTER_DISTRIBUICOES_POR_DIA =
            " SELECT d " +
            " FROM DistribuicaoAutomatica d " +
            " JOIN FETCH d.beneficiario b " +
            " JOIN FETCH b.frota f " +
            " JOIN FETCH f.configuracaoDistribuicaoAutomatica c " +
            " WHERE c.diaDistribuicao = :dia " +
                " AND c.status = 1 " +
                " AND d.valorBeneficio <> 0 ";

    /**
     * Instancia o repositorio
     */
    public OracleDistribuicaoAutomaticaDados() {
        super(DistribuicaoAutomatica.class);
    }

    @Override
    public DistribuicaoAutomatica obterPorIdBeneficiario(Long idBeneficiario) {
        return pesquisarUnico(new ParametroPesquisaIgual("beneficiario", idBeneficiario));
    }

    @Override
    public List<DistribuicaoAutomatica> obterDistribuicoesPorDia(Integer dia){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("dia", dia));
        return pesquisar(null, OBTER_DISTRIBUICOES_POR_DIA, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
