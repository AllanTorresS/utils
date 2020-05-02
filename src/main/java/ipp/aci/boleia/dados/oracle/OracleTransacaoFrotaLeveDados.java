package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITransacaoFrotaLeveDados;
import ipp.aci.boleia.dominio.TransacaoFrotaLeve;
import ipp.aci.boleia.dominio.enums.TipoTransacaoFrotaLeve;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades TransacaoFrotaLeve
 */
@Repository
public class OracleTransacaoFrotaLeveDados extends OracleRepositorioBoleiaDados<TransacaoFrotaLeve> implements ITransacaoFrotaLeveDados {

    /**
     * Instancia o repositorio
     */
    public OracleTransacaoFrotaLeveDados() {
        super(TransacaoFrotaLeve.class);
    }

    @Override
    public TransacaoFrotaLeve obterUltimaTransacaoQueimaPorNsuZaccOriginal(String nsuZacc) {
        ParametroOrdenacaoColuna ordenacaoColuna = new ParametroOrdenacaoColuna("dataRequisicao", Ordenacao.DECRESCENTE);
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, ordenacaoColuna);
        return pesquisar(paginacao,
                new ParametroPesquisaIgual("tipoTransacao", TipoTransacaoFrotaLeve.AUTORIZAR.getValue()),
                new ParametroPesquisaIgual("nsuZacc", nsuZacc)
        ).getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public TransacaoFrotaLeve obterPrimeiraTransacaoQueimaPorCdPedido(Long idPedido) {
        ParametroOrdenacaoColuna ordenacaoColuna = new ParametroOrdenacaoColuna("dataRequisicao", Ordenacao.CRESCENTE);
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, ordenacaoColuna);
        return pesquisar(paginacao,
                new ParametroPesquisaIgual("pedido.id", idPedido),
                new ParametroPesquisaIgual("tipoTransacao", TipoTransacaoFrotaLeve.AUTORIZAR.getValue()),
                new ParametroPesquisaNulo("nsuZaccOriginal")
        ).getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public TransacaoFrotaLeve obterPorNsuZacc(String nsuZacc) {
        return pesquisarUnico(new ParametroPesquisaIgual("nsuZacc", nsuZacc));
    }

    @Override
    public List<TransacaoFrotaLeve> obterTransacoesQueimaPorPedido(Long idPedido) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("pedido.id", idPedido),
                new ParametroPesquisaIgual("tipoTransacao", TipoTransacaoFrotaLeve.AUTORIZAR.getValue()));
    }


}
