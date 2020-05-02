package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoBloqueioFrotaPontoVendaDados;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoBloqueioFrotaPontoVenda;
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
 * Implementação do repositório de dados da entidade {@link HistoricoBloqueioFrotaPontoVenda}.
 */
@Repository
public class OracleHistoricoBloqueioFrotaPontoVendaDados extends OracleRepositorioBoleiaDados<HistoricoBloqueioFrotaPontoVenda> implements IHistoricoBloqueioFrotaPontoVendaDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoBloqueioFrotaPontoVendaDados() {
        super(HistoricoBloqueioFrotaPontoVenda.class);
    }

    @Override
    public HistoricoBloqueioFrotaPontoVenda obterUltimoHistoricoBloqueio(FrotaPontoVenda frotaPontoVenda) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPontoVenda.id", frotaPontoVenda.getId()));
        parametros.add(new ParametroPesquisaIgual("dataDesbloqueio", null));
        ParametroOrdenacaoColuna parametroOrdenacao = new ParametroOrdenacaoColuna("dataBloqueio", DECRESCENTE);
        return obterPrimeiroObjetoDaLista(pesquisar(parametroOrdenacao, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }

    @Override
    public HistoricoBloqueioFrotaPontoVenda obterBloqueioPorData(Long frotaPtovId, Date data) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPontoVenda.id", frotaPtovId));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataBloqueio", data));
        parametros.add(new ParametroPesquisaOr(new ParametroPesquisaDataMaiorOuIgual("dataDesbloqueio", data), new ParametroPesquisaNulo("dataDesbloqueio")));
        return obterPrimeiroObjetoDaLista(pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()])));
    }
}
