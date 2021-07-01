package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDistribuicaoAutomaticaDados;
import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;

/**
 * Respositorio de entidades DistribuicaoAutomatica
 */
@Repository
public class OracleDistribuicaoAutomaticaDados extends OracleRepositorioBoleiaDados<DistribuicaoAutomatica> implements IDistribuicaoAutomaticaDados {

    private static final String OBTER_DISTRIBUICOES_POR_DATA =
            " SELECT d " +
            " FROM DistribuicaoAutomatica d " +
            " JOIN FETCH d.beneficiario b " +
            " JOIN FETCH b.frota f " +
            " JOIN FETCH f.configuracaoDistribuicaoAutomatica c " +
            " WHERE c.dataDistribuicao BETWEEN :dataInicial AND :dataFinal " +
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
    public List<DistribuicaoAutomatica> obterDistribuicoesPorData(Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("dataInicial", obterPrimeiroInstanteDia(data)));
        parametros.add(new ParametroPesquisaIgual("dataFinal", obterUltimoInstanteDia(data)));
        return pesquisar(null, OBTER_DISTRIBUICOES_POR_DATA, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
