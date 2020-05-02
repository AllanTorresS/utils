package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Base para construcao dos repositorios de preco, para tratamento unificado da ordenacao de registros
 *
 * @param <P> A entidade persistente alvo
 */
@Repository
public abstract class OracleOrdenacaoPrecosDados<P extends IPersistente> extends OracleRepositorioBoleiaDados<P> {

    /**
     * Instancia o repositorio
     *
     * @param classe A classe da entidade alvo
     */
    public OracleOrdenacaoPrecosDados(Class<P> classe) {
        super(classe);
    }


    /**
     * Monta os parametros de ordenacao de acordo com a entidade alvo
     *
     * @param paginacao  A informacao de paginacao recebida
     * @param parametros a lista de parametros a ser montada
     */
    protected void montarParametroOrdenacaoPreco(InformacaoPaginacao paginacao, List<ParametroPesquisa> parametros) {
        String prefixoCampoFrotaPontoVenda = getPrefixoCampoFrotaPontoVenda();
        String prefixoCampoPrecoBase = getPrefixoCampoPrecoBase();
        montarParametroOrdenacaoPreco(
                paginacao,
                parametros,
                prefixoCampoFrotaPontoVenda != null ? prefixoCampoFrotaPontoVenda + "." : "",
                prefixoCampoPrecoBase != null ? prefixoCampoPrecoBase + "." : "");
    }

    /**
     * Retorna o nome do campo frotaPontoVenda para navegacao nas consultas JPQL
     *
     * @return O nome do campo
     */
    protected abstract String getPrefixoCampoFrotaPontoVenda();

    /**
     * Retorna o nome do campo precoBase para navegacao nas consultas JPQL
     *
     * @return O nome do campo
     */
    protected abstract String getPrefixoCampoPrecoBase();

    /**
     * Monta os parametros de ordenacao de acordo com o
     *
     * @param paginacao         A informacao de paginacao recebida
     * @param parametros        a lista de parametros a ser montada
     * @param prefixoPontoVenda Prefixo do caminho do ponto de venda na entidade de Preço
     * @param prefixoPrecoBase  Prefixo do caminho do Preco Base na entidade de Preco Base
     */
    private void montarParametroOrdenacaoPreco(InformacaoPaginacao paginacao, List<ParametroPesquisa> parametros, String prefixoPontoVenda, String prefixoPrecoBase) {
        if (paginacao != null && CollectionUtils.isNotEmpty(paginacao.getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna ordenacao = paginacao.getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = ordenacao.getNome();
            if (nomeOrdenacao != null) {
                if (nomeOrdenacao.contains("dataAtualizacao")) {
                    paginacao.getParametrosOrdenacaoColuna().get(0).setTruncarData(true);
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna(prefixoPontoVenda + "pontoVenda.nome"));
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna(prefixoPrecoBase + "precoMicromercado.tipoCombustivel.descricao"));
                } else if (nomeOrdenacao.contains("pontoVenda.nome")) {
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE, true));
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna(prefixoPrecoBase + "precoMicromercado.tipoCombustivel.descricao"));
                } else if (nomeOrdenacao.contains("precoMicromercado.tipoCombustivel.descricao")) {
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.DECRESCENTE, true));
                    paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna(prefixoPontoVenda + "pontoVenda.nome"));
                }

            }
        }
        parametros.add(new ParametroPesquisaIgual(prefixoPontoVenda + "pontoVenda.status", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual(prefixoPontoVenda + "pontoVenda.statusHabilitacao", StatusHabilitacaoPontoVenda.HABILITADO.getValue()));
    }
}
