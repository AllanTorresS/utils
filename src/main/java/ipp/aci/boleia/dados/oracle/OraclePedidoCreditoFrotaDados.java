package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPedidoCreditoFrotaDados;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoCreditoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades de
 * PedidoCreditoFrota
 */
@Repository
public class OraclePedidoCreditoFrotaDados extends OracleRepositorioBoleiaDados<PedidoCreditoFrota> implements IPedidoCreditoFrotaDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OraclePedidoCreditoFrotaDados() {
        super(PedidoCreditoFrota.class);
    }

    @Override
    public List<PedidoCreditoFrota> obterTodosAbertos(Date dataAtual) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaDataMaiorOuIgual("validadePedido", dataAtual),
                new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PROCESSANDO.getValue())),
                new ParametroPesquisaFetch("frota"));
    }

    @Override
    public PedidoCreditoFrota obterUltimoPedidoAberto(Date dataAtual) {
        ResultadoPaginado<PedidoCreditoFrota> result = pesquisar(
                new InformacaoPaginacao(1, 1, "id"),
                new ParametroPesquisaDataMaiorOuIgual("validadePedido", dataAtual),
                new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PROCESSANDO.getValue())
                )
        );
        return CollectionUtils.isEmpty(result.getRegistros()) ? null : result.getRegistros().get(0);
    }

    @Override
    public List<PedidoCreditoFrota> obterPendentesAposData(Date data) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()), new ParametroPesquisaDataMaiorOuIgual("dataPedido", data));
    }

    @Override
    public List<PedidoCreditoFrota> obterPagosComSaldo(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("dataPedido", Ordenacao.CRESCENTE),
                new ParametroPesquisaIgual("status", StatusPedidoCredito.PAGO.getValue()),
                new ParametroPesquisaMaior("saldoPedido", BigDecimal.ZERO),
                new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public List<PedidoCreditoFrota> obterPagosNaoVencidosPendentesJDE(Date dataAtual) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE),
                new ParametroPesquisaDataMaiorOuIgual("validadePedido", dataAtual),
                new ParametroPesquisaIgual("status", StatusPedidoCredito.PAGO.getValue()),
                new ParametroPesquisaOr(
                        new ParametroPesquisaDiferente("statusJde", StatusIntegracaoJde.REALIZADO.getValue()),
                        new ParametroPesquisaNulo("statusJde")),
                new ParametroPesquisaNulo("mensagemErroJde"),
                new ParametroPesquisaFetch("frota"));
    }

    @Override
    public ResultadoPaginado<PedidoCreditoFrota> pesquisar(FiltroPesquisaPedidoCreditoVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PedidoCreditoFrota> pesquisarParaExportacao(FiltroPesquisaPedidoCreditoVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao().getParametrosOrdenacaoColuna(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<PedidoCreditoFrota> obterPagosExpirados() {
        return pesquisar((ParametroOrdenacaoColuna)null, new ParametroPesquisaIgual("status", StatusPedidoCredito.PAGO.getValue()),
                new ParametroPesquisaDataMenor("validadePedido", ambiente.buscarDataAmbiente()));
    }

    @Override
    public List<PedidoCreditoFrota> obterParaAutocomplete(String termoPesquisa) {
        return  pesquisar(new ParametroOrdenacaoColuna("codigoPedido", Ordenacao.CRESCENTE), new ParametroPesquisaLike("codigoPedido", termoPesquisa));
    }

    /**
     * Cria os parametros para pesquisa de pedidos
     * @param filtro O filtro da pesquisa
     * @return Os parametros montados
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaPedidoCreditoVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarStatusMundiPaggPesquisa(filtro, parametros);
        povoarStatusInternoPesquisa(filtro, parametros);

        if (filtro.getCodigoPedido() != null) {
            parametros.add(new ParametroPesquisaLike("codigoPedido", filtro.getCodigoPedido().getNome()));
        }

        if (filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        if (filtro.getDe() != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataPedido", filtro.getDe()));
        }
        if (filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataPedido", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }

        if(filtro.getPaginacao().getParametrosOrdenacaoColuna() != null) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataPedido", Ordenacao.DECRESCENTE));
        } else {
            filtro.getPaginacao().setParametrosOrdenacaoColuna(Collections.singletonList(new ParametroOrdenacaoColuna("dataPedido", Ordenacao.DECRESCENTE)));
        }

        return parametros;
    }

    /**
     * Preenche a pesquisa com o parametro de status mundipagg informado pelo usuario
     * @param filtro O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarStatusMundiPaggPesquisa(FiltroPesquisaPedidoCreditoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getStatusMundiPagg() != null && filtro.getStatusMundiPagg().getName() != null){
            StatusPedidoCredito status = StatusPedidoCredito.valueOf(filtro.getStatusMundiPagg().getName());
            if (status.equals(StatusPedidoCredito.VENCIDO)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.VENCIDO.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PROCESSANDO.getValue())));
                parametros.add(new ParametroPesquisaDataMenorOuIgual("validadePedido", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
            } else {
                if(status.equals(StatusPedidoCredito.PENDENTE) || status.equals(StatusPedidoCredito.PROCESSANDO)) {
                    parametros.add(new ParametroPesquisaDataMaiorOuIgual("validadePedido", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
                }
                parametros.add(new ParametroPesquisaIgual("status", status.getValue()));
            }
        }
    }

    /**
     * Preenche a pesquisa com o parametro de status interno informado pelo usuario
     * @param filtro O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarStatusInternoPesquisa(FiltroPesquisaPedidoCreditoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getStatusSolucao() != null && filtro.getStatusSolucao().getName() != null){
            StatusPedidoCredito status = StatusPedidoCredito.valueOf(filtro.getStatusSolucao().getName());
            if(status.equals(StatusPedidoCredito.PENDENTE) || status.equals(StatusPedidoCredito.PROCESSANDO)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PROCESSANDO.getValue())));
                parametros.add(new ParametroPesquisaDataMaiorOuIgual("validadePedido", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
            } else if(status.equals(StatusPedidoCredito.PAGOAMENOS) || status.equals(StatusPedidoCredito.PAGOAMAIS)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PAGOAMENOS.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PAGOAMAIS.getValue())));
            } else if (status.equals(StatusPedidoCredito.VENCIDO)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.VENCIDO.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PENDENTE.getValue()),
                        new ParametroPesquisaIgual("status", StatusPedidoCredito.PROCESSANDO.getValue())));
                parametros.add(new ParametroPesquisaDataMenorOuIgual("validadePedido", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
            } else {
                parametros.add(new ParametroPesquisaIgual("status", status.getValue()));
            }
        }
    }
}
