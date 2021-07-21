package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoEdicaoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAutorizacaoPagamentoEdicaoVo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório de entidades AutorizacaoPagamento
 */

@Repository
public class OracleAutorizacaoPagamentoEdicaoDados extends OracleRepositorioBoleiaDados<AutorizacaoPagamentoEdicao> implements IAutorizacaoPagamentoEdicaoDados {

    /**
     * Instancia o repositório
     */
    public OracleAutorizacaoPagamentoEdicaoDados() {
        super(AutorizacaoPagamentoEdicao.class);
    }

    @Override
    public ResultadoPaginado<AutorizacaoPagamentoEdicao> pesquisar(FiltroPesquisaAutorizacaoPagamentoEdicaoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getAutorizacaoPagamentoId() != null) {
            parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.id", filtro.getAutorizacaoPagamentoId()));
        }
        if (filtro.getStatus() != null) {
            parametros.add(new ParametroPesquisaIgual("statusEdicao", filtro.getStatus().getValue()));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public AutorizacaoPagamentoEdicao obterUltimaAutorizacaoPgtoEdicao(AutorizacaoPagamento autorizacaoPagamento) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.id", autorizacaoPagamento.getId()));
        parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("statusEdicao", StatusEdicao.EDITADO.getValue()),
                new ParametroPesquisaIgual("statusEdicao", StatusEdicao.PENDENTE.getValue())
        ));

        return pesquisar(new InformacaoPaginacao(1, 1, "dataEdicao", Ordenacao.DECRESCENTE),
                parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public AutorizacaoPagamentoEdicao obterEdicaoPendentePorAbastecimento(Long idAbastecimento){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.id", idAbastecimento));
        parametros.add(new ParametroPesquisaIgual("statusEdicao", StatusEdicao.PENDENTE.getValue()));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public AutorizacaoPagamentoEdicao obterUltimaAutorizacaoPgtoEdicaoRejeitada(Long idAbastecimento) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.id", idAbastecimento));
        parametros.add(new ParametroPesquisaIgual("statusEdicao", StatusEdicao.REJEITADO.getValue()));

        return pesquisar(new InformacaoPaginacao(1,1,"dataEdicao",Ordenacao.DECRESCENTE),
                parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public boolean verificarSeAbastecimentoPossuiEdicaoEfetuada(Long id) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("autorizacaoPagamento.id", id));
        parametros.add(new ParametroPesquisaIgual("statusEdicao", StatusEdicao.EDITADO.getValue()));
        return pesquisarTotalRegistros(parametros.toArray(new ParametroPesquisa[parametros.size()])).intValue() > 0;
    }

    @Override
    public List<AutorizacaoPagamentoEdicao> obterPorMotorista(Motorista motorista){
        return pesquisar(new ParametroOrdenacaoColuna(),
                new ParametroPesquisaIgual("motorista", motorista));
    }
}