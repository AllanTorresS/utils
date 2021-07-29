package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoConfiguracaoAntecipacaoDados;
import ipp.aci.boleia.dominio.HistoricoConfiguracaoAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade {@link HistoricoConfiguracaoAntecipacao}
 */
@Repository
public class OracleHistoricoConfiguracaoAntecipacaoDados extends OracleRepositorioBoleiaDados<HistoricoConfiguracaoAntecipacao> implements IHistoricoConfiguracaoAntecipacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoConfiguracaoAntecipacaoDados() {
        super(HistoricoConfiguracaoAntecipacao.class);
    }

    @Override
    public HistoricoConfiguracaoAntecipacao obterRegistroMaisRecente() {
        ParametroOrdenacaoColuna ordenacaoDataDecrescente = new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE);
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, ordenacaoDataDecrescente);
        return pesquisar(paginacao).getRegistros().stream().findFirst().orElse(null);
    }
}
