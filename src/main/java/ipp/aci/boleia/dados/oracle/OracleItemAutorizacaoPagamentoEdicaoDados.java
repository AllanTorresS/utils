package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IItemAutorizacaoPagamentoEdicaoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades ItemAutorizacaoPagamentoEdicao
 */
@Repository
public class OracleItemAutorizacaoPagamentoEdicaoDados extends OracleRepositorioBoleiaDados<ItemAutorizacaoPagamentoEdicao> implements IItemAutorizacaoPagamentoEdicaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleItemAutorizacaoPagamentoEdicaoDados() {
        super(ItemAutorizacaoPagamentoEdicao.class);
    }

    @Override
    public List<ItemAutorizacaoPagamentoEdicao> obterItensEdicaoPorAutorizacaoEdicao(AutorizacaoPagamentoEdicao autorizacaoPagamentoEdicao) {
        if (autorizacaoPagamentoEdicao == null){
            return null;
        }
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("autorizacaoPagamentoEdicao", autorizacaoPagamentoEdicao));
    }
}
