package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoParametroPostosPermitidosDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.HistoricoParametroPostosPermitidos;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.Ordenacao.DECRESCENTE;
import static ipp.aci.boleia.util.UtilitarioLambda.obterPrimeiroObjetoDaLista;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoParametroPostosPermitidos}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleHistoricoParametroPostosPermitidosDados extends OracleRepositorioBoleiaDados<HistoricoParametroPostosPermitidos> implements IHistoricoParametroPostosPermitidosDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoParametroPostosPermitidosDados() {
        super(HistoricoParametroPostosPermitidos.class);
    }

    @Override
    public HistoricoParametroPostosPermitidos obterUltimoHistoricoAutorizacao(FrotaParametroSistemaPostoAutorizadoAbastecimento parametroPostoPermitido, HistoricoParametroUso historicoParametroUso) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("pontoVenda.id", parametroPostoPermitido.getPontoVenda().getId()));
        parametros.add(new ParametroPesquisaIgual("historicoParametroUso.id", historicoParametroUso.getId()));
        parametros.add(new ParametroPesquisaIgual("dataDesautorizacao", null));
        ParametroOrdenacaoColuna parametroOrdenacao = new ParametroOrdenacaoColuna("dataAutorizacao", DECRESCENTE);
        return obterPrimeiroObjetoDaLista(pesquisar(parametroOrdenacao, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }

    @Override
    public HistoricoParametroPostosPermitidos obterHistorico(PontoDeVenda pv, HistoricoParametroUso historicoParametroUso, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("pontoVenda.id", pv.getId()));
        parametros.add(new ParametroPesquisaIgual("historicoParametroUso.id", historicoParametroUso.getId()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataAutorizacao", data));
        parametros.add(new ParametroPesquisaOr(new ParametroPesquisaDataMaiorOuIgual("dataDesautorizacao", data), new ParametroPesquisaNulo("dataDesautorizacao")));
        return obterPrimeiroObjetoDaLista(pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }
}
