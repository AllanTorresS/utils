package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPropostaAntecipacaoDados;
import ipp.aci.boleia.dominio.PropostaAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link PropostaAntecipacao}.
 */
@Repository
public class OraclePropostaAntecipacaoDados extends OracleRepositorioBoleiaDados<PropostaAntecipacao> implements IPropostaAntecipacaoDados {

    /**
     * Instancia o repositorio
     */
    public OraclePropostaAntecipacaoDados() {
        super(PropostaAntecipacao.class);
    }


    @Override
    public PropostaAntecipacao obterPorIdParceiro(String idProposta) {
        return pesquisarUnico(new ParametroPesquisaIgual("idParceiro", idProposta));
    }
}
