package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IItemAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.vo.PrecoItemVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades ItemAutorizacaoPagamento
 */
@Repository
public class OracleItemAutorizacaoPagamentoDados extends OracleRepositorioBoleiaDados<ItemAutorizacaoPagamento> implements IItemAutorizacaoPagamentoDados {

    private static final String CONSULTA_ITENS_PRECO_MAXIMO_FROTA =
        " SELECT new ipp.aci.boleia.dominio.vo.PrecoItemVo(ite.codigo, ite.tipoItem, MAX(ite.valorUnitario)) " +
        " FROM ItemAutorizacaoPagamento ite INNER JOIN ite.autorizacaoPagamento aut INNER JOIN aut.frota f " +
        " WHERE f.id = :idFrota AND aut.dataProcessamento > :dataMinima AND aut.status = 1 GROUP BY ite.codigo, ite.tipoItem";

    /**
     * Instancia o repositorio
     */
    public OracleItemAutorizacaoPagamentoDados() {
        super(ItemAutorizacaoPagamento.class);
    }

    @Override
    public List<ItemAutorizacaoPagamento> obterItensParaConsolidacao(Long codigoFrota, Long codigoPV, Long codigoEmpresaAgregadaQueExigeNF, Date dataInicioPeriodo, Date dataFimPeriodo, boolean prePago) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIn("autorizacaoPagamento.status", Arrays.asList(StatusAutorizacao.AUTORIZADO.getValue(),StatusAutorizacao.CANCELADO.getValue())));
        params.add(new ParametroPesquisaIgual("autorizacaoPagamento.transacaoFrota.frota.id", codigoFrota));
        params.add(new ParametroPesquisaIgual("autorizacaoPagamento.pontoVenda.id", codigoPV));
        params.add(new ParametroPesquisaDataMaiorOuIgual("autorizacaoPagamento.dataProcessamento", dataInicioPeriodo));
        params.add(new ParametroPesquisaDataMenorOuIgual("autorizacaoPagamento.dataProcessamento", dataFimPeriodo));
        params.add(new ParametroPesquisaIgual("autorizacaoPagamento.transacaoFrota.consumiuCreditoPrePago", prePago));

        Boolean possuiEmpresaAgregadaQueExigeNF = codigoEmpresaAgregadaQueExigeNF != null;
        if(possuiEmpresaAgregadaQueExigeNF) {
            params.add(new ParametroPesquisaIgual("autorizacaoPagamento.empresaAgregada.id", codigoEmpresaAgregadaQueExigeNF));
        }
        params.add(new ParametroPesquisaIgual("autorizacaoPagamento.empresaAgregadaExigeNf", possuiEmpresaAgregadaQueExigeNF));

        return pesquisar((ParametroOrdenacaoColuna) null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public List<ItemAutorizacaoPagamento> obterItensPorIdAutorizacao(Long idAutorizacao) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("autorizacaoPagamento.id", idAutorizacao));
    }

    @Override
    public ItemAutorizacaoPagamento desanexar(ItemAutorizacaoPagamento entidade){
        return super.desanexar(entidade);
    }

    @Override
    public List<PrecoItemVo> obterItensPrecoMaximoAutorizadosPorFrotaData(Long idFrota, Date dataMinima) {
        return pesquisar(null, CONSULTA_ITENS_PRECO_MAXIMO_FROTA, PrecoItemVo.class, new ParametroPesquisaIgual("idFrota", idFrota), new ParametroPesquisaIgual("dataMinima", dataMinima)).getRegistros();
    }

    @Override
    public List<ItemAutorizacaoPagamento> obterItensAutorizadosPorDataMinima(Date dataMinima) {
        return pesquisar((ParametroOrdenacaoColuna) null,  new ParametroPesquisaIgual("autorizacaoPagamento.status", StatusAutorizacao.AUTORIZADO.getValue()), new ParametroPesquisaDataMaiorOuIgual("autorizacaoPagamento.dataProcessamento", dataMinima));
    }
}
