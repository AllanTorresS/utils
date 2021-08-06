package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import org.springframework.stereotype.Repository;

import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Respositorio de entidades HistoricoFluxoAbastecimentoMotoristaConfig
 */
@Repository
public class OracleHistoricoFluxoAbastecimentoMotoristaDados extends OracleRepositorioBoleiaDados<HistoricoFluxoAbastecimentoMotoristaConfig> implements IHistoricoFluxoAbastecimentoMotoristaDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoFluxoAbastecimentoMotoristaDados() {
        super(HistoricoFluxoAbastecimentoMotoristaConfig.class);
    }

    @Override
    public HistoricoFluxoAbastecimentoMotoristaConfig obterFluxoPorData(Motorista motorista, Date dataRequisicao) {
        ParametrosPesquisaBuilder parametros = new ParametrosPesquisaBuilder(
                new ParametroPesquisaIgual("motorista.id", motorista.getId()),
                new ParametroPesquisaDataMenorOuIgual("dataAlteracao", dataRequisicao)
        );
        return obterPrimeiroObjetoDaLista(pesquisar(new ParametroOrdenacaoColuna("dataAlteracao", Ordenacao.DECRESCENTE), parametros.buildArray()));
    }
}