package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IExtratoPedidoTransacaoDados;
import ipp.aci.boleia.dominio.ExtratoPedidoTransacao;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.OperacaoDeCredito;
import ipp.aci.boleia.dominio.enums.TipoTransacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaExtratoPedidoCreditoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades de
 * ExtratoPedidoTransacao
 */
@Repository
public class OracleExtratoPedidoTransacaoDados extends OracleRepositorioBoleiaDados<ExtratoPedidoTransacao> implements IExtratoPedidoTransacaoDados {

    /**
     * Instancia o repositorio
     */
    public OracleExtratoPedidoTransacaoDados() {
        super(ExtratoPedidoTransacao.class);
    }

    @Override
    public ResultadoPaginado<ExtratoPedidoTransacao> pesquisar(FiltroPesquisaExtratoPedidoCreditoVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<ExtratoPedidoTransacao> pesquisarParaExportacao(FiltroPesquisaExtratoPedidoCreditoVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao().getParametrosOrdenacaoColuna(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de extratos a serem exportados
     *
     * @param filtro O filtro de pesquisa
     * @return A lista de parametros da consulta
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaExtratoPedidoCreditoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("transacaoFrota.frota.modoPagamento", ModalidadePagamento.PRE_PAGO.getValue()));

        if(filtro.getCodigoPedido() != null) {
            parametros.add(new ParametroPesquisaIgual("pedido.codigoPedido", filtro.getCodigoPedido().getNome()));
        }
        if(filtro.getTipoOperacao() != null && filtro.getTipoOperacao().getName() != null) {
            OperacaoDeCredito tipoOperacao = OperacaoDeCredito.valueOf(filtro.getTipoOperacao().getName());
            List<TipoTransacao> tipoTransacoes = TipoTransacao.obterPorTipoOperacao(tipoOperacao);
            List<ParametroPesquisa> parametrosTransacao = tipoTransacoes.stream().map(t->new ParametroPesquisaIgual("transacaoFrota.tipoTransacao", t.getValue())).collect(Collectors.toList());
            parametros.add(new ParametroPesquisaOr(parametrosTransacao.toArray(new ParametroPesquisa[parametrosTransacao.size()])));
        }
        if(filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("transacaoFrota.frota.id", filtro.getFrota().getId()));
        }
        if(filtro.getDe() != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("transacaoFrota.dataTransacao", filtro.getDe()));
        }
        if(filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("transacaoFrota.dataTransacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }

        if(filtro.getPaginacao().getParametrosOrdenacaoColuna() != null) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("transacaoFrota.dataTransacao", Ordenacao.DECRESCENTE));
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("pedido.dataPedido", Ordenacao.DECRESCENTE));
        } else {
            filtro.getPaginacao().setParametrosOrdenacaoColuna(Arrays.asList(new ParametroOrdenacaoColuna("transacaoFrota.dataTransacao", Ordenacao.DECRESCENTE),
                    new ParametroOrdenacaoColuna("pedido.dataPedido", Ordenacao.DECRESCENTE)));
        }

        return parametros;
    }
}
