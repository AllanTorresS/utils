package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoFrotaConfig;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Respositorio de entidades HistoricoFluxoAbastecimentoFrotaConfig
 */
@Repository
public class OracleHistoricoFluxoAbastecimentoFrotaDados extends OracleRepositorioBoleiaDados<HistoricoFluxoAbastecimentoFrotaConfig> implements IHistoricoFluxoAbastecimentoFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoFluxoAbastecimentoFrotaDados() {
        super(HistoricoFluxoAbastecimentoFrotaConfig.class);
    }

    @Override
    public HistoricoFluxoAbastecimentoFrotaConfig obterFluxoPorData(Frota frota, Date dataRequisicao) {
        ParametrosPesquisaBuilder parametros = new ParametrosPesquisaBuilder(
                new ParametroPesquisaIgual("frota.id", frota.getId()),
                new ParametroPesquisaDataMenorOuIgual("dataAlteracao", dataRequisicao)
        );
        return obterPrimeiroObjetoDaLista(pesquisar(new ParametroOrdenacaoColuna("dataAlteracao", Ordenacao.DECRESCENTE), parametros.buildArray()));
    }
}