package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoSaldoVeiculosDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaCota;
import ipp.aci.boleia.dominio.HistoricoSaldoVeiculo;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaCota}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoSaldoVeiculoDados extends OracleRepositorioBoleiaDados<HistoricoSaldoVeiculo> implements IHistoricoSaldoVeiculosDados {

    private static final int TAMANHO_HISTORICO = 5;

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoSaldoVeiculoDados() {
        super(HistoricoSaldoVeiculo.class);
    }

    @Override
    public List<HistoricoSaldoVeiculo> obterPorIdVeiculo(Long idVeiculo) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("veiculo.id", idVeiculo));
        parametros.add(new ParametroPesquisaIgual("veiculo.status", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("veiculo.excluido", Boolean.FALSE));

        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(TAMANHO_HISTORICO);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataAlteracao", Ordenacao.DECRESCENTE));
        return pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
