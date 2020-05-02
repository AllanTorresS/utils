package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAbastecimentoVeiculoMesDados;
import ipp.aci.boleia.dominio.AbastecimentoVeiculoMes;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades Veiculo
 */
@Repository
public class OracleAbastecimentoVeiculoMesDados extends OracleRepositorioBoleiaDados<AbastecimentoVeiculoMes> implements IAbastecimentoVeiculoMesDados {

    private static final String QUERY_DATA_MAXIMA = "SELECT MAX(a.dataAtualizacao) FROM AbastecimentoVeiculoMes a";

    /**
     * Instancia o repositorio
     */
    public OracleAbastecimentoVeiculoMesDados() {
        super(AbastecimentoVeiculoMes.class);
    }

    @Override
    public List<AbastecimentoVeiculoMes> obterPorMesAnoFrota(Date mesAno, Long idFrota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("veiculo.frota.id", idFrota), new ParametroPesquisaIgual("mesAno", mesAno));
    }

    @Override
    public List<AbastecimentoVeiculoMes> obterPorFrotaDataMinima(Long idFrota, Date dataMinima) {
        return pesquisar(new ParametroOrdenacaoColuna("mesAno"), new ParametroPesquisaIgual("veiculo.frota.id", idFrota), new ParametroPesquisaDataMaiorOuIgual("mesAno", dataMinima));
    }

    @Override
    public Date obterDataMaxima() {
        return pesquisar(null, QUERY_DATA_MAXIMA, Date.class).getRegistros().get(0);
    }
}
