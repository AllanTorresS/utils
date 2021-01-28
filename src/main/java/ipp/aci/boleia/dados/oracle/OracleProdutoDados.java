package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IProdutoDados;
import ipp.aci.boleia.dominio.Produto;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * Produto
 */
@Repository
public class OracleProdutoDados extends OracleRepositorioBoleiaDados<Produto> implements IProdutoDados {

    private static final String LISTAR_PRODUTOS_POR_CONSOLIDADO =
            "SELECT DISTINCT p " +
            "FROM Produto p " +
            "JOIN p.itensAutorizacaoPagamento ia " +
            "JOIN ia.autorizacaoPagamento ap " +
            "WHERE (ap.transacaoConsolidadaPostergada.id = :idConsolidado OR ap.transacaoConsolidada.id = :idConsolidado) AND " +
            "      p.nome NOT LIKE 'Outros' " +
            "ORDER BY p.nome";

    /**
     * Instancia o repositório
     */
    public OracleProdutoDados() {
        super(Produto.class);
    }

    @Override
    public List<Produto> listarPorConsolidado(Long idConsolidado) {
        ParametroPesquisaIgual parametroConsolidado = new ParametroPesquisaIgual("idConsolidado", idConsolidado);
        return pesquisar(null, LISTAR_PRODUTOS_POR_CONSOLIDADO, parametroConsolidado).getRegistros();
    }

    @Override
    public List<Produto> obterTodosOrdenadosPeloNome() {
        List<Produto> produtos = super.obterTodos(null);
        produtos.sort((p1, p2) -> {
            if (p1.isOutros() == p2.isOutros()) {
                return p1.getNome().toLowerCase().compareTo(p2.getNome().toLowerCase());
            }
            return p1.isOutros() ? 1 : -1;
        });
        return produtos;
    }

    @Override
    public List<Produto> obterProdutosExcetoOutrosOrdenadosPeloNome() {
        return pesquisar(
                new ParametroOrdenacaoColuna("nome"),
                new ParametroPesquisaDiferente("nome", Produto.OUTROS));
    }
}
