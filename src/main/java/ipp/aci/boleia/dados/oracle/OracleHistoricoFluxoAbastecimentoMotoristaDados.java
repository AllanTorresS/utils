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

import javax.persistence.Query;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Respositorio de entidades HistoricoFluxoAbastecimentoMotoristaConfig
 */
@Repository
public class OracleHistoricoFluxoAbastecimentoMotoristaDados extends OracleRepositorioBoleiaDados<HistoricoFluxoAbastecimentoMotoristaConfig> implements IHistoricoFluxoAbastecimentoMotoristaDados {

    private static final String EXCLUSAO_POR_ID_MOTORISTA =
            "DELETE FROM HistoricoFluxoAbastecimentoMotoristaConfig h " +
            "WHERE h.motorista.id = :idMotorista";

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

    @Override
    public List<HistoricoFluxoAbastecimentoMotoristaConfig> obterFluxosPorMotorista(Motorista motorista) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("motorista.id", motorista.getId()));
    }

    @Override
    public void excluirPermanentementePorIdMotorista(Long idMotorista) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUSAO_POR_ID_MOTORISTA);
        query.setParameter("idMotorista", idMotorista);
        query.executeUpdate();
    }
}